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

//    @Test
//    public  void helpClick (){
//        loginPage.clickLinkHelp();
//
//    }

    @Test
    public void loginErrorTest(){
        MainPage mainPage = loginPage.entrySystem("admin","11");
        String error = loginPage.getErorrText();
        Assert.assertEquals("Неверный пароль пользователя",error);
    }

    /*@After
    public void tearDown(){
        driver.quit();
    }*/

}
