package com.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

public class JournalMSE {

    private WebDriver driver;
    public JournalMSE(WebDriver driver){
        this.driver = driver;
    }

    private By journalTitle = By.xpath("//div[@class='title-container']");
    private By buttonSearch = By.xpath("//div//a[text()=\" Найти \"]");
    private By buttonSaveExcel = By.xpath("//div//button[text()=\" Сохранить Excel \"]");
    private By buttonClear = By.xpath("//div//a[text()=\" очистить \"]");
    private By inputFIO = By.xpath("//input[@formcontrolname=\"fio\"]");
    private By inputDateFrom = By.xpath("//input[@id='mat-input-5']");
    private By inputDateBy = By.xpath("//input[@id='mat-input-6']");
    private By inputStatus = By.xpath("//st-autocomplete[@formcontrolname='status']");
    private By inputConclusion = By.xpath("//st-autocomplete[@formcontrolname='conclusion']");
    private By inputDocCommission = By.xpath("//st-autocomplete[@formcontrolname='docCommission']");
    private By inputAuthor = By.xpath("//st-autocomplete[@formcontrolname='author']");
    private By deleteAction = By.xpath("//button[text()=' Удалить ']");
    private By BasedOnAction = By.xpath("//button[text()=' Создать на основании ']");
    private By printDirection = By.xpath("//button[text()=' Направление на МСЭ(088/у) ']");
    private By printResultDirection = By.xpath("//button[text()=' Сведения о результатах проведенной МСЭ ']");
    private By snackBarDelete = By.xpath("//span[text()='Направление на МСЭ удалено успешно.']");
    private By viewResultDirection = By.xpath("//button[text()=' Просмотр сведений о результатах проведенной МСЭ ']");



    public String journalTitle(){ return driver.findElement(journalTitle).getText(); }

    public JournalMSE typeFIO (String fio){
        driver.findElement(inputFIO).sendKeys(fio);
        return this;
    }

    public JournalMSE typeDateFrom (String dateFrom){
        driver.findElement(inputDateFrom).sendKeys(dateFrom);
        return this;
    }

    public JournalMSE typeDateTo (String dateTo){
        driver.findElement(inputDateBy).sendKeys(dateTo);
        return this;
    }

    public JournalMSE typeStatus (String status){
        driver.findElement(inputStatus).click();
        driver.findElement(By.xpath("//mat-option[@title='" + status + "']")).click();
        return this;
    }

    public JournalMSE typeConclusion (String conclusion){
        driver.findElement(inputConclusion).click();
        driver.findElement(By.xpath("//mat-option[@title='" + conclusion + "']")).click();
        return this;
    }

    public JournalMSE typeDocCommission (String docCommission){
        driver.findElement(inputDocCommission).click();
        driver.findElement(By.xpath("//mat-option[contains(@title,'" + docCommission + "')]")).click();
        return this;
    }

    public JournalMSE typeAuthor (String author){
        driver.findElement(inputAuthor).click();
        driver.findElement(By.xpath("//mat-option[contains(@title,'" + author + "')]")).click();
        return this;
    }

    public int countRowTable(){return driver.findElements(By.xpath("//datatable-body-row[@ng-reflect-row-index]")).size(); }


    public JournalMSE clickSaveExcel () {
        driver.findElement(buttonSaveExcel).click();
        new JournalMSE(driver);
        return this;
    }

    public JournalMSE clickSearch () {
        driver.findElement(buttonSearch).click();
        new JournalMSE(driver);
        return this;
    }

    public JournalMSE clickClear(){
        driver.findElement(buttonClear).click();
        new JournalMSE(driver);
        return this;
    }

    public String clickMenuDelete(int indexRow){
        driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index=" + indexRow + "]//div[@class='datatable-body-cell-label']//i")).click();
        if (driver.findElement(deleteAction).isEnabled()){
            driver.findElement(deleteAction).click();
            (new WebDriverWait(driver, 20)).until(visibilityOfAllElementsLocatedBy(snackBarDelete));
            return driver.findElement(snackBarDelete).getText();
        }
        return "Кнопка Удалить не активна!";
    }

    public void clickMenuBasedOn(int indexRow){
        driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index=" + indexRow + "]//div[@class='datatable-body-cell-label']//i")).click();
        driver.findElement(BasedOnAction).click();
    }

    public JournalMSE clickMenuPrintDirection(int indexRow){
        driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index=" + indexRow + "]//div[@class='datatable-body-cell-label']//div[2]//i")).click();
        driver.findElement(printDirection).click();
        return this;
    }

    public JournalMSE clickMenuPrintResultDirection(int indexRow){
        driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index=" + indexRow + "]//div[@class='datatable-body-cell-label']//div[2]//i")).click();
        driver.findElement(printResultDirection).click();
        return this;
    }

    public void clickMenuResultDirection(int indexRow){
        driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index=" + indexRow + "]//div[@class='datatable-body-cell-label']//i[3]")).click();
        driver.findElement(viewResultDirection).click();
    }

    public String[] deleteFile(String direct){
        File dir = new File(direct);
        String[] listFile = dir.list();
        for (File item : dir.listFiles()) {
            item.delete(); }
        return listFile;
    }
}
