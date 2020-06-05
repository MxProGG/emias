package com;
import com.page.DirectionPageMSE;
import com.page.JournalMSE;
import com.page.LoginPage;
import com.page.MainPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

import static org.hamcrest.MatcherAssert.assertThat;


import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;



public class DirectionMSETest {
    private WebDriver driver;
    private LoginPage loginPage;
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");

    @BeforeEach
    public void setUp(){
        WebDriverInstall driverInstall = new WebDriverInstall();
        driver = driverInstall.setUpDriver();
        loginPage = new LoginPage(driver);
    }

   //@Test //Переход из Веб Мис в Журнал направлений на МСЭ
    public void linkMSE(){
        MainPage mainPage = loginPage.entrySystem("admin","11");
        try {
            mainPage.linkPage("#MseIndexNg");
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
        JournalMSE journalMSE = new JournalMSE(driver);
        String journalTitle = journalMSE.journalTitle();
        Assertions.assertEquals("Журнал направлений на медико-социальную экспертизу (МСЭ)",journalTitle);
    }

    @Test // Кейс 1.1 Создание направления на МСЭ
    public void createMSE_1_1()  {
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

    @Test //Кейс 4.1 Поиск направления по ФИО и периодам выдачи
    public void journalSearch_4_1() throws ParseException {
        linkMSE();
        String dateFrom="14.02.2020";
        String dateTo="29.02.2020";
        JournalMSE journalMSE = new JournalMSE(driver);
        Assertions.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeFIO("Темников");
        journalMSE.typeDateFrom(dateFrom);
        journalMSE.typeDateTo(dateTo);
        journalMSE.clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String FIOLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[3]")).getText();
            Assertions.assertEquals("Темников Дмитрий Олегович", FIOLabel,"ФИО не совпадает!");
            String DateLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[2]")).getText();
            Date dateGrid=formatDate.parse(DateLabel);
            Assertions.assertTrue((dateGrid.after(formatDate.parse(dateFrom)) && dateGrid.before(formatDate.parse(dateTo))) || dateGrid.equals(formatDate.parse(dateFrom)) || dateGrid.equals(formatDate.parse(dateTo)),"Дата в гриде не входит в диапазон поиска!");
        }
        journalMSE.clickClear();
    }

    @Test //Кейс 4.2 Поиск направления по Статусу и Членам комиссии
    public void journalSearch_4_2(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assertions.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeStatus("Зарегистрирован");
        journalMSE.typeDocCommission("Иванов");
        journalMSE.clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String [] mas = new String[driver.findElements(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li")).size()];
            String statusLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[9]")).getText();
            Assertions.assertEquals("Зарегистрирован", statusLabel, "Статус не совпадает!");
            for (int j=1;j <= driver.findElements(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li")).size(); j++) {
               String DocCommissionLabelT= driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li[" + j + "]")).getText();
               mas[j-1] = DocCommissionLabelT;
            }
            assertThat("Член Комиссии не входит в полученный список!", mas, hasItemInArray("Иванов П.С."));
        }
        journalMSE.clickClear();
    }

    @Test //Кейс 4.3 Поиск направления по Заключению и Автору
    public void journalSearch_4_3(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assertions.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeConclusion("Установлена")
                    .typeAuthor("Иванов")
                    .clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String ConclusionLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[11]")).getText();
            Assertions.assertEquals("Установлена", ConclusionLabel,"Заключение не совпадает!");
        }
        journalMSE.clickClear();
    }

    @Test // Кейс 4.4.1 Сохранения в Ехcel без фильтра
    public void journalDownloadExcel_4_4_1() throws InterruptedException {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assertions.assertEquals(10, journalMSE.countRowTable());
        journalMSE.clickSaveExcel();
        sleep(5000);
        assertThat("Файл не скачан, папка пуста!", journalMSE.deleteFile("D:\\Work\\Download_selenium"), arrayWithSize(1));
    }

    @Test // Кейс 4.4.2 Сохранения в Ехcel c фильтром
    public void journalDownloadExcel_4_4_2() throws InterruptedException {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assertions.assertEquals(10, journalMSE.countRowTable());
        journalMSE.typeFIO("Темников");
        journalMSE.typeStatus("Зарегистрирован");
        journalMSE.clickSearch();
        journalMSE.clickSaveExcel();
        sleep(5000);
        assertThat("Файл не скачан, папка пуста!",journalMSE.deleteFile("D:\\Work\\Download_selenium"),arrayWithSize(1));
    }

    @Test // Кейс 4.5 Удаление направления
    public void journalDeleteMSE_4_5(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assertions.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeFIO("Авто Тест");
        Date dateNow = new Date();
        journalMSE.typeDateFrom(formatDate.format(dateNow));
        journalMSE.typeDateTo(formatDate.format(dateNow));
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

    @AfterEach
    public void tearDown(){
        /*File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "./target/screenshots/" + screenshot.getName();
        try {
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e){e.printStackTrace();}*/
        driver.quit();
    }

}
