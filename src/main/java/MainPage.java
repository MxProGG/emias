import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;

    }

    private By linkMSE = By.xpath("//span[text()=\"Журнал направлений на МСЭ\"]");

    public void linkPage(String linktext){

        //String linkXpath ="//span[text()=" + "'" + linktext + "'" + "]//";
        String linkXpath = "//a[@href='"+ linktext + "']";
        By linkFormat = By.xpath(linkXpath);
        driver.findElement(linkFormat).click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        //driver.findElement(By.xpath("//a[@href='#MseIndexNg']")).click();
        }


}

