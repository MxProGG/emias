package com.mse;

import com.WebDriverInstall;
import com.page.JournalMSE;
import com.page.LoginPage;
import com.page.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.lang.Thread.sleep;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;


public class JournalTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private JournalMSE journalMSE;
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
    private String dateFrom="14.02.2020";
    private String dateTo="29.02.2020";

    @BeforeEach //Инициализация веб драйвера и настройки браузера
    public void setUp(){
        WebDriverInstall driverInstall = new WebDriverInstall();
        driver = driverInstall.setUpDriver();
        loginPage = new LoginPage(driver);
    }

    @BeforeEach //Переход из Веб Мис в Журнал направлений на МСЭ
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
        Assertions.assertEquals(10,journalMSE.countRowTable(),"Грида пустая!");
    }

    @Test
    @DisplayName("Поиск направления по ФИО")
    public void searchFIO() {
        journalMSE.typeFIO("Темников");
        journalMSE.typeDateFrom(dateFrom);
        journalMSE.typeDateTo(dateTo);
        journalMSE.clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String FIOLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[3]")).getText();
            Assertions.assertEquals("Темников Дмитрий Олегович", FIOLabel,"ФИО не совпадает!");
        }
        journalMSE.clickClear();
    }

    @Test
    @DisplayName("Поиск направления по периодам выдачи")
    public void searchPeriod() throws ParseException {
        journalMSE.typeFIO("Темников");
        journalMSE.typeDateFrom(dateFrom);
        journalMSE.typeDateTo(dateTo);
        journalMSE.clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String DateLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[2]")).getText();
            Date dateGrid=formatDate.parse(DateLabel);
            Assertions.assertTrue((dateGrid.after(formatDate.parse(dateFrom)) && dateGrid.before(formatDate.parse(dateTo))) || dateGrid.equals(formatDate.parse(dateFrom)) || dateGrid.equals(formatDate.parse(dateTo)),"Дата в гриде не входит в диапазон поиска!");
        }
        journalMSE.clickClear();
    }

    @Test
    @DisplayName("Поиск направления по Статусу")
    public void searchStatus(){
        journalMSE.typeStatus("Зарегистрирован")
                .typeDocCommission("Иванов")
                .clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String statusLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[9]")).getText();
            Assertions.assertEquals("Зарегистрирован", statusLabel, "Статус не совпадает!");
        }
        journalMSE.clickClear();
    }

    @Test
    @DisplayName("Поиск направления по Членам комиссии")
    public void searchDocCommission(){
        journalMSE.typeStatus("Зарегистрирован")
                .typeDocCommission("Иванов")
                .clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String [] mas = new String[driver.findElements(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li")).size()];
            for (int j=1;j <= driver.findElements(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li")).size(); j++) {
                String DocCommissionLabelT= driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li[" + j + "]")).getText();
                mas[j-1] = DocCommissionLabelT;
            }
            assertThat("Член Комиссии не входит в полученный список!", mas, hasItemInArray("Иванов П.С."));
        }
        journalMSE.clickClear();
    }

    @Test
    @DisplayName("Поиск направления по Заключению")
    public void searchConclusion(){
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

    /*@Test
    @DisplayName("Поиск направления по Автору")
    public void searchAuthor(){
        journalMSE.typeConclusion("Установлена")
                .typeAuthor("Иванов")
                .clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String ConclusionLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[11]")).getText();
            Assertions.assertEquals("Установлена", ConclusionLabel,"Заключение не совпадает!");
        }
        journalMSE.clickClear();
    }*/

    @Test
    @DisplayName("Сохранения в Ехcel без фильтра")
    public void downloadExcelNotFilter() throws InterruptedException {
        journalMSE.clickSaveExcel();
        sleep(5000);
        assertThat("Файл не скачан, папка пуста!", journalMSE.deleteFile("D:\\Work\\Download_selenium"), arrayWithSize(1));
    }

    @Test
    @DisplayName("Сохранения в Ехcel c фильтром")
    public void downloadExcelWithFilter() throws InterruptedException {
        journalMSE.typeFIO("Темников")
                .typeStatus("Зарегистрирован")
                .clickSearch()
                .clickSaveExcel();
        sleep(5000);
        assertThat("Файл не скачан, папка пуста!",journalMSE.deleteFile("D:\\Work\\Download_selenium"),arrayWithSize(1));
    }

    @AfterEach //Закрываем ссесию веб драйвера
    public void tearDown(){
        driver.quit();
    }
}
