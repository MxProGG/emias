package com.whcMvcUI;

import com.WebDriverInstall;
import com.page.LoginPage;
import com.page.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;


public class MisPageTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        WebDriverInstall driverInstall = new WebDriverInstall();
        driver = driverInstall.setUpDriver();
        loginPage = new LoginPage(driver);
        //com.page.LoginPage loginPage = PageFactory.initElements(driver, com.page.LoginPage.class);
    }

    @Test
    @DisplayName("Успешный переход на страницу помощи в Confluence")
    public  void helpClick (){
        Assertions.assertEquals("TrustMed",loginPage.getHeadingText());
        loginPage.clickLinkHelp();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        Assertions.assertEquals("https://confluence.softrust.ru/pages/viewpage.action?pageId=7405997",driver.getCurrentUrl());
     }

    @Test
    @DisplayName("Неудачный вход в Веб МИС")
    public void loginErrorTest(){
        loginPage.entrySystem("admin","22");
        String error = loginPage.getErorrText();
        Assertions.assertEquals("Неверный пароль пользователя",error);
    }

    @Test
    @DisplayName("Успешный вход в Веб МИС")
    public void loginSuccesful(){
        loginPage.entrySystem("admin","11");
        Assertions.assertEquals("Центр управления", driver.getTitle()) ;
    }

    @Test
    @DisplayName("Успешный выход из Веб МИС")
    public void loginOut () throws InterruptedException {
        loginPage.entrySystem("admin","11");
        mainPage = new MainPage(driver);
        sleep(2000);
        mainPage.logOut();
        Assertions.assertEquals("TrustMed", loginPage.getHeadingText());

    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }

}
