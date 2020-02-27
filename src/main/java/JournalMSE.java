import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JournalMSE {

    private WebDriver driver;

    public JournalMSE(WebDriver driver){
        this.driver = driver;
    }

    private By journalTitle = By.xpath("//div[@class='title-container']");
    private By buttonSearch = By.xpath("//div//a[text()=\" Найти \"]");
    private By buttonSaveExcel = By.xpath("//div//button[text()=\" Сохранить Excel \"]");
    private By buttonClear = By.xpath("//div//a[text()=\" очистить \"]");
    private By inputFIO = By.xpath("//input[@id='mat-input-0']");
    private By inputDateFrom = By.xpath("//input[@id='mat-input-5']");
    private By inputDateBy = By.xpath("//input[@id='mat-input-6']");
    private By inputStatus = By.xpath("//st-autocomplete[@formcontrolname='status']");
    private By inputConclusion = By.xpath("//st-autocomplete[@formcontrolname='conclusion']");
    private By inputDocCommission = By.xpath("//st-autocomplete[@formcontrolname='docCommission']");
    private By inputAuthor = By.xpath("//st-autocomplete[@formcontrolname='author']");
    //private By menuAction = By.xpath("//datatable-body-row[@ng-reflect-row-index=2]//div[@class='datatable-body-cell-label']//i");
    private By deleteAction = By.xpath("//button[text()=' Удалить ']");
    private By snackBarDelete = By.xpath("//span[text()='Направление на МСЭ удалено успешно.']");


    public String journalTitle(){ return driver.findElement(journalTitle).getText(); }

    public JournalMSE typeFIO (String fio){
        driver.findElement(inputFIO).sendKeys(fio);
        return this;
    }

    public JournalMSE typeDateFrom (String dateFrom){
        driver.findElement(inputDateFrom).sendKeys(dateFrom);
        return this;
    }

    public JournalMSE typeDateBy (String dateBy){
        driver.findElement(inputDateBy).sendKeys(dateBy);
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
        //buttonLogin.click();
        return new JournalMSE(driver);
    }

    public JournalMSE clickSearch () {
        driver.findElement(buttonSearch).click();
        //buttonLogin.click();
        return new JournalMSE(driver);
    }

    public JournalMSE clickClear(){
        driver.findElement(buttonClear).click();
        return new JournalMSE(driver);
    }

    public String clickMenuDelete(int indexRow){
        driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index=" + indexRow + "]//div[@class='datatable-body-cell-label']//i")).click();
        driver.findElement(deleteAction).click();
        return driver.findElement(By.xpath("//span[text()='Направление на МСЭ удалено успешно.']")).getText();
    }


}
