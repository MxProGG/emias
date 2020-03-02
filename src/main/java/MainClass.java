import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MainClass {

       public static void main(String[] args) {
        /*//Путь к веб драйверу
        System.setProperty("webdriver.chrome.driver", "D:\\java_project\\java_lesson_selenium\\drives\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //Неявное ожидание для все элементов 10 сек
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Чтоб окно браузера запускалось на втором мониторе
        driver.manage().window().setPosition(new Point(1930,60));
        //На весь экран
        driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(1600,1000));

        driver.get("http://192.168.7.54/mis/test2/");
        //page.LoginPage loginPage = new page.LoginPage(driver);
        page.LoginPage loginPage = PageFactory.initElements(driver, page.LoginPage.class);
        loginPage.entrySystem("admin","11");

        page.MainPage mainPage = new page.MainPage(driver);
        mainPage.linkPage("Журнал направлений на МСЭ");

        driver.quit();*/
    }
}
