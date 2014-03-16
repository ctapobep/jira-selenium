package jira.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * @author stanislav bashkirtsev
 */
public class InitialJiraSetup {
    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        if(args.length < 2){
            System.err.println("First param should be base url where jira can be find, second - license key.");
            System.exit(1);
        }
        String baseUrl = args[0];
        String license = args[1];

        log("BaseUrl = " + baseUrl);
        log("License contains [" + license.length() + "] symbols. First part: [" + license.substring(0, 15) + "]");
        driver = new HtmlUnitDriver(true);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);

        driver.get(baseUrl);
        log("Initial page was opened");
        id("jira-setupwizard-submit").click();
        logAndSleep("Left default values, opening license page, this may take awhile...");

        id("jira-setupwizard-licenseSetupSelectorexistingLicense").click();
        id("licenseKey").sendKeys(license);
        css("aui-button-primary").click();
        logAndSleep("License was specified, starting to create an admin user");

        name("fullname").sendKeys("admin");
        name("email").sendKeys("admin@admin.org");
        name("username").sendKeys("admin");
        name("password").sendKeys("admin");
        name("confirm").sendKeys("admin");
        id("jira-setupwizard-submit").click();

        logAndSleep("admin/admin user was created, opening 'Enable Atlassian Analytics for JIRA'");
        id("jira-setupwizard-disable-analytics-submit").click();
        logAndSleep("No need in Google Analytics, opening 'Set Up Email Notifications'...");

        id("jira-setupwizard-email-notifications-disabled").click();
        id("jira-setupwizard-submit").click();
        logAndSleep("Don't care about email notifications right now, finishing configuration");

        driver.quit();
    }

    private static WebElement css(String cssClass) {
        return driver.findElement(By.className(cssClass));
    }

    private static WebElement name(String elementName) {
        return driver.findElement(By.name(elementName));
    }


    private static WebElement id(String elementId) {
        return driver.findElement(By.id(elementId));
    }

    private static void logAndSleep(String string) throws InterruptedException {
        log(string);
        Thread.sleep(1000);
    }

    private static void log(String string) {
        System.out.println(string);
    }
}
