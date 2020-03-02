package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DirectionPage {

    private WebDriver driver;

    public DirectionPage(WebDriver driver){
        this.driver = driver;
    }

    private By buttonSave = By.xpath("//button[contains(.,'Сохранить')]");
    private By snackBarSave = By.xpath("//span[text()='Направление сохранено']");





    public String newDirection() throws InterruptedException {

        Thread.sleep(5000);
        for (String handle1 : driver.getWindowHandles()) {
            driver.switchTo().window(handle1);
        }
        //WebElement elementPatient = driver.findElement(By.xpath("//div[@mattooltipclass='tooltip-MKAB']"));
        //(new WebDriverWait(driver, 20)).until(visibilityOfAllElementsLocatedBy(By.xpath("//div[@mattooltipclass='tooltip-MKAB']//span[contains(.,'Темников')]")));
        driver.findElement(buttonSave).click();
        return driver.findElement(snackBarSave).getText();

        /*List<WebElement> idTap = driver.findElements(By.xpath("//tr[@id=*]"));
        for (int i = 1; i<idTap.size(); i++){
           driver.findElement(By.xpath("//td[@title=" + i + "]")).click();
        }
        Integer countRowTable = driver.findElements(By.xpath("//tr[@id=*]")).size();*/
    }


}
