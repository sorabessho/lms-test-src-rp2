package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
		checkTitleH2("ログイン");

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
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		WebElement loginBtnElement = webDriver.findElement(By.cssSelector("input[type='submit'][value='ログイン']"));

		idElement.clear();
		idElement.sendKeys("StudentAA01");
		passwordElement.clear();
		passwordElement.sendKeys("TestUser1234");
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
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		//画面操作
		WebElement attendanceBtnElement = webDriver.findElement(By.cssSelector("input[type='submit'][value='出勤']"));
		attendanceBtnElement.click();

		acceptJSAlert();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//出勤登録検証
		//現在時刻の用意
		String now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
		String attendanceTdXpath = "//tr[contains(@class,'info')]/td[3]";
		WebElement attendanceTdElement = webDriver.findElement(By.xpath(attendanceTdXpath));
		assertEquals(now, attendanceTdElement.getText());

		//エビデンス取得
		scrollBy("100");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		//画面操作
		WebElement livingBtnElement = webDriver.findElement(By.cssSelector("input[type='submit'][value='退勤']"));
		livingBtnElement.click();

		acceptJSAlert();

		//画面タイトル検証
		checkTitleH2("勤怠管理");

		//退勤登録検証
		//現在時刻の用意
		String now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
		String livingTdXpath = "//tr[contains(@class,'info')]/td[4]";
		WebElement livingTdElement = webDriver.findElement(By.xpath(livingTdXpath));
		assertEquals(now, livingTdElement.getText());

		//エビデンス取得
		scrollBy("100");
		getEvidence(new Object() {
		});

	}

}
