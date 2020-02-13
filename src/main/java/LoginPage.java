import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class LoginPage {
    private WebDriver driver;

    public LoginPage (WebDriver driver){
        this.driver = driver;
    }
    //private WebElement login;
    //private WebElement pass;

    private By loginInput = By.xpath("//input[@id='Login']");
    private By passwordInput = By.xpath("//input[@id='Password']");
    private By buttonLogin = By.xpath("//input[@id='loginBtn']");
    private By checkBoxRemember = By.xpath("//input[@id='Remember']");
    private By linkHelp = By.xpath("//a [@href ='http://confluence.softrust.ru/pages/viewpage.action?pageId=7406016']");
    private By heading = By.xpath("//div[text()=\"TrustMed\"]");
    private By errorLogin = By.xpath("//span[@class='error-login']");

    public MainPage clickLogin () {
        driver.findElement(buttonLogin).click();
        return new MainPage(driver);
    }

    public LoginPage typeLogin (String username) {
        driver.findElement(loginInput).sendKeys(username);
        return this;
    }

    public LoginPage typePassword (String password) {
        driver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    public void clickRemember () {
        if (!driver.findElement(checkBoxRemember).isSelected()){
        driver.findElement(checkBoxRemember).click();}
    }

    public void clickLinkHelp () {
        driver.findElement(linkHelp).click();
    }

    public String getHeadingText(){
        return driver.findElement(heading).getText();
    }

    public String getErorrText() { return driver.findElement(errorLogin).getText(); }

    public MainPage entrySystem (String username, String password ){
        String mainWindow = driver.getWindowHandle();
        this.clickLinkHelp();
        driver.switchTo().window(mainWindow);
        this.typeLogin(username);
        this.typePassword(password);
        this.clickRemember();
        this.clickLogin();
        return new MainPage(driver);
    }

}
