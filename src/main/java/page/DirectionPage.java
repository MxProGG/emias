package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

public class DirectionPage {

    private WebDriver driver;

    public DirectionPage(WebDriver driver){
        this.driver = driver;
    }

    private By buttonSave = By.xpath("//button[contains(.,'Сохранить')]");
    private By snackBarSave = By.xpath("//span[text()='Направление сохранено']");
    private By inputProtokolVK = By.xpath("//st-autocomplete[@field='protocolVk']");
    //private By inputProtokolVKSelect = By.xpath("//mat-option[contains(.,' 545 ')]");
    private By buttonClearVK = By.xpath("//button[@id='4198BD84-7A21-4E38-B36B-3ECB2E956408']");
    private By buttonAddVK = By.xpath("//button[contains(.,'Добавить')]");
    private By buttonEditVK = By.xpath("//button[contains(.,'Редактировать')]");
    private By inputTargetMSE = By.xpath("//span[contains(.,' 5.1 установление группы инвалидности')]//..");
    private By inputWhere13 = By.xpath("//st-autocomplete[@formcontrolname='medicalPlace']");
    private By inputWhere13Select = By.xpath("//span[contains(.,' 1 - Амбулаторно-поликлиническое учреждение ')]");
    private By checkFirst18 = By.xpath("//mat-radio-group[@formcontrolname='isFirstExamination']//mat-radio-button[1]");
    private By checkSecond18 = By.xpath("//mat-radio-group[@formcontrolname='isFirstExamination']//mat-radio-button[2]");
    private By input19_3 = By.xpath("//st-autocomplete[@formcontrolname='invPeriod']");
    private By input19_3Select = By.xpath("//span[contains(.,' 1 - Один год ')]");
    private By input23 = By.xpath("//div[text()=' 23. Анамнез заболевания ']//../div[2]//textarea");
    private By input24 = By.xpath("//div[text()=' 24. Анамнез жизни ']//../div[2]//textarea");
    private By input28 = By.xpath("//div[contains(text(),' 28. Состояние здоровья гражданина при направлении на медико-социальную экспертизу*')]/following::div[1]//textarea");
    private By input34 = By.xpath("//div[contains(text(),' 34. Рекомендуемые мероприятия по медицинской реабилитации')]/following::div[1]//textarea");
    private By input35 = By.xpath("//div[contains(text(),' 35. Рекомендуемые мероприятия по реконструктивной хирургии')]/following::div[1]//textarea");
    private By input36 = By.xpath("//div[contains(text(),' 36. Рекомендуемые мероприятия по протезированию и ортезированию')]/following::div[1]//textarea");
    private By input37 = By.xpath("//div[contains(text(),' 37. Санаторно-курортное лечение')]/following::div[1]//textarea");






    public String newDirection() throws InterruptedException {

        sleep(5000);
        for (String handle1 : driver.getWindowHandles()) {
            driver.switchTo().window(handle1);
        }
        //WebElement elementPatient = driver.findElement(By.xpath("//div[@mattooltipclass='tooltip-MKAB']"));
        //(new WebDriverWait(driver, 20)).until(visibilityOfAllElementsLocatedBy(By.xpath("//div[@mattooltipclass='tooltip-MKAB']//span[contains(.,'Темников')]")));
        editDirection();
        driver.findElement(buttonSave).click();
        (new WebDriverWait(driver, 20)).until(visibilityOfAllElementsLocatedBy(snackBarSave));

        return driver.findElement(snackBarSave).getText();

        /*List<WebElement> idTap = driver.findElements(By.xpath("//tr[@id=*]"));
        for (int i = 1; i<idTap.size(); i++){
           driver.findElement(By.xpath("//td[@title=" + i + "]")).click();
        }
        Integer countRowTable = driver.findElements(By.xpath("//tr[@id=*]")).size();*/
    }

    public void editDirection() {
        driver.findElement(buttonClearVK).click();
        driver.findElement(inputProtokolVK).click();
        driver.findElement(By.xpath("//mat-option[contains(.,'" + 545 + "')]")).click();
        driver.findElement(inputTargetMSE).click();
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//legend[text()='11. Адрес места жительства*']")));
        driver.findElement(inputWhere13).click();
        driver.findElement(inputWhere13Select).click();
        driver.findElement(checkSecond18).click();
        driver.findElement(input19_3).click();
        driver.findElement(input19_3Select).click();
        driver.findElement(input23).sendKeys("TEST23");
        driver.findElement(input24).clear();
        driver.findElement(input24).sendKeys("TEST24");
        driver.findElement(input28).sendKeys("TEST28");
        driver.findElement(input34).sendKeys("TEST34");
        driver.findElement(input35).sendKeys("TEST35");
        driver.findElement(input36).sendKeys("TEST36");
        driver.findElement(input37).sendKeys("TEST37");

        //driver.findElement(buttonSave).click();
        //(new WebDriverWait(driver, 20)).until(visibilityOfAllElementsLocatedBy(snackBarSave));
        //return driver.findElement(snackBarSave).getText();

    }

}
