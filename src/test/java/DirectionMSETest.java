import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;



public class DirectionMSETest {
    private WebDriver driver;
    private LoginPage loginPage;
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");

    @Before
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
        Assert.assertEquals("Журнал направлений на медико-социальную экспертизу (МСЭ)",journalTitle);
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
            Assert.assertEquals("Направление сохранено",messageSave);
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
        Assert.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeFIO("Темников");
        journalMSE.typeDateFrom(dateFrom);
        journalMSE.typeDateTo(dateTo);
        journalMSE.clickSearch();
        Assert.assertNotEquals("Грида пустая!",0,journalMSE.countRowTable());
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String FIOLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[3]")).getText();
            Assert.assertEquals("ФИО не совпадает!","Темников Дмитрий Олегович", FIOLabel);
            String DateLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[2]")).getText();
            Date dateGrid=formatDate.parse(DateLabel);
            Assert.assertTrue("Дата в гриде не входит в диапазон поиска!",
                    (dateGrid.after(formatDate.parse(dateFrom)) && dateGrid.before(formatDate.parse(dateTo))) || dateGrid.equals(formatDate.parse(dateFrom)) || dateGrid.equals(formatDate.parse(dateTo)));
        }
        journalMSE.clickClear();
    }

    @Test //Кейс 4.2 Поиск направления по Статусу и Членам комиссии
    public void journalSearch_4_2(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeStatus("Зарегистрирован");
        journalMSE.typeDocCommission("Иванов");
        journalMSE.clickSearch();
        Assert.assertNotEquals("Грида пустая!",0,journalMSE.countRowTable());
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String [] mas = new String[driver.findElements(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li")).size()];
            String statusLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[9]")).getText();
            Assert.assertEquals("Статус не совпадает!","Зарегистрирован", statusLabel);
            for (int j=1;j <= driver.findElements(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li")).size(); j++) {
               String DocCommissionLabelT= driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[8]//li[" + j + "]")).getText();
               mas[j-1] = DocCommissionLabelT;
            }
            Assert.assertThat("Член Комиссии не входит в полученный список!", mas, hasItemInArray("Иванов П.С."));
        }
        journalMSE.clickClear();
    }

    @Test //Кейс 4.3 Поиск направления по Заключению и Автору
    public void journalSearch_4_3(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeConclusion("Установлена");
        journalMSE.typeAuthor("Иванов");
        journalMSE.clickSearch();
        Assert.assertNotEquals("Грида пустая!",0,journalMSE.countRowTable());
        for (int i=0; i < journalMSE.countRowTable(); i++) {
            String ConclusionLabel = driver.findElement(By.xpath("//datatable-body-row[@ng-reflect-row-index='" + i + "']//datatable-body-cell[11]")).getText();
            Assert.assertEquals("Заключение не совпадает!","Установлена", ConclusionLabel);
        }
        journalMSE.clickClear();
    }

    @Test // Кейс 4.4.1 Сохранения в Ехcel без фильтра
    public void journalDownloadExcel_4_4_1() throws InterruptedException {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10, journalMSE.countRowTable());
        journalMSE.clickSaveExcel();
        sleep(5000);
        Assert.assertThat("Файл не скачан, папка пуста!", journalMSE.deleteFile("D:\\Work\\Download_selenium"), arrayWithSize(1));
    }

    @Test // Кейс 4.4.2 Сохранения в Ехcel c фильтром
    public void journalDownloadExcel_4_4_2() throws InterruptedException {
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10, journalMSE.countRowTable());
        journalMSE.typeFIO("Темников");
        journalMSE.typeStatus("Зарегистрирован");
        journalMSE.clickSearch();
        journalMSE.clickSaveExcel();
        sleep(5000);
        Assert.assertThat("Файл не скачан, папка пуста!",journalMSE.deleteFile("D:\\Work\\Download_selenium"),arrayWithSize(1));
    }

    @Test // Кейс 4.5 Удаление направления
    public void journalDeleteMSE_4_5(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10,journalMSE.countRowTable());
        journalMSE.typeFIO("Авто Тест");
        Date dateNow = new Date();
        journalMSE.typeDateFrom(formatDate.format(dateNow));
        journalMSE.typeDateTo(formatDate.format(dateNow));
        journalMSE.clickSearch();
        Assert.assertNotEquals("Грида пустая!",0,journalMSE.countRowTable());
        String messageDelete = journalMSE.clickMenuDelete(0);
        Assert.assertEquals("Не было сообщение об успешном удалении направления","Направление на МСЭ удалено успешно.",messageDelete);
    }

/*
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
    }*/

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
