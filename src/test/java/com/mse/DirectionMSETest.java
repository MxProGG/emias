package com.mse;

import com.WebDriverInstall;
import com.page.DirectionPageMSE;
import com.page.JournalMSE;
import com.page.LoginPage;
import com.page.MainPage;
import com.sql.DataSQL;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;


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
    @Order(3)
    @DisplayName("Удаление направления на МСЭ")
    public void journalDeleteMSE() {
        linkMSE();
        WebDriverInstall.wait(2);
        journalMSE.typeFIO("Авто").clickSearch();
       /* Date dateNow = new Date();
        journalMSE.typeDateFrom(formatDate.format(dateNow));
        journalMSE.typeDateTo(formatDate.format(dateNow));*/
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        String messageDelete = journalMSE.clickMenuDelete(0);
        Assertions.assertEquals("Направление на МСЭ удалено успешно.",messageDelete,"Не было сообщение об успешном удалении направления");
    }

    @Test // Кейс 4.6 Создать направление на основании
    @Order(2)
    @DisplayName("Создать направление на основании")
    public void createBasedOnMSE() throws InterruptedException {
        linkMSE();
        DataSQL.setStatusMSE(DataSQL.getExamId(1),6);
        journalMSE.typeFIO("Авто").clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        journalMSE.clickMenuBasedOn(0);
        DirectionPageMSE directionPageMSE = new DirectionPageMSE(driver);
        String messageSave = directionPageMSE.newDirection();
        Assertions.assertEquals("Направление сохранено",messageSave);
        directionPageMSE.clickButtonClose();
        WebDriverInstall.wait(2);
        journalMSE.typeFIO("Авто").clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        ArrayList<String> mas = new ArrayList<>();
        mas.add("Сформирован");
        mas.add("Аннулирован");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String statusLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[9]")).getText();
            Assertions.assertEquals(mas.get(i), statusLabel, "Статус не совпадает!");
        }
        DataSQL.deleteDirectionMSE(DataSQL.getExamId(1));
        DataSQL.setStatusMSE(DataSQL.getExamId(7),1);
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
        WebDriverInstall.takeScreenshot();
        driver.quit();
    }

}
