package TestCases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase extends AbstractTestNGCucumberTests {
    protected static WebDriver driver;
    public static Properties prop;
    protected ATUTestRecorder recorder;
    public static com.relevantcodes.extentreports.ExtentReports report;
    public static com.relevantcodes.extentreports.ExtentTest logger;
    ExtentReports extent;
    LocalDate myObj = LocalDate.now();
    public static String downloadpath=System.getProperty("user.dir")+"\\download";
    @BeforeMethod
    @Parameters({"browser"})
    public void start(@Optional("chrome") String browername,Method method) throws ATUTestRecorderException {

        
        
        if(browername.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver(chromeopthion());}
        else if (browername.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver=new FirefoxDriver();
        }
        else if(browername.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver= new EdgeDriver();
        }
        driver.manage().window().maximize();
        startrecord(method.getName());
        logger=report.startTest(method.getName());

    }
    @AfterMethod
    public void end(Method method,ITestResult result) throws ATUTestRecorderException, IOException {

        takescreenshot(method.getName());
        recorder.stop();
        if(result.getStatus()==ITestResult.SUCCESS) {
            logger.log(LogStatus.PASS, "Test Pass");
            logger.log(LogStatus.PASS, "<a href='"+result.getName()+".png"+"'><span class='label info'>Download Snapshot</span></a>");
            logger.log(LogStatus.PASS, "<a href='"+result.getName()+".mov"+"'><span class='label info'>Download Video</span></a>");
            logger.setDescription("DHA Report");

        }
        else if(result.getStatus()==ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, result.getThrowable());
            logger.log(LogStatus.PASS, "<a href='"+result.getName()+".png"+"'><span class='label info'>Download Snapshot</span></a>");
            logger.log(LogStatus.PASS, "<a href='"+result.getName()+".mov"+"'><span class='label info'>Download Video</span></a>");
        }
        else {
            logger.log(LogStatus.SKIP, "Test Skipped");
        }
        driver.quit();

    }
    @BeforeSuite
    public void startreport() {
        String reportpath=System.getProperty("user.dir")+"\\TestReport\\ExtentReportResults"+myObj+".html";
        report=new com.relevantcodes.extentreports.ExtentReports(reportpath,true);
        report.addSystemInfo("Reported By", "QA Team");
        report.loadConfig(new File(System.getProperty("user.dir")+"\\extent2.xml"));

    }
    @AfterSuite
    public void endreport() {
        report.flush();
    }
    public TestBase() throws IOException {
        prop=new Properties();
        FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src/test/java\\properties\\data.properties");
        prop.load(fis);
    }
    public static void takescreenshot(String name) throws IOException, ATUTestRecorderException {
        File srcfile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcfile, new File(System.getProperty("user.dir")+"\\TestReport\\"+name+".png"));

    }
    public void startrecord(String name) throws ATUTestRecorderException {
        recorder=new ATUTestRecorder(System.getProperty("user.dir")+"\\TestReport", name, false);
        recorder.start();
    }


    public void waitelementByXpath(String element) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(prop.getProperty(element)))));

    }
    public void AssertequalmethodByXpath(String actual,String expected) {
        org.testng.Assert.assertEquals(driver.findElement(By.xpath(prop.getProperty(actual))).getText(), expected);
    }
    public void assertdisplayingByXpath(String element) {
        Assert.assertTrue(driver.findElement(By.xpath(prop.getProperty(element))).isDisplayed());
    }
    public void AssertContainByXpath(String element,String word) {
        Assert.assertTrue(driver.findElement(By.xpath(prop.getProperty(element))).getText().contains(word));
    }
    public void scrollByXpath(String element) {
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(prop.getProperty(element))));
    }
    public void uploadfileByXpath(String element,String Filename) {
        String  filepath=System.getProperty("user.dir")+"\\Uplodedfiles\\"+Filename;
        driver.findElement(By.xpath(prop.getProperty(element))).sendKeys(filepath);
    }
    public void RobotClassByXpath(String browseelement,String Filename) throws InterruptedException {
        String filepath=System.getProperty("user.dir")+"\\Uplodedfiles\\"+Filename;
        driver.findElement(By.xpath(prop.getProperty(browseelement))).click();
        Thread.sleep(5000);
        //robot class
        Robot robot;
        try {
            robot = new Robot();
            //ctrl+c
            StringSelection selection =new StringSelection(filepath);
            Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(2000);
            //ctrl+v
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(2000);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void  DismissALert() throws InterruptedException {
        Alert alert=driver.switchTo().alert();
        alert.dismiss();
        Thread.sleep(3000);
    }
    public void  AcceptALert() throws InterruptedException {
        Alert alert=driver.switchTo().alert();
        alert.accept();
        Thread.sleep(3000);
    }
    public void ClickByXpath(String elemnt) {
        driver.findElement(By.xpath(prop.getProperty(elemnt))).click();

    }
    public void WriteByXpath(String element,String text) {
        driver.findElement(By.xpath(prop.getProperty(element))).sendKeys(prop.getProperty(text));
    }
    public void SubmitByXpath(String element) {
        driver.findElement(By.xpath(prop.getProperty(element))).submit();
    }
    public void SelectByXpath(String element,String Visibletext) {
        org.openqa.selenium.support.ui.Select select=new org.openqa.selenium.support.ui.Select(driver.findElement(By.xpath(prop.getProperty(element))));
        select.selectByVisibleText(Visibletext);
    }
    public void IsSelectedByXpath(String element) {
        Assert.assertTrue(driver.findElement(By.xpath(prop.getProperty(element))).isSelected());
    }
    public void RightclickByXpath(String xpath) {
        Actions action=new Actions(driver);
        action.contextClick(driver.findElement(By.xpath(prop.getProperty(xpath)))).perform();
    }
    public void HoverByXpath(String xpath) {
        Actions action=new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath(prop.getProperty(xpath)))).perform();
    }
    public void DoubleclickByXpath(String xpath) {
        Actions action=new Actions(driver);
        action.doubleClick(driver.findElement(By.xpath(prop.getProperty(xpath)))).perform();
    }
    public void DragAndDropByXpath(String Dragelement,String Dropelement) {
        Actions action=new Actions(driver);
        action.dragAndDrop(driver.findElement(By.xpath(prop.getProperty(Dragelement))),
                driver.findElement(By.xpath(prop.getProperty(Dropelement))));
    }
    public void ClearByXpath(String element) {
        driver.findElement(By.xpath(prop.getProperty(element))).clear();
    }
    public void windowhandleByXpath(String buttonwhichopennewwindow,int indexofwindow) {
        driver.findElement(By.xpath(prop.getProperty(buttonwhichopennewwindow))).click();
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList<>(windowHandles);
        driver.switchTo().window(windowHandlesList.get(indexofwindow));
    }
    public void Switchtoframe(int indexofframe) {
        driver.switchTo().frame(indexofframe);
    }
    public void Backtoparentpage() {
        driver.switchTo().defaultContent();
    }
    public static ChromeOptions chromeopthion() {
        ChromeOptions option =new ChromeOptions();
        HashMap<String, Object> chromeprefs=new HashMap<String, Object>();
        
        chromeprefs.put("profile.default.content_settings.popups", 0);
        chromeprefs.put("download.default_directory", downloadpath);
        option.setExperimentalOption("prefs", chromeprefs);
        option.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        option.addArguments("--disable-gpu");
        option.addArguments("--disable-dev-shm-usage");
        option.addArguments("--no-sandbox");
        option.addArguments("--remote-allow-origins=*");

        return option;
    }
    public void downloadfileByXpath(String element) {
        driver.findElement(By.xpath(prop.getProperty(element))).click();
    }
    public void CheckBrokenLinks(String Tagnameoflist,String Attribute) {
        List<WebElement> links=driver.findElements(By.tagName(prop.getProperty(Tagnameoflist)));
        System.out.println(links.size());
        for(int i =0;i<links.size();i++) {
            WebElement elemnet =links.get(i);
            String urll=elemnet.getAttribute(prop.getProperty(Attribute));
            VerifyLinks(urll);
        }
    }
    public  void VerifyLinks(String url)  {
        try {
            URL urlconn=new URL(url);
            try {
                HttpURLConnection http=(HttpURLConnection) urlconn.openConnection();
                http.setConnectTimeout(2000);
                http.connect();
                if(http.getResponseCode()==200) {
                    System.out.println(url+" - "+http.getResponseMessage());
                }
                if(http.getResponseCode()==404) {
                    System.out.println(url+" - "+http.getResponseMessage());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public void Forward() {
        driver.navigate().forward();
    }
    public void Back() {
        driver.navigate().back();
    }
    public void Refresh() {
        driver.navigate().refresh();
    }
    public String GetcurrentURL() {
        String url =driver.getCurrentUrl();
        return url;
    }
    public WebElement ListofwebelementByXpath(String xpathlist,int index) throws InterruptedException{
        List<WebElement> list=driver.findElements(By.xpath(prop.getProperty(xpathlist)));
        Thread.sleep(5000);
        WebElement element= list.get(index);
        return element ;

    }
    public void NavigateToURL(String Url) {
        driver.navigate().to(prop.getProperty(Url));
        System.out.println(GetcurrentURL());
    }
    public void OpennewTap(int index,String URL) {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(index));
        driver.navigate().to(prop.getProperty(URL));
    }
    public void SwitchTab(int index) {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(index));
    }
    public void SingleKeyPress(int key) throws AWTException {
        Robot robot;
        robot=new Robot();
        robot.keyPress(key);
        robot.keyRelease(key);
    }
    public void DoubleKeyPress(int key1,int key2) throws AWTException {
        Robot robot;
        robot=new Robot();
        robot.keyPress(key1);
        robot.keyPress(key2);
        robot.keyRelease(key1);
        robot.keyRelease(key2);
    }
    public void waitelementByid(String element) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty(element)))));

    }
    public void AssertequalmethodByid(String actual,String expected) {
        org.testng.Assert.assertEquals(driver.findElement(By.id(prop.getProperty(actual))).getText(), expected);
    }
    public void assertdisplayingByid(String element) {
        Assert.assertTrue(driver.findElement(By.id(prop.getProperty(element))).isDisplayed());
    }
    public void AssertContainByid(String element,String word) {
        Assert.assertTrue(driver.findElement(By.id(prop.getProperty(element))).getText().contains(word));
    }
    public void scrollByid(String element) {
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id(prop.getProperty(element))));
    }
    public void uploadfileByid(String element,String Filename) {
        String  filepath=System.getProperty("user.dir")+"\\Uplodedfiles\\"+Filename;
        driver.findElement(By.id(prop.getProperty(element))).sendKeys(filepath);
    }
    public void RobotClassByid(String browseelement,String Filename) throws InterruptedException {
        String filepath=System.getProperty("user.dir")+"\\Uplodedfiles\\"+Filename;
        driver.findElement(By.id(prop.getProperty(browseelement))).click();
        Thread.sleep(5000);
        //robot class
        Robot robot;
        try {
            robot = new Robot();
            //ctrl+c
            StringSelection selection =new StringSelection(filepath);
            Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(2000);
            //ctrl+v
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(2000);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void ClickByid(String elemnt) {
        driver.findElement(By.id(prop.getProperty(elemnt))).click();

    }
    public void WriteByid(String element,String text) {
        driver.findElement(By.id(prop.getProperty(element))).sendKeys(prop.getProperty(text));
    }
    public void SubmitByid(String element) {
        driver.findElement(By.id(prop.getProperty(element))).submit();
    }
    public void SelectByid(String element,String Visibletext) {
        org.openqa.selenium.support.ui.Select select=new org.openqa.selenium.support.ui.Select(driver.findElement(By.id(prop.getProperty(element))));
        select.selectByVisibleText(Visibletext);
    }
    public void IsSelectedByid(String element) {
        Assert.assertTrue(driver.findElement(By.id(prop.getProperty(element))).isSelected());
    }
    public void RightclickByid(String id) {
        Actions action=new Actions(driver);
        action.contextClick(driver.findElement(By.id(prop.getProperty(id)))).perform();
    }
    public void HoverByid(String id) {
        Actions action=new Actions(driver);
        action.moveToElement(driver.findElement(By.id(prop.getProperty(id)))).perform();
    }
    public void DoubleclickByid(String id) {
        Actions action=new Actions(driver);
        action.doubleClick(driver.findElement(By.id(prop.getProperty(id)))).perform();
    }
    public void DragAndDropByid(String Dragelement,String Dropelement) {
        Actions action=new Actions(driver);
        action.dragAndDrop(driver.findElement(By.id(prop.getProperty(Dragelement))),
                driver.findElement(By.id(prop.getProperty(Dropelement))));
    }
    public void ClearByid(String element) {
        driver.findElement(By.id(prop.getProperty(element))).clear();
    }
    public void windowhandleByid(String buttonwhichopennewwindow,int indexofwindow) {
        driver.findElement(By.id(prop.getProperty(buttonwhichopennewwindow))).click();
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList<>(windowHandles);
        driver.switchTo().window(windowHandlesList.get(indexofwindow));
    }
    public void downloadfileByid(String element) {
        driver.findElement(By.id(prop.getProperty(element))).click();
    }
    public WebElement ListofwebelementByid(String idlist,int index) throws InterruptedException{
        List<WebElement> list=driver.findElements(By.id(prop.getProperty(idlist)));
        Thread.sleep(5000);
        WebElement element= list.get(index);
        return element ;

    }
    public void WaitAlert() {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.alertIsPresent());
    }

}


