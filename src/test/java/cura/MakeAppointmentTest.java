package cura;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class MakeAppointmentTest {

    WebDriver webDriver;

    @BeforeTest
    public void init() {

        /*
         initiate Browser
         jika sudah menambahkan di system variable tidak perlu lagi menambahkan setProperty.
         System.setProperty("webdriver.chrome.driver", "C:\\..\\..\\..\\chromedriver-win64");
        */
        webDriver = new ChromeDriver();

        /* pergi ke home page */
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        webDriver.manage().window().maximize();

        /* login */
        webDriver.findElement(By.id("txt-username")).sendKeys("John Doe");
        webDriver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        webDriver.findElement(By.id("btn-login")).click();
        Assert.assertEquals(webDriver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @Test(priority = 0)
    public void checkElement() {
        /* check h2 element */
        Assert.assertEquals(webDriver.findElement(By.cssSelector("section h2")).getText(), "Make Appointment");

        /* chech dropdown element */
        Select dropdownFacility = new Select(webDriver.findElement(By.id("combo_facility")));
        List<WebElement> dropdownOptions = dropdownFacility.getOptions();
        Assert.assertEquals(dropdownOptions.get(0).getAttribute("value"), "Tokyo CURA Healthcare Center");
        Assert.assertEquals(dropdownOptions.get(1).getAttribute("value"), "Hongkong CURA Healthcare Center");
        Assert.assertEquals(dropdownOptions.get(2).getAttribute("value"), "Seoul CURA Healthcare Center");

        /* check text area */
        Assert.assertEquals(webDriver.findElement(By.id("txt_comment")).getAttribute("placeholder"), "Comment");
    }

    @Test(priority = 1)
    public void makeAppointmentWithCorrectValues() {
        /* select facility */
        Select dropdownFacility = new Select(webDriver.findElement(By.id("combo_facility")));
        dropdownFacility.selectByValue("Hongkong CURA Healthcare Center");

        /* select check box */
        webDriver.findElement(By.id("chk_hospotal_readmission")).click();

        /* select apply for */
        webDriver.findElement(By.id("radio_program_medicaid")).click();

        /* select visit date */
        webDriver.findElement(By.id("txt_visit_date")).sendKeys("01/01/2021");

        /* select comment */
        webDriver.findElement(By.id("txt_comment")).sendKeys("This is a comment");

        /* click book appointment */
        webDriver.findElement(By.id("btn-book-appointment")).click();
        Assert.assertEquals(webDriver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/appointment.php#summary");
    }

    @Test(priority = 2, dependsOnMethods = {"makeAppointmentWithCorrectValues"})
    public void checkAppointmentSummary() {
        /* check appointment summary */
        Assert.assertEquals(webDriver.findElement(By.cssSelector("section h2")).getText(), "Appointment Confirmation");

        /* check p element */
        Assert.assertEquals(webDriver.findElement(By.cssSelector("section p")).getText(), "Please be informed that your appointment has been booked as following:");

        /* check facility */
        Assert.assertEquals(webDriver.findElement(By.id("facility")).getText(), "Hongkong CURA Healthcare Center");

        /* check readmission */
        Assert.assertEquals(webDriver.findElement(By.id("hospital_readmission")).getText(), "Yes");

        /* check healthcare program */
        Assert.assertEquals(webDriver.findElement(By.id("program")).getText(), "Medicaid");

        /* check visit date */
        Assert.assertEquals(webDriver.findElement(By.id("visit_date")).getText(), "01/01/2021");

        /* check comment */
        Assert.assertEquals(webDriver.findElement(By.id("comment")).getText(), "This is a comment");

        /* go home */
        webDriver.findElement(By.className("btn-default")).click();
        Assert.assertEquals(webDriver.getCurrentUrl(), "https://katalon-demo-cura.herokuapp.com/");

    }

    @AfterTest
    public void closeBrowser() {
        webDriver.close();
    }
}
