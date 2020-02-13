import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;

    }

    private By linkMSE = By.xpath("//span[text()=\"Журнал направлений на МСЭ\"]");

    public void linkPage(String linktext){
        String form ="//span[text()=" + "'" + linktext + "'" + "]";
        By linkFormat = By.xpath(form);
        driver.findElement(linkFormat).click();
        }



}

