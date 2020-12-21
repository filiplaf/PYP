package pyp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pageObjects.MyFilesPage;
import resources.Base;


public class MyFilesAddFiles extends Base{
	public static Logger log = LogManager.getLogger(MyFilesAddFiles.class.getName());
	
	@Test
	public void uploadMyDevice() throws Exception {
		//String name = new Object(){}.getClass().getEnclosingMethod().getName();
		navigateTab("My Files");
		wait_time(2);
		MyFilesPage mf = new MyFilesPage(driver);
		if("My Files".equals(mf.titleMyFiles().getText())){
			log.info("My Files Page successfully opened");
			log.info("Pre upload-a na " +mf.allFilesTab().getText()+ " prikazano je " +mf.mediaBody().size()+ " fajlova");
		}
		else
			log.error("My Files Page is not opened");
		mf.addFilesButton().click();
		wait_time(1);
		mf.uploadMedia1().sendKeys(upload());
		wait_time(1);
		mf.uploadMedia().sendKeys(upload1());
		wait_time(3);
		mf.uploadMedia().sendKeys(upload2());
		mf.addFilesButton1().click();
		wait_time(5);
		try {
			mf.uploadFilesButton1().isDisplayed();
			log.error("Upload is not passed");
		} catch (Exception e) {
			log.info("Media is uploaded");
		}
		log.info("Trenutno na " +mf.allFilesTab().getText()+ " prikazano je " +mf.mediaBody().size()+ " fajlova");
		try{
			mf.loadMoreFiles().isDisplayed();
		scrollIntoView(mf.loadMoreFiles());
		try{
			while(mf.loadMoreFiles().isDisplayed()){
			jsClick(mf.loadMoreFiles(), driver);
			wait_time(3);
			scrollIntoView(mf.allFilesTab());
			wait_time(3);
			scrollIntoView(mf.loadMoreFiles());
			}
		}
		catch (Exception e) {
			log.info("No more load files button");
		}
		}
		catch (Exception e) {
			log.info("Load more files button is not on page");
		} 
		log.info("Trenutno na " +mf.allFilesTab().getText()+ " prikazano je " +mf.mediaBody().size()+ " fajlova");
		mf.imageTab().click();
		wait_time(1);
		log.info("Trenutno na " +mf.imageTab().getText()+ " prikazano je " +mf.mediaBody().size()+ " fajlova");
		mf.musicTab().click();
		wait_time(1);
		log.info("Trenutno na " +mf.musicTab().getText()+ " prikazano je " +mf.mediaBody().size()+ " fajlova");
		log.info("Trenutno zauzece prostora je: " +mf.storageInfo().getText());
		log.info("*************************************************************************");
	}
	
	@AfterTest(alwaysRun = true)
	public void teardown() {
		driver.close();
		driver.quit();
	}
}
