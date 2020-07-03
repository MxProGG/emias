package com;

import com.config.TestConfig;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebDriverInstall {
    public static WebDriver driver;

    public static WebDriver setUpDriver() {
           //Путь к веб драйверу
           System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
           //Прописыаем настройку куда скачивать файлы
           ChromeOptions dir = new ChromeOptions();
           Map<String, Object> dirDownload = new HashMap<>();
           dirDownload.put("download.default_directory", "D:\\Work\\Download_selenium");
           dir.setExperimentalOption("prefs",dirDownload);
           //Прописываем настройки чтобы запускать браузер в режиме без окна.
           ChromeOptions options = new ChromeOptions();
           options.addArguments("--headless");
           options.addArguments("window-size=1650x1050");
           options.setExperimentalOption("prefs",dirDownload);
           //Определяем режимы запуска в заисимости от значения mode, КаСтЫлЬ!!!
          TestConfig.initConfig();
           if(TestConfig.isHeadless()) {
                  driver = new ChromeDriver(options);
           } else {
                  driver = new ChromeDriver(dir);
           }
           /*String mode_browser = "headless";// для запуска без окна браузера передать не "headless"
           if (mode_browser == "headless"){
                  driver = new ChromeDriver(options);}
                else{driver = new ChromeDriver(dir);}*/
           //Неявное ожидание для все элементов 10 сек
           driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
           //Чтоб окно браузера запускалось на втором мониторе
           driver.manage().window().setPosition(new Point(1930, 60));
           //На весь экран
           driver.manage().window().maximize();
           //driver.manage().window().setSize(new Dimension(1600,1000));
           driver.get("http://192.168.7.54/mis/test2/");
           return driver;
           //com.page.LoginPage loginPage = new com.page.LoginPage(driver);
    }

  /*  public static WebDriver currentDriver() {
        return driver;
    }*/

    public static void executeJs(String script) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        try {
            js.executeScript(script);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenshot() {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")
                + File.separator + "target"
                + File.separator + "screenshots"
                + File.separator + " " + "screenshot_" +  (new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date())) + ".png";
        try {
            FileUtils.copyFile(scrFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "./target/screenshots/" + screenshot.getName();
        try {
            FileUtils.copyFile(screenshot, new File(path));
        } catch (IOException e){e.printStackTrace();}*/
    }

    public static void wait(int seconds)
    {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
