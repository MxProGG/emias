import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import sql.DataSQL;

import java.util.List;

public class DirectionMSETest {
    //Logger logger;
    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp(){
//      logger = logger.getLogger("emias_2_3");
//      PropertyConfigurator.configure("src/main/resources/log4j.properties");
        WebDriverInstall driverInstall = new WebDriverInstall();
        driver = driverInstall.setUpDriver();
        loginPage = new LoginPage(driver);
        //page.LoginPage loginPage = PageFactory.initElements(driver, page.LoginPage.class);
    }

    @Test //Переход из Веб Мис в Журнал направлений на МСЭ
    public void linkMSE(){
        MainPage mainPage = loginPage.entrySystem("admin","11");
        try {
            mainPage.linkPage("#MseIndexNg");
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
        JournalMSE journalMSE = new JournalMSE(driver);
        String journalTitle = journalMSE.journalTitle();
        Assert.assertEquals("Журнал направлений на медико-социальную экспертизу (МСЭ)",journalTitle);
    }

    @Test //Поиск направления по всем полям
    public void journalSearch(){
        //logger.info("11ffff");
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeFIO("Темников");
        journalMSE.typeDateFrom("14.02.2020");
        journalMSE.typeDateTo("29.02.2020");
        journalMSE.typeStatus("Зарегистрирован");
        journalMSE.typeConclusion("Установлена");
        journalMSE.typeDocCommission("Мастякова");
        journalMSE.typeAuthor("Иванов");
        journalMSE.clickSearch();
        //Assert.assertEquals(1,journalMSE.countRowTable());
        journalMSE.clickClear();
        journalMSE.clickSearch();
        Assert.assertEquals(10,journalMSE.countRowTable());
    }

    @Test //Кейс 4.1 Поиск направления по ФИО и периодам выдачи
    public void journalSearch_4_1() throws ParseException {
        linkMSE();
        String DateFrom="14.02.2020";
        String DateTo="29.02.2020";
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateFrom=format.parse(DateFrom);
        Date dateTo=format.parse(DateTo);
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeFIO("Темников");
        journalMSE.typeDateFrom(DateFrom);
        journalMSE.typeDateTo(DateTo);
        journalMSE.clickSearch();
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String FIOLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[3]")).getText();
            Assert.assertEquals("Темников Дмитрий Олегович", FIOLabel);
            String DateLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[2]")).getText();
            Date dateY=format.parse(DateLabel);
            Assert.assertTrue("Дата в результате не входит в диапазон поиска!!",(dateY.after(dateFrom) && dateY.before(dateTo)) || dateY.equals(dateFrom) || dateY.equals(dateTo));
        }
        journalMSE.clickClear();
    }

    @Test //Кейс 4.2 Поиск направления по Статусу и Членам комиссии
    public void journalSearch_4_2(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeStatus("Зарегистрирован");
        journalMSE.typeDocCommission("Мастякова");
        journalMSE.clickSearch();
        String statusLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='0']//span[text()=' Зарегистрирован ']")).getText();
        Assert.assertEquals("Зарегистрирован", statusLabel);
        journalMSE.clickClear();
    }

    @Test
    public void journalDeleteMSE(){

        //linkMSE();
        //JournalMSE journalMSE = new JournalMSE(driver);
        //Assert.assertEquals(10,journalMSE.countRowTable());
       // String messageDelete = journalMSE.clickMenuDelete(0);
        //Assert.assertEquals("Направление на МСЭ удалено успешно.",messageDelete);
    }

    @Test
    public void createMSE()  {
        MainPage mainPage = loginPage.entrySystem("admin","11");
        try {
            mainPage.linkPage("/mis/test2/Tap");
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
        mainPage.searchTap();
        DirectionPageMSE directionPageMSE = new DirectionPageMSE(driver);
        try {
            String messageSave = directionPageMSE.newDirection();
            Assert.assertEquals("Направление сохранено",messageSave);
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createResultDirection()  {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        journalMSE.clickMenuResultDirection(0);
        ResultDirectionPageMSE resultDirectionPageMSE = new ResultDirectionPageMSE(driver);
        String messageSave = resultDirectionPageMSE.newResult();
        Assert.assertEquals("Сведения о результатах проведённой МСЭ сохранены",messageSave);
    }

    @Test
    public void clearResultDirection() {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        journalMSE.clickMenuResultDirection(0);
        ResultDirectionPageMSE resultDirectionPageMSE = new ResultDirectionPageMSE(driver);
        try {
            String messageSave = resultDirectionPageMSE.clearResult();
            Assert.assertEquals("Сведения о результатах проведённой МСЭ сохранены", messageSave);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown(){
        /*File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "./target/screenshots/" + screenshot.getName();
        try {
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e){e.printStackTrace();}*/
        driver.quit();
    }

}
