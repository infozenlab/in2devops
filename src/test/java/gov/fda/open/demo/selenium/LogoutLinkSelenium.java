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

public class LogoutLinkSelenium {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory
			.getLogger(GitHubLinkSelenium.class);

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	
	private static final  String BASE_URL = "https://fda.identrix.com/";
	private static final  String CONTEXT_URL = "/fdademo/";
	private static final  String LINK_TEXT = "Try the App!";
	private static final  String LOGOUT_TEXT = "Logout";

	@Before
	@Loggable(LogLevel.INFO)
	public void setUp() throws ApplicationException {
		driver = new FirefoxDriver();
		baseUrl = BASE_URL;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@Test
	@Loggable(LogLevel.INFO)
	public void testLogoutLink() throws ApplicationException {
		driver.get(baseUrl + CONTEXT_URL);
		driver.findElement(By.linkText(LINK_TEXT)).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText(LOGOUT_TEXT)).click();
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