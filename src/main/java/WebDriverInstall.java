import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class WebDriverInstall {
    public WebDriver driver;

    public WebDriver setUpDriver() {

           //Путь к веб драйверу
           System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
           ChromeOptions options = new ChromeOptions();
           options.addArguments("--headless");
           options.addArguments("window-size=1650x1050");
           int mode = 1; // для запуска без окна браузера передать >0
           if (mode == 1){
           driver = new ChromeDriver();}
                else{driver = new ChromeDriver(options);}
           //Неявное ожидание для все элементов 10 сек
           driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
           //Чтоб окно браузера запускалось на втором мониторе
           driver.manage().window().setPosition(new Point(1930, 60));
           //На весь экран
           driver.manage().window().maximize();
           //driver.manage().window().setSize(new Dimension(1600,1000));
           driver.get("http://192.168.7.54/mis/test2/");
           return driver;
           //page.LoginPage loginPage = new page.LoginPage(driver);
    }

}
