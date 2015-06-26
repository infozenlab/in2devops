package gov.fda.open.demo.selenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GitHubLinkSelenium.class, InfoZenLinkSelenium.class, SignInSelenium.class, SideMenuLinksSelenium.class, LogoutLinkSelenium.class })
public class AllTestsSuiteIntegration {
}
