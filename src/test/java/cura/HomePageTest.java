package cura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomePageTest {

    WebDriver webDriver;

    @BeforeTest
    public void init() {
        // initiate Browser
        // jika sudah menambahkan di system variable tidak perlu lagi menambahkan setProperty.
        // System.setProperty("webdriver.chrome.driver", "C:\\..\\..\\..\\chromedriver-win64");
        webDriver = new ChromeDriver();

        // pergi ke home page
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/");
        webDriver.manage().window().maximize();
    }

    @Test(priority = 0)
    public void checkElement() {
        // check h1 element
        Assert.assertEquals(webDriver.findElement(By.cssSelector("header h1")).getText(), "CURA Healthcare Service");
        Assert.assertEquals(Color.fromString(webDriver.findElement(By.cssSelector("header h1")).getCssValue("color")).asHex(), "#ffffff");

        // check h3 element
        Assert.assertEquals(webDriver.findElement(By.cssSelector("header h3")).getText(), "We Care About Your Health");
        Assert.assertEquals(Color.fromString(webDriver.findElement(By.cssSelector("header h3")).getCssValue("color")).asHex(), "#4fb6e7");

        // check button
        Assert.assertEquals(webDriver.findElement(By.id("btn-make-appointment")).getText(), "Make Appointment");
        Assert.assertEquals(webDriver.findElement(By.id("btn-make-appointment")).getCssValue("background-color"), "rgba(115, 112, 181, 0.8)");

        // check toogle menu
        Assert.assertEquals(webDriver.findElement(By.id("menu-toggle")).getCssValue("background-color"), "rgba(115, 112, 181, 0.8)");
    }

    @Test(priority = 1)
    public void clickToogleMenu() {
        webDriver.findElement(By.id("menu-toggle")).click();
        // click kanan pada li dan copy XPath
        Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[1]")).getText(), "CURA Healthcare");
        Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[2]")).getText(), "Home");
        Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]")).getText(), "Login");
    }

    @Test(priority = 2)
    public void clickMakeAppointmentButton() {
        webDriver.findElement(By.id("btn-make-appointment")).click();
        Assert.assertEquals(webDriver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/profile.php#login");
    }

    @AfterTest
    public void closeBrowser() {
        webDriver.quit();
    }

}
