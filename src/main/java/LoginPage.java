import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    public LoginPage (WebDriver driver){
        this.driver = driver;
    }
    //private WebElement login;
    //private WebElement pass;

    private By login = By.xpath("//input[@id='Login']");
    private By password = By.xpath("//input[@id='Password']");
    private By buttonLogin = By.xpath("//input[@id='loginBtn']");


}
