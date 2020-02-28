import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;

    }

    private By linkMSE = By.xpath("//span[text()=\"Журнал направлений на МСЭ\"]");
    private By buttonResetFilter = By.xpath("//input[@id='reset_filter']");
    private By buttonSearch = By.xpath("//input[@id='btnfindtapgrid1']");
    private By inputRow = By.xpath("//input[@id='sinptapgrid1']");


    public void linkPage(String linktext) throws InterruptedException {
        String linkXpath = "//a[@href='"+ linktext + "']";
        By linkFormat = By.xpath(linkXpath);
        Thread.sleep(2000);
       /* WebElement element = driver.findElement(By.xpath(linkXpath));
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(element));
        WebDriverWait wait = new WebDriverWait (driver, 12);
       wait.until(ExpectedConditions.elementToBeClickable(linkFormat));*/
       // wait.until(ExpectedConditions.elementToBeSelected(linkFormat));
        /* JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);",linkFormat);*/
        //Object executeScript = jse.executeScript("javascript:window.scrollBy(250,350)", linkFormat);
        driver.findElement(linkFormat).click();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        /*ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));*/
        }


    public void newMSE(){
        driver.findElement(buttonResetFilter).click();
        driver.findElement(inputRow).sendKeys("Темников");
        driver.findElement(buttonSearch).click();
        driver.findElement(By.xpath("//td[@title=" + 2 + "]")).click();
        driver.findElement(By.xpath("//a[@role='menuitem' and contains(text(),'Редактировать')]")).click();
        WebElement elementAction = driver.findElement(By.xpath("//a[text()='Действия']"));
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(elementAction));
        elementAction.click();
        driver.findElement(By.xpath("//span[text()='Направление на МСЭ']")).click();

        //driver.switchTo().window("Медицинская Информационная Система");

        /*List<WebElement> idTap = driver.findElements(By.xpath("//tr[@id=*]"));
        for (int i = 1; i<idTap.size(); i++){
           driver.findElement(By.xpath("//td[@title=" + i + "]")).click();
        }
        Integer countRowTable = driver.findElements(By.xpath("//tr[@id=*]")).size();*/

    }


}

