package resources;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
//import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
//import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	public static Logger log = LogManager.getLogger(Base.class.getName());
	public static WebDriver driver;

	public WebDriver initializeDriver() throws IOException {
		//WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		System.setProperty("webdriver.chrome.driver",chrome());
		driver = new ChromeDriver(options);
		//driver.manage().window().setSize(new Dimension(990, 768));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	public static String chrome() {
	    String filename = "drivers/chromedriver.exe";
	    File file = new File(filename);
	    String path = file.getAbsolutePath();
	    return path;
	}
	
	public static boolean waitForTextToAppear(WebDriver driver, String textToAppear, WebElement element) {	
		try { Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(60))
		        .pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
			return true;
	}
		catch (Exception e) {
			return false;
		}
	}

	public static void wait_time(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
		}
	}
	
	public String randomString(int number) {
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(number);
	    for (int i = 0; i < number; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	    return generatedString;
	}
	//Click on element if is not visible on page but exist in DOM
	public static void jsClick(WebElement element, WebDriver driver) {
		JavascriptExecutor ex = (JavascriptExecutor) driver;
		ex.executeScript("arguments[0].click();", element);
	}
	
	public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
	
	//For upload picture, absolute path
	public static String upload() {
	    String filename = "MediaForTest/picture.png";
	    File file = new File(filename);
	    String path = file.getAbsolutePath();
	    return path;
	}
	public static String upload1() {
	    String filename = "MediaForTest/picture1.png";
	    File file = new File(filename);
	    String path = file.getAbsolutePath();
	    return path;
	}
	public static String upload2() {
	    String filename = "MediaForTest/gamestartup.mp3";
	    File file = new File(filename);
	    String path = file.getAbsolutePath();
	    return path;
	}
	
	public void captureScreen(String name) throws Exception{
		wait_time(2);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("Screenshots/" + name+".png"));
	}
	
	public void captureFullScreen(String name) throws Exception{
		wait_time(2);
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		ImageIO.write(screenshot.getImage(),"PNG", new File("Screenshots/" + name+".png"));
	}
	
	
	public static void checkFormat(String format) {
		List<WebElement> allFormats = driver.findElements(By.cssSelector("app-video-format > ul > li"));
		for(WebElement e: allFormats) {
			if(e.getText().equals(format)) 
				e.click();
		}
	}
	
	public static void navigateTab(String tab) {
		List<WebElement> allTabs = driver.findElements(By.cssSelector("div>nav.ng-star-inserted>a"));
		for(WebElement e: allTabs) {
			if(e.getText().equals(tab))
				e.click();
		}
	}
	
	public static void navigateMobileTab(String tab) {
		WebElement hamburgerMenu = driver.findElement(By.cssSelector("div.container-fluid.header.ng-star-inserted>button"));
		hamburgerMenu.click();
		List<WebElement> allTabs = driver.findElements(By.cssSelector(".nav-mobile.ng-star-inserted>a.nav-link"));
		for(WebElement e: allTabs) {
			if(e.getText().equals(tab))
				e.click();
		}
	}
	
}
