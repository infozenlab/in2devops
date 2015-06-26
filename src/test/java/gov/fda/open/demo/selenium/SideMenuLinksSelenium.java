package gov.fda.open.demo.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.test.context.ContextConfiguration;

public class SideMenuLinksSelenium {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://fda.identrix.com/";
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
  }

  @Test
  public void testSideMenuLinks() throws Exception {
    driver.get(baseUrl + "/fdademo/");
    driver.findElement(By.linkText("Try the App!")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Try Our App!")).click();
    driver.findElement(By.linkText("Team Members")).click();
    driver.findElement(By.linkText("Persona Maps")).click();
    driver.findElement(By.linkText("Business Hypothesis")).click();
    driver.findElement(By.linkText("Focus Groups")).click();
    driver.findElement(By.linkText("Digital Services Playbook")).click();
    driver.findElement(By.linkText("Lean/Kanban")).click();
    driver.findElement(By.linkText("Pair Programming")).click();
    driver.findElement(By.xpath("//a[contains(text(),'DevOps in Practice')]")).click();
    driver.findElement(By.xpath("//a[contains(text(),'Application Architecture')]")).click();
    driver.findElement(By.linkText("Responsive Mobile")).click();
    driver.findElement(By.linkText("ReadMe.md")).click();
    driver.findElement(By.linkText("SonarQube: Time Machine")).click();
    driver.findElement(By.linkText("SonarQube: Dashboard")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  @SuppressWarnings("unused")
private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  @SuppressWarnings("unused")
private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  @SuppressWarnings("unused")
private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
