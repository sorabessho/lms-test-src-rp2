package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

	/**
	 * 勤怠情報直接編集画面でのエラーメッセージの検証
	 * 
	 * @param expectedMessage 期待するエラーメッセージ
	 */
	static void checkErrorMessage(String expectedMessage) {
		pageLoadTimeout(10);
		String errorMessageXpath = "//*[@id='main']/div/div/ul/li/span";
		WebElement errorMessagElement = webDriver.findElement(By.xpath(errorMessageXpath));
		assertEquals(expectedMessage, errorMessagElement.getText());
	}

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//URLに遷移
		goTo("http://localhost:8080/lms/");

		//画面タイトル検証
		pageLoadTimeout(10);
		WebElement titleElement = webDriver.findElement(By.tagName("h2"));
		assertEquals("ログイン", titleElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//画面操作
		WebElement idElement = webDriver.findElement(By.id("loginId"));
		idElement.clear();
		idElement.sendKeys("StudentAA01");

		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("TestUser1234");

		WebElement loginBtnElement = webDriver.findElement(By.cssSelector("input[type='submit'][value='ログイン']"));
		loginBtnElement.click();

		//画面タイトル検証
		visibilityTimeout(By.cssSelector("li.active"), 10);
		WebElement titleElement = webDriver.findElement(By.cssSelector("li.active"));
		assertEquals("コース詳細", titleElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		//画面操作
		WebElement attendanceLinkElement = webDriver.findElement(By.linkText("勤怠"));
		attendanceLinkElement.click();

		acceptJSAlert();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		//画面操作
		WebElement directEditLinkElement = webDriver.findElement(By.linkText("勤怠情報を直接編集する"));
		directEditLinkElement.click();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		//画面操作
		Select endMinuteSelect = new Select(webDriver.findElement(By.id("endMinute1")));
		endMinuteSelect.selectByIndex(0);

		clickUpdateBtn();

		acceptJSAlert();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//エラーメッセージ検証
		checkErrorMessage("* 退勤時間が正しく入力されていません。");

		//ボックスエラー表示検証
		checkBoxError(By.id("endMinute1"));

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		//画面操作
		Select endMinuteSelect = new Select(webDriver.findElement(By.id("endMinute1")));
		endMinuteSelect.selectByVisibleText("15");

		Select startHourElement = new Select(webDriver.findElement(By.id("startHour1")));
		Select startMinuteElement = new Select(webDriver.findElement(By.id("startMinute1")));

		startHourElement.selectByIndex(0);
		startMinuteElement.selectByIndex(0);

		clickUpdateBtn();

		acceptJSAlert();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//エラーメッセージ検証
		checkErrorMessage("* 出勤情報がないため退勤情報を入力出来ません。");

		//ボックスエラー表示検証
		checkBoxError(By.id("startHour1"));
		checkBoxError(By.id("startMinute1"));

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		//画面操作
		Select startHourElement = new Select(webDriver.findElement(By.id("startHour1")));
		Select startMinuteElement = new Select(webDriver.findElement(By.id("startMinute1")));
		Select endHourSelect = new Select(webDriver.findElement(By.id("endHour1")));
		Select endMinuteSelect = new Select(webDriver.findElement(By.id("endMinute1")));

		startHourElement.selectByVisibleText("12");
		startMinuteElement.selectByVisibleText("30");
		endHourSelect.selectByVisibleText("10");
		endMinuteSelect.selectByVisibleText("10");

		clickUpdateBtn();

		acceptJSAlert();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//エラーメッセージ検証
		checkErrorMessage("* 退勤時刻[1]は出勤時刻[1]より後でなければいけません。");

		//ボックスエラー表示検証
		checkBoxError(By.id("endHour1"));
		checkBoxError(By.id("endMinute1"));

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		//画面操作
		Select startHourElement = new Select(webDriver.findElement(By.id("startHour1")));
		Select startMinuteElement = new Select(webDriver.findElement(By.id("startMinute1")));
		Select endHourSelect = new Select(webDriver.findElement(By.id("endHour1")));
		Select endMinuteSelect = new Select(webDriver.findElement(By.id("endMinute1")));
		Select blankTimeSelect = new Select(
				webDriver.findElement(By.cssSelector("select[name='attendanceList[1].blankTime']")));

		startHourElement.selectByVisibleText("09");
		startMinuteElement.selectByVisibleText("00");
		endHourSelect.selectByVisibleText("12");
		endMinuteSelect.selectByVisibleText("00");
		blankTimeSelect.selectByVisibleText("4時間");

		clickUpdateBtn();

		acceptJSAlert();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//エラーメッセージ検証
		checkErrorMessage("* 中抜け時間が勤務時間を超えています。");

		//ボックスエラー表示検証
		checkBoxError(By.cssSelector("select[name='attendanceList[1].blankTime']"));

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		//画面操作
		Select startHourElement = new Select(webDriver.findElement(By.id("startHour1")));
		Select startMinuteElement = new Select(webDriver.findElement(By.id("startMinute1")));
		Select endHourSelect = new Select(webDriver.findElement(By.id("endHour1")));
		Select endMinuteSelect = new Select(webDriver.findElement(By.id("endMinute1")));
		Select blankTimeSelect = new Select(
				webDriver.findElement(By.cssSelector("select[name='attendanceList[1].blankTime']")));
		WebElement noteElement = webDriver.findElement(By.cssSelector("input[name='attendanceList[1].note']"));

		startHourElement.selectByVisibleText("08");
		startMinuteElement.selectByVisibleText("55");
		endHourSelect.selectByVisibleText("18");
		endMinuteSelect.selectByVisibleText("15");
		blankTimeSelect.selectByIndex(0);
		noteElement.clear();
		noteElement.sendKeys("あ".repeat(101));

		clickUpdateBtn();

		acceptJSAlert();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//エラーメッセージ検証
		checkErrorMessage("* 備考の長さが最大値(100)を超えています。");

		//ボックスエラー表示検証
		checkBoxError(By.cssSelector("input[name='attendanceList[1].note']"));

		//エビデンス取得
		getEvidence(new Object() {
		});
	}
}
