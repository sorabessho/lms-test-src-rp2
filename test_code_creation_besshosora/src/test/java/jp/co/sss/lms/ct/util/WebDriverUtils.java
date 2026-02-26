package jp.co.sss.lms.ct.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

/**
 * Webドライバーユーティリティ
 * @author holy
 */
public class WebDriverUtils {

	/** Webドライバ */
	public static WebDriver webDriver;

	/**
	 * インスタンス取得
	 * @return Webドライバ
	 */
	public static void createDriver() {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		webDriver = new ChromeDriver();
	}

	/**
	 * インスタンス終了
	 */
	public static void closeDriver() {
		webDriver.quit();
	}

	/**
	 * 画面遷移
	 * @param url
	 */
	public static void goTo(String url) {
		webDriver.get(url);
		pageLoadTimeout(5);
	}

	/**
	 * JSのalertモーダルウィンドウのOKを押下
	 * 
	 * @author 別所
	 */
	public static void acceptJSAlert() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
	}

	/**
	 * レポート登録画面の「提出する」ボタンを押下
	 * 
	 * @author 別所
	 */
	public static void clickSubmitBtn() {
		WebElement submitBtnElement = webDriver.findElement(By.cssSelector("button[type='submit']"));
		scrollIntoView(submitBtnElement);
		submitBtnElement.click();
	}

	/**
	 * 勤怠情報直接変更画面の「更新」ボタンを押下
	 * 
	 * @author 別所
	 */
	public static void clickUpdateBtn() {
		WebElement updateBtnElement = webDriver.findElement(By.cssSelector("input[type='submit'][value='更新']"));
		scrollIntoView(updateBtnElement);
		updateBtnElement.click();
	}

	/**
	 * 画面タイトルを検証
	 * ※h2要素限定
	 * 
	 * @author 別所
	 * @param expectedTitle 期待値（タイトル）
	 */
	public static void checkTitleH2(String expectedTitle) {
		pageLoadTimeout(10);
		WebElement titleElement = webDriver.findElement(By.tagName("h2"));
		assertEquals(expectedTitle, titleElement.getText());
	}

	/**
	 * ボックスエラー表示を確認
	 * classにerrorInputがあるか
	 * 
	 * @author 別所
	 * @param locater
	 */
	public static void checkBoxError(By locater) {
		WebElement element = webDriver.findElement(locater);
		String classString = element.getAttribute("class");
		assertTrue(classString.contains("errorInput"));
	}

	/**
	 * ページロードタイムアウト設定
	 * @param second
	 */
	public static void pageLoadTimeout(int second) {
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(second));
	}

	/**
	 * 要素の可視性タイムアウト設定
	 * @param locater
	 * @param second
	 */
	public static void visibilityTimeout(By locater, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
	}

	/**
	 * 指定ピクセル分だけスクロール
	 * @param pixel
	 */
	public static void scrollBy(String pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + pixel + ");");
	}

	/**
	 * 指定位置までスクロール
	 * @param pixel
	 */
	public static void scrollTo(String pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0," + pixel + ");");
	}

	/**
	 * 要素の位置までスクロール
	 * 
	 * @author 別所
	 * @param element
	 */
	public static void scrollIntoView(WebElement element) {
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
	}

	/**
	 * エビデンス取得
	 * @param instance
	 */
	public static void getEvidence(Object instance) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getEnclosingClass().getSimpleName();
			String methodName = instance.getClass().getEnclosingMethod().getName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * エビデンス取得（サフィックスあり）
	 * @param instance
	 * @param suffix
	 */
	public static void getEvidence(Object instance, String suffix) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getEnclosingClass().getSimpleName();
			String methodName = instance.getClass().getEnclosingMethod().getName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + "_" + suffix + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
