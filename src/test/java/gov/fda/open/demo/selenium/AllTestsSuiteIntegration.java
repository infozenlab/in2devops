package gov.fda.open.demo.selenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GitHubLink.class, InfoZenLink.class, SignIn.class, SideMenuLinks.class, LogoutLink.class })
public class AllTestsSuiteIntegration {
}
