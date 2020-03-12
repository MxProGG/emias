import org.junit.*;
import org.openqa.selenium.WebDriver;
import page.LoginPage;
import page.MainPage;
import static java.lang.Thread.sleep;


public class MisPageTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;



    @Before
    public void setUp() {
        WebDriverInstall driverInstall = new WebDriverInstall();
        driver = driverInstall.setUpDriver();
        loginPage = new LoginPage(driver);
        //page.LoginPage loginPage = PageFactory.initElements(driver, page.LoginPage.class);
    }

    @Test
    public  void helpClick (){
        Assert.assertEquals("TrustMed",loginPage.getHeadingText());
        loginPage.clickLinkHelp();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        Assert.assertEquals("http://confluence.softrust.ru/pages/viewpage.action?pageId=7406016",driver.getCurrentUrl());
     }

    @Test
    public void loginErrorTest(){
        loginPage.entrySystem("admin","22");
        String error = loginPage.getErorrText();
        Assert.assertEquals("Неверный пароль пользователя",error);
    }

    @Test
    public void loginSuccesful(){
        loginPage.entrySystem("admin","11");
        Assert.assertEquals("Центр управления", driver.getTitle());
    }

    @Test
    public void loginOut () throws InterruptedException {
        loginPage.entrySystem("admin","11");
        mainPage = new MainPage(driver);
        sleep(2000);
        mainPage.logOut();
        Assert.assertEquals("TrustMed", loginPage.getHeadingText());

    }

    @After
    public void tearDown(){

        driver.quit();
    }

}
