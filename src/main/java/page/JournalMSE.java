package page;
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
    private By inputFIO = By.xpath("//input[@id='mat-input-0']");
    private By inputDateFrom = By.xpath("//input[@id='mat-input-5']");
    private By inputDateBy = By.xpath("//input[@id='mat-input-6']");
    private By inputStatus = By.xpath("//st-autocomplete[@formcontrolname='status']");
    private By inputConclusion = By.xpath("//st-autocomplete[@formcontrolname='conclusion']");
    private By inputDocCommission = By.xpath("//st-autocomplete[@formcontrolname='docCommission']");
    private By inputAuthor = By.xpath("//st-autocomplete[@formcontrolname='author']");
    private By deleteAction = By.xpath("//button[text()=' Удалить ']");
    private By snackBarDelete = By.xpath("//span[text()='Направление на МСЭ удалено успешно.']");
    private By viewResultDirection = By.xpath("//button[text()=' Просмотр сведений о результатах проведенной МСЭ ']");



    public String journalTitle(){ return driver.findElement(journalTitle).getText(); }

    public void typeFIO (String fio){
        driver.findElement(inputFIO).sendKeys(fio);
        //return this;
    }

    public void typeDateFrom (String dateFrom){
        driver.findElement(inputDateFrom).sendKeys(dateFrom);
    }

    public void typeDateTo (String dateTo){
        driver.findElement(inputDateBy).sendKeys(dateTo);
    }

    public void typeStatus (String status){
        driver.findElement(inputStatus).click();
        driver.findElement(By.xpath("//mat-option[@title='" + status + "']")).click();
    }

    public void typeConclusion (String conclusion){
        driver.findElement(inputConclusion).click();
        driver.findElement(By.xpath("//mat-option[@title='" + conclusion + "']")).click();
    }

    public void typeDocCommission (String docCommission){
        driver.findElement(inputDocCommission).click();
        driver.findElement(By.xpath("//mat-option[contains(@title,'" + docCommission + "')]")).click();
    }

    public void typeAuthor (String author){
        driver.findElement(inputAuthor).click();
        driver.findElement(By.xpath("//mat-option[contains(@title,'" + author + "')]")).click();
    }

    public int countRowTable(){return driver.findElements(By.xpath("//datatable-body-row[@ng-reflect-row-index]")).size(); }


    public JournalMSE clickSaveExcel () {
        driver.findElement(buttonSaveExcel).click();
        return new JournalMSE(driver);
    }

    public void clickSearch () {
        driver.findElement(buttonSearch).click();
        new JournalMSE(driver);
    }

    public void clickClear(){
        driver.findElement(buttonClear).click();
        new JournalMSE(driver);
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
