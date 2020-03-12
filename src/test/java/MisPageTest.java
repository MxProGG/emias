//import jdk.javadoc.internal.doclets.toolkit.util.DocFile;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page.DirectionPage;
import page.JournalMSE;
import page.LoginPage;
import page.MainPage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;


public class MisPageTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;



    @Before
    public void setUp() {
        WebDriverInstall driverInstall = new WebDriverInstall();
        driver = driverInstall.setUpDriver();
        loginPage = new LoginPage(driver);
        //page.LoginPage loginPage = PageFactory.initElements(driver, page.LoginPage.class);
    }

    @Test
    public  void helpClick (){
        Assert.assertEquals("TrustMed",loginPage.getHeadingText());
        loginPage.clickLinkHelp();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        Assert.assertEquals("http://confluence.softrust.ru/pages/viewpage.action?pageId=7406016",driver.getCurrentUrl());
     }

    @Test
    public void loginErrorTest(){
        loginPage.entrySystem("admin","22");
        String error = loginPage.getErorrText();
        Assert.assertEquals("Неверный пароль пользователя",error);
    }

    @Test
    public void loginSuccesful(){
        loginPage.entrySystem("admin","11");
        Assert.assertEquals("Центр управления", driver.getTitle());
    }

    @Test
    public void loginOut () throws InterruptedException {
        loginPage.entrySystem("admin","11");
        mainPage = new MainPage(driver);
        sleep(2000);
        mainPage.logOut();
        Assert.assertEquals("TrustMed", loginPage.getHeadingText());

    }

    /*@Test
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
        Assert.assertEquals(1,journalMSE.countRowTable());
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
        DirectionPage directionPage = new DirectionPage(driver);
        try {
            mainPage.searchTap();
            String messageSave = directionPage.newDirection();
            Assert.assertEquals("Направление сохранено",messageSave);
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }

    }*/

    @After
    public void tearDown(){

        driver.quit();
    }

}
