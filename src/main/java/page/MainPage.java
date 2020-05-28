package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class MainPage {

    private WebDriver driver;



    public MainPage(WebDriver driver){this.driver = driver;}

    //private By linkMSE = By.xpath("//span[text()=\"Журнал направлений на МСЭ\"]");
    private By buttonResetFilter = By.xpath("//input[@id='reset_filter']");
    private By buttonSearch = By.xpath("//input[@id='btnfindtapgrid1']");
    private By inputRow = By.xpath("//input[@id='sinptapgrid1']");
    private By menuEditTAP = By.xpath("//a[@role='menuitem' and contains(text(),'Редактировать')]");
    private By tabAction = By.xpath("//a[text()='Действия']");
    private By redirectMSE = By.xpath("//span[text()='Направление на МСЭ']");
    private By logOut = By.xpath("//span[text()='Выход']");
    private By inputTAP_DateFrom = By.xpath("//input[@id='Show_TAP_DateFrom']");
    private By inputTAP_DateTo= By.xpath("//input[@id='Show_TAP_DateTo']");


    public void logOut (){driver.findElement(logOut).click();}

    public void linkPage(String linktext) throws InterruptedException {
        String linkXpath = "//a[@href='"+ linktext + "']";
        By linkFormat = By.xpath(linkXpath);
        Thread.sleep(5000);
        driver.findElement(linkFormat).click();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        //return new MainPage(driver);
    }


    public void searchTap() {
        driver.findElement(buttonResetFilter).click();
        driver.findElement(inputRow).sendKeys("Авто Тест МСЭ");
        driver.findElement(inputTAP_DateFrom).clear();
        driver.findElement(inputTAP_DateFrom).sendKeys("29.05.2020");
        driver.findElement(inputTAP_DateTo).clear();
        driver.findElement(inputTAP_DateTo).sendKeys("29.05.2020");
        driver.findElement(buttonSearch).click();
        driver.findElement(By.xpath("//td[@title=" + 1 + "]")).click();
        driver.findElement(menuEditTAP).click();
        WebElement elementAction = driver.findElement(tabAction);
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(elementAction));
        elementAction.click();
        driver.findElement(redirectMSE).click();
    }


}

