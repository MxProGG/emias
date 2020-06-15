package com.mse;

import com.WebDriverInstall;
import com.page.DirectionPageMSE;
import com.page.JournalMSE;
import com.page.LoginPage;
import com.page.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DirectionMSETest {
    private WebDriver driver;
    private LoginPage loginPage;
    private JournalMSE journalMSE;

    @BeforeEach //Инициализация веб драйвера и настройки браузера
    public void setUp(){
        WebDriverInstall driverInstall = new WebDriverInstall();
        driver = driverInstall.setUpDriver();
        loginPage = new LoginPage(driver);
    }

    //@BeforeEach //Переход из Веб Мис в Журнал направлений на МСЭ
    public void linkMSE(){
        MainPage mainPage = loginPage.entrySystem("admin","11");
        try {
            mainPage.linkPage("#MseIndexNg");
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
        journalMSE = new JournalMSE(driver);
        String journalTitle = journalMSE.journalTitle();
        Assertions.assertEquals("Журнал направлений на медико-социальную экспертизу (МСЭ)",journalTitle);
    }

    @Test
    @Order(1)
    @DisplayName("Создание направления на МСЭ")
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
            Assertions.assertEquals("Направление сохранено",messageSave);
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test // Кейс 4.5 Удаление направления
    @Order(2)
    @DisplayName("Удаление направления на МСЭ")
    public void journalDeleteMSE() throws InterruptedException {
        linkMSE();
        sleep(2000);
        journalMSE.typeFIO("Авто");
       /* Date dateNow = new Date();
        journalMSE.typeDateFrom(formatDate.format(dateNow));
        journalMSE.typeDateTo(formatDate.format(dateNow));*/
        journalMSE.clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        String messageDelete = journalMSE.clickMenuDelete(0);
        Assertions.assertEquals("Направление на МСЭ удалено успешно.",messageDelete,"Не было сообщение об успешном удалении направления");
    }

/*
    @Test
    public void createResultDirection()  {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        journalMSE.clickMenuResultDirection(0);
        ResultDirectionPageMSE resultDirectionPageMSE = new ResultDirectionPageMSE(driver);
        String messageSave = resultDirectionPageMSE.newResult();
        Assertions.assertEquals("Сведения о результатах проведённой МСЭ сохранены",messageSave);
    }

    @Test
    public void clearResultDirection() {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        journalMSE.clickMenuResultDirection(0);
        ResultDirectionPageMSE resultDirectionPageMSE = new ResultDirectionPageMSE(driver);
        try {
            String messageSave = resultDirectionPageMSE.clearResult();
            Assertions.assertEquals("Сведения о результатах проведённой МСЭ сохранены", messageSave);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    @AfterEach //Закрываем ссесию веб драйвера
    public void tearDown(){
        /*File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "./target/screenshots/" + screenshot.getName();
        try {
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e){e.printStackTrace();}*/
        driver.quit();
    }

}
