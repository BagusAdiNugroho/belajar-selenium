package cura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginPageTest {

    WebDriver webDriver;

    @BeforeTest
    public void init() {
        // initiate Browser
        // jika sudah menambahkan di system variable tidak perlu lagi menambahkan setProperty.
        // System.setProperty("webdriver.chrome.driver", "C:\\..\\..\\..\\chromedriver-win64");
        webDriver = new ChromeDriver();

        // pergi ke home page
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        webDriver.manage().window().maximize();
    }

    @Test(priority = 0)
    public void checkElement() {
        // check h2 element
        Assert.assertEquals(webDriver.findElement(By.cssSelector("section h2")).getText(), "Login");

        // check p element
        Assert.assertEquals(webDriver.findElement(By.cssSelector("section p")).getText(), "Please login to make appointment.");

        // check text box element
        Assert.assertEquals(webDriver.findElement(By.id("txt-username")).getAttribute("placeholder"), "Username");
        Assert.assertEquals(webDriver.findElement(By.id("txt-password")).getAttribute("placeholder"), "Password");
    }

    @Test(priority = 1)
    public void loginWithNullValues() {
        webDriver.findElement(By.id("btn-login")).click();
        Assert.assertEquals(webDriver.findElement(By.className("text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test(priority = 2)
    public void loginWithWrongValues() {
        webDriver.findElement(By.id("txt-username")).sendKeys("John Doe");
        webDriver.findElement(By.id("txt-password")).sendKeys("wrong");
        webDriver.findElement(By.id("btn-login")).click();
        Assert.assertEquals(webDriver.findElement(By.className("text-danger")).getText(), "Login failed! Please ensure the username and password are valid.");
    }

    @Test(priority = 3)
    public void loginWithCorrectValues() {
        webDriver.findElement(By.id("txt-username")).sendKeys("John Doe");
        webDriver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        webDriver.findElement(By.id("btn-login")).click();
        Assert.assertEquals(webDriver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @AfterTest
    public void closeBrowser() {
        webDriver.close();
    }
}
