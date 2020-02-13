import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Set;

public class LoginPage {
    private WebDriver driver;

    public LoginPage (WebDriver driver){
        this.driver = driver;
    }

   /* @FindBy (xpath = "//input[@id='Login']")
    private WebElement loginInput;*/
    private By loginInput = By.xpath("//input[@id='Login']");

    /*@FindBy (xpath = "//input[@id='Password']")
    private WebElement passwordInput;*/
    private By passwordInput = By.xpath("//input[@id='Password']");


    /*@FindBy (xpath = "//input[@id='loginBtn']")
    private WebElement buttonLogin;*/
    private By buttonLogin = By.xpath("//input[@id='loginBtn']");


    @FindBy (xpath = "//input[@id='Remember']")
    private WebElement checkBoxRemember;

    @FindBy (xpath = "//a [@href ='http://confluence.softrust.ru/pages/viewpage.action?pageId=7406016']")
    private WebElement linkHelp;

    @FindBy (xpath = "//div[text()=\"TrustMed\"]")
    private WebElement heading;

    @FindBy (xpath = "//span[@class='error-login']")
    private WebElement errorLogin;


    public LoginPage typeLogin (String username) {
        driver.findElement(loginInput).sendKeys(username);
        //loginInput.sendKeys(username);
        return this;
    }

    public LoginPage typePassword (String password) {
        driver.findElement(passwordInput).sendKeys(password);
        //passwordInput.sendKeys(password);
        return this;
    }

    public MainPage clickLogin () {
        driver.findElement(buttonLogin).click();
        //buttonLogin.click();
        return new MainPage(driver);
    }

    public void clickRemember () {
        if (!checkBoxRemember.isSelected()){
        checkBoxRemember.click();}
    }

    public void clickLinkHelp () {
        linkHelp.click();

    }

    public String getHeadingText(){
        return heading.getText();
    }

    public String getErorrText() { return errorLogin.getText(); }

    public MainPage entrySystem (String username, String password ){
        //String mainWindow = driver.getWindowHandle();
        //this.clickLinkHelp();
        //driver.switchTo().window(mainWindow);
        this.typeLogin(username);
        this.typePassword(password);
        this.clickRemember();
        this.clickLogin();
        return new MainPage(driver);
    }

}
