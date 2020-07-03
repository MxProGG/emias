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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    public void linkMSE() {
        MainPage mainPage = loginPage.entrySystem("admin","11");
        mainPage.linkPage("#MseIndexNg");
        journalMSE = new JournalMSE(driver);
        String journalTitle = journalMSE.journalTitle();
        Assertions.assertEquals("Журнал направлений на медико-социальную экспертизу (МСЭ)",journalTitle);
        Assertions.assertEquals(10,journalMSE.countRowTable(),"Грида пустая!");
    }

    @Test
    @Order(1)
    @DisplayName("Поиск направления по ФИО")
    public void searchFIO() {
        journalMSE.typeFIO("Темников")
                .typeDateFrom(dateFrom)
                .typeDateTo(dateTo)
                .clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String FIOLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[3]")).getText();
            Assertions.assertEquals("Темников Дмитрий Олегович", FIOLabel,"ФИО не совпадает!");
        }
        journalMSE.clickClear();
    }

    @Test
    @Order(2)
    @DisplayName("Поиск направления по Периодам выдачи")
    public void searchPeriod() throws ParseException {
        journalMSE.typeFIO("Темников")
                .typeDateFrom(dateFrom)
                .typeDateTo(dateTo)
                .clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String DateLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[2]")).getText();
            Date dateGrid=formatDate.parse(DateLabel);
            Assertions.assertTrue((dateGrid.after(formatDate.parse(dateFrom)) && dateGrid.before(formatDate.parse(dateTo))) || dateGrid.equals(formatDate.parse(dateFrom)) || dateGrid.equals(formatDate.parse(dateTo)),"Дата в гриде не входит в диапазон поиска!");
        }
        journalMSE.clickClear();

    }

    @Test
    @Order(3)
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
    @Order(10)
    @DisplayName("Переход из направления в Журнал РЭМД")
    public void searchErrorStatus(){
        journalMSE.typeStatus("Ошибка регистрации")
                .clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String statusLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[9]")).getText();
            Assertions.assertEquals("Ошибка регистрации", statusLabel, "Статус не совпадает!");
        }
        journalMSE.clickMenuJournalREMD(0);
        Assertions.assertEquals("Журнал обмена с РЭМД",driver.findElement(By.xpath("//div[@class='title-container']")).getText());
    }

    @Test
    @Order(8)
    @DisplayName("Печать направления из журнала")
    public void printDirectionJournal() {
        journalMSE.typeStatus("Зарегистрирован")
                .clickSearch().clickMenuPrintDirection(0);
        WebDriverInstall.wait(2);
        //driver.getPageSource();
        //driver.findElement(By.xpath("//body//div[@class='headStyle']")).getText();
        int i=0;
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            i++;
        }

        //Assertions.assertEquals("Документ без имени",driver.findElement(By.xpath("//body//div[@class='headStyle']")).getText());
        Assertions.assertEquals(4, i,"Кол-во вкладок не совпало!");
        
        //String printLabel = driver.findElement(By.xpath("//p[text()='Форма N 088/у']")).;
        //Assertions.assertEquals("Форма N 088/у",printLabel);
    }

    @Test
    @Order(9)
    @DisplayName("Печать обратного талона из журнала")
    public void printResultDirectionJournal() {
        journalMSE.typeStatus("Зарегистрирован")
                .clickSearch().clickMenuPrintResultDirection(0);
        WebDriverInstall.wait(2);
        int i=0;
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            i++;
        }
        Assertions.assertEquals(4, i,"Кол-во вкладок не совпало!");
        //String printLabel = driver.findElement(By.xpath("//p[text()='Форма N 088/у']")).;
        //Assertions.assertEquals("Форма N 088/у",printLabel);
    }

    @Test
    @Order(4)
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
    @Order(5)
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

   /* @Test
    @Order(8)
    @DisplayName("Поиск направления по Автору")
    public void searchAuthor(){
        journalMSE.typeConclusion("Установлена")
                .typeAuthor("Иванов")
                .clickSearch();
        Assertions.assertNotEquals(0,journalMSE.countRowTable(),"Грида пустая!");
        driver.getPageSource();
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String ConclusionLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[11]")).getText();
            Assertions.assertEquals("Установлена", ConclusionLabel,"Заключение не совпадает!");
        }
        journalMSE.clickClear();
    }*/

    @Test
    @Order(6)
    @DisplayName("Сохранения в Ехcel без фильтра")
    public void downloadExcelNotFilter() {
        journalMSE.clickSaveExcel();
        WebDriverInstall.wait(5);
        //sleep(5000);
        assertThat("Файл не скачан, папка пуста!", journalMSE.deleteFile("D:\\Work\\Download_selenium"), arrayWithSize(1));
    }

    @Test
    @Order(7)
    @DisplayName("Сохранения в Ехcel c фильтром")
    public void downloadExcelWithFilter() {
        journalMSE.typeFIO("Темников")
                .typeStatus("Зарегистрирован")
                .clickSearch()
                .clickSaveExcel();
        WebDriverInstall.wait(5);
        assertThat("Файл не скачан, папка пуста!",journalMSE.deleteFile("D:\\Work\\Download_selenium"),arrayWithSize(1));
    }

    @AfterEach //Закрываем ссесию веб драйвера
    public void tearDown(){
        WebDriverInstall.takeScreenshot();
        driver.quit();
    }
}
