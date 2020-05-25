import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import page.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

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

    @Test
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

    @Test
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

    @Test
    public void journalDeleteMSE(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10,journalMSE.countRowTable());
        String messageDelete = journalMSE.clickMenuDelete(0);
        Assert.assertEquals("Направление на МСЭ удалено успешно.",messageDelete);
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
        DirectionPage directionPage = new DirectionPage(driver);
        try {
            String messageSave = directionPage.newDirection();
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
        ResultDirectionPage resultDirectionPage = new ResultDirectionPage(driver);
        String messageSave = resultDirectionPage.newResult();
        Assert.assertEquals("Сведения о результатах проведённой МСЭ сохранены",messageSave);
    }

    @Test
    public void clearResultDirection() {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        journalMSE.clickMenuResultDirection(0);
        ResultDirectionPage resultDirectionPage = new ResultDirectionPage(driver);
        try {
            String messageSave = resultDirectionPage.clearResult();
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
