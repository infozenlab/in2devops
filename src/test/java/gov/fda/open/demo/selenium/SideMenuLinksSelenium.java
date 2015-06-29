package gov.fda.open.demo.selenium;

import static org.junit.Assert.fail;
import gov.fda.open.demo.error.ApplicationException;
import gov.fda.open.demo.model.enums.LogLevel;
import gov.fda.open.demo.service.loggable.Loggable;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SideMenuLinksSelenium {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(GitHubLinkSelenium.class);

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	
	private static final  String BASE_URL = "https://fda.identrix.com/";
	private static final  String CONTEXT_URL = "/fdademo/";
	private static final  String LINK_TEXT1 = "Try the App!";
	private static final  String LINK_TEXT2 = "Try Our App!";
	private static final  String LINK_TEXT3 = "Team Members";
	private static final  String LINK_TEXT4 = "Persona Maps";
	private static final  String LINK_TEXT5 = "Business Hypothesis";
	private static final  String LINK_TEXT6 = "Focus Groups";
	private static final  String LINK_TEXT7 = "Digital Services Playbook";
	private static final  String LINK_TEXT8 = "Lean/Kanban";
	private static final  String LINK_TEXT9 = "Pair Programming";
	private static final  String LINK_TEXT10 = "Responsive Mobile";
	private static final  String LINK_TEXT11 = "README.md";
	private static final  String LINK_TEXT12 = "SonarQube: Time Machine";
	private static final  String LINK_TEXT13 = "SonarQube: Dashboard";

	@Before
	@Loggable(LogLevel.INFO)
	public void setUp() throws ApplicationException {
		driver = new FirefoxDriver();
		baseUrl = BASE_URL;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@Test
	@Loggable(LogLevel.INFO)
	public void testSideMenuLinks() throws ApplicationException {
		driver.get(baseUrl + CONTEXT_URL);
		driver.findElement(By.linkText(LINK_TEXT1)).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText(LINK_TEXT2)).click();
		driver.findElement(By.linkText(LINK_TEXT3)).click();
		driver.findElement(By.linkText(LINK_TEXT4)).click();
		driver.findElement(By.linkText(LINK_TEXT5)).click();
		driver.findElement(By.linkText(LINK_TEXT6)).click();
		driver.findElement(By.linkText(LINK_TEXT7)).click();
		driver.findElement(By.linkText(LINK_TEXT8)).click();
		driver.findElement(By.linkText(LINK_TEXT9)).click();
		driver.findElement(
				By.xpath("//a[contains(text(),'DevOps in Practice')]")).click();
		driver.findElement(
				By.xpath("//a[contains(text(),'Application Architecture')]"))
				.click();
		driver.findElement(By.linkText(LINK_TEXT10)).click();
		driver.findElement(By.linkText(LINK_TEXT11)).click();
		driver.findElement(By.linkText(LINK_TEXT12)).click();
		driver.findElement(By.linkText(LINK_TEXT13)).click();
	}

	@After
	@Loggable(LogLevel.INFO)
	public void tearDown() throws ApplicationException {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Loggable(LogLevel.INFO)
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			LOG.error("Error while isElementPresent()", e);
			return false;
		}
	}

	@Loggable(LogLevel.INFO)
	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			LOG.error("Error while isAlertPresent()", e);
			return false;
		}
	}

	@Loggable(LogLevel.INFO)
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
