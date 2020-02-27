import org.junit.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;



public class LoginPageTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp(){
        //Путь к веб драйверу
        System.setProperty("webdriver.chrome.driver", "D:\\java_project\\java_lesson_selenium\\drives\\chromedriver.exe");
        driver = new ChromeDriver();
        //Неявное ожидание для все элементов 10 сек
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Чтоб окно браузера запускалось на втором мониторе
        driver.manage().window().setPosition(new Point(1930,60));
        //На весь экран
        driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(1600,1000));
        driver.get("http://192.168.7.54/mis/test2/");
        loginPage = new LoginPage(driver);
        //LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @Test
    public  void helpClick (){
        loginPage.clickLinkHelp();

    }

    @Test
    public void loginErrorTest(){
        MainPage mainPage = loginPage.entrySystem("admin","22");
        String error = loginPage.getErorrText();
        Assert.assertEquals("Неверный пароль пользователя",error);

    }

    @Test
    public void linkMSE(){
        MainPage mainPage = loginPage.entrySystem("admin","11");
        mainPage.linkPage("#MseIndexNg");
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
        journalMSE.typeDateBy("29.02.2020");
        journalMSE.typeStatus("Зарегистрирован");
        journalMSE.typeConclusion("Установлена");
        journalMSE.typeDocCommission("Мастякова");
        journalMSE.typeAuthor("Иванов");
        journalMSE.clickSearch();
        Assert.assertEquals(1,journalMSE.countRowTable());
        journalMSE.clickClear();
        journalMSE.clickSearch();
        Assert.assertEquals(10,journalMSE.countRowTable());
        //Assert.assertEquals("Журнал направлений на медико-социальную экспертизу (МСЭ)",journalTitle);
    }
    @Test
    public void journalDeleteMSE(){
        linkMSE();
        JournalMSE journalMSE = new JournalMSE(driver);
        Assert.assertEquals(10,journalMSE.countRowTable());
        String message = journalMSE.clickMenuDelete(9);
        Assert.assertEquals("Направление на МСЭ удалено успешно.",message);
    }

    /*@After
    public void tearDown(){
        driver.quit();
    }*/

}
