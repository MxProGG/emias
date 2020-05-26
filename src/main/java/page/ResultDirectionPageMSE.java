package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

public class ResultDirectionPageMSE {


    private WebDriver driver;


    public ResultDirectionPageMSE(WebDriver driver){
        this.driver = driver;
    }

    private By snackBarSave = By.xpath("//span[text()='Сведения о результатах проведённой МСЭ сохранены']");
    private By buttonSave = By.xpath("//button[contains(.,'Сохранить')]");
    private By input3 = By.xpath("//span[text()=' 3. Номер акта медико-социальной экспертизы ']//..//..//div[2]//input");
    private By buttonNewInput5 = By.xpath("//legend[text()='5. Виды стойких расстройств функций организма и степень их выраженности']//..//button");
    private By buttonDeleteInput5 = By.xpath("//legend[text()='5. Виды стойких расстройств функций организма и степень их выраженности']//..//button[@ng-reflect-message='Удалить']");
    private By input5TypeDysfunc =By.xpath("//st-autocomplete[@name='typeDysfunc']//input");
    private By input5TypeDysfuncSelect = By.xpath("//span[contains(.,' 1 - Нарушения психических функций ')]");
    private By input5_2 = By.xpath("//div[text()=' Степень выраженности ']//..//..//div[4]//input");
    private By input5_2Select = By.xpath("//span[contains(.,' 1 - Незначительные нарушения ')]");
    private By input5_3 = By.xpath("//div[text()=' Степень выраженности в % ']//..//..//div[4]/div[2]//input");
    private By buttonNewInput6 = By.xpath("//legend[text()='6. Ограничения основных категорий жизнедеятельности и степень их выраженности']//..//button");
    private By input6LifeCategory =By.xpath("//st-autocomplete[@name='lifeCategory']//input");
    private By input6LifeCategorySelect = By.xpath("//span[contains(.,' 1 - Способность к самообслуживанию ')]");
    //private By input6_2 = By.xpath("//div[text()=' Степень выраженности ']//..//..//div[4]//input");
    private By input7_1Inv = By.xpath("//st-autocomplete[@formcontrolname='inv']//input");
    private By input7_1InvDel = By.xpath("//st-autocomplete[@formcontrolname='inv']//button");
    private By input7_1InvSelect = By.xpath("//span[contains(.,' 1 - I гр ')]");
    private By input7_2Inv = By.xpath("//st-autocomplete[@formcontrolname='invReason']//input");
    private By input7_2InvDel = By.xpath("//st-autocomplete[@formcontrolname='invReason']//button");
    private By input7_2InvSelect = By.xpath("//span[contains(.,' 4 - Общее заболевание ')]");
    private By input7_OtherInv = By.xpath("//textarea[@formcontrolname='invReasonOther']");
    private By input7_3Inv = By.xpath("//st-autocomplete[@formcontrolname='addNoteInv']//input");
    private By input7_3InvDel = By.xpath("//st-autocomplete[@formcontrolname='addNoteInv']//button");
    private By input7_3InvSelect = By.xpath("//span[contains(.,' 1 - по зрению ')]");
    private By input7_4Inv = By.xpath("//date-time[@formcontrolname='invEndDate']//input");
    private By input7_4InvDel = By.xpath("//date-time[@formcontrolname='invEndDate']//button[@aria-label='Clear']");
    private By input7_5Inv = By.xpath("//date-time[@formcontrolname='nextDateMse']//input");
    private By input7_5InvDel = By.xpath("//date-time[@formcontrolname='nextDateMse']//button[@aria-label='Clear']");
    private By input7_6Inv = By.xpath("//input[@formcontrolname='profLossDegree']");
    private By input7_6InvDel = By.xpath("//st-autocomplete[@formcontrolname='profLossDegree']//button[@aria-label='Clear']");
    private By input7_7Inv = By.xpath("//st-autocomplete[@formcontrolname='profLossPeriod']//input");
    private By input7_7InvDel = By.xpath("//st-autocomplete[@formcontrolname='profLossPeriod']//button[@aria-label='Clear']");
    private By input7_7InvSelect = By.xpath("//span[contains(.,' 2 - Один год ')]");
    private By input7_8Inv = By.xpath("//mat-checkbox[@formcontrolname='isDevelopedIpra']");
    private By input7_9Inv = By.xpath("//mat-checkbox[@formcontrolname='isDevelopedIpraAccident']");


    public String newResult() {
        editResulDirection();
        driver.findElement(buttonSave).click();
        (new WebDriverWait(driver, 20)).until(visibilityOfAllElementsLocatedBy(snackBarSave));
        return driver.findElement(snackBarSave).getText();
    }

    private void editResulDirection() {
        driver.findElement(input3).sendKeys("TEST3");
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//legend[text()='5. Виды стойких расстройств функций организма и степень их выраженности']")));
        driver.findElement(buttonNewInput5).click();
        driver.findElement(input5TypeDysfunc).sendKeys("1");
        driver.findElement(input5TypeDysfuncSelect).click();
        driver.findElement(input5_2).click();
        driver.findElement(input5_2Select).click();
        driver.findElement(input5_3).sendKeys("33");
        //driver.findElement(buttonNewInput6).click();
        //driver.findElement(input6LifeCategory);
        //driver.findElement(input6LifeCategorySelect).click();
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//legend[text()='6. Ограничения основных категорий жизнедеятельности и степень их выраженности']")));
        driver.findElement(input7_1Inv).click();
        driver.findElement(input7_1InvSelect).click();
        driver.findElement(input7_2Inv).click();
        driver.findElement(input7_2InvSelect).click();
        driver.findElement(input7_OtherInv).sendKeys("TestOther");
        driver.findElement(input7_3Inv).click();
        driver.findElement(input7_3InvSelect).click();
        driver.findElement(input7_4Inv).sendKeys("12.03.2020");
        driver.findElement(input7_5Inv).sendKeys("12.12.2021");
        driver.findElement(input7_6Inv).sendKeys("44");
        driver.findElement(input7_7Inv).click();
        driver.findElement(input7_7InvSelect).click();
        driver.findElement(input7_8Inv).click();
        driver.findElement(input7_9Inv).click();
    }

    public String clearResult() throws InterruptedException {
        driver.findElement(input3).sendKeys(Keys.CONTROL + "a");
        driver.findElement(input3).sendKeys(Keys.DELETE);
        //driver.findElement(input3).clear(); //Надо разобраться почему, после сохранения значения остаются
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//legend[text()='5. Виды стойких расстройств функций организма и степень их выраженности']")));
        driver.findElement(buttonDeleteInput5).click();
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//legend[text()='6. Ограничения основных категорий жизнедеятельности и степень их выраженности']")));
        driver.findElement(input7_1InvDel).click();
        driver.findElement(input7_2InvDel).click();
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//legend[text()='5. Виды стойких расстройств функций организма и степень их выраженности']")));
        driver.findElement(input7_OtherInv).sendKeys(Keys.CONTROL + "a");
        driver.findElement(input7_OtherInv).sendKeys(Keys.DELETE);
        //driver.findElement(input7_OtherInv).clear();
        driver.findElement(input7_3InvDel).click();
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//span[text()=' 7.8. Разработана индивидуальная программа реабилитации или абилитации инвалида (ребенка-инвалида) ']")));
        driver.findElement(By.xpath("//span[text()=' 7.4. Инвалидность установлена на срок ']")).click();
        driver.findElement(input7_4InvDel).click();
        driver.findElement(input7_5InvDel).click();
        driver.findElement(input7_6Inv).sendKeys(Keys.CONTROL + "a");
        driver.findElement(input7_6Inv).sendKeys(Keys.DELETE);
        //driver.findElement(input7_6Inv).clear(); //Надо разобраться почему, после сохранения значения остаются
        driver.findElement(input7_6Inv).sendKeys("0");
        driver.findElement(input7_7InvDel).click();
        driver.findElement(input7_8Inv).click();
        driver.findElement(input7_9Inv).click();
        driver.findElement(buttonSave).click();
        (new WebDriverWait(driver, 20)).until(visibilityOfAllElementsLocatedBy(snackBarSave));
        String ms = driver.findElement(snackBarSave).getText();
        sleep(5000);
        return ms;
    }
}
