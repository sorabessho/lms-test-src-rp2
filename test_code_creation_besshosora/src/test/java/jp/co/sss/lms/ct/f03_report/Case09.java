package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {
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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		//画面操作
		WebElement aElement = webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん"));
		aElement.click();

		//画面タイトル検証
		checkTitleH2("ユーザー詳細");

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//画面操作
		String editBtnXpath = "//tr["
				+ "td[1][normalize-space()='2022年10月2日(日)'] and "
				+ "td[2][normalize-space()='週報【デモ】']]"
				+ "//input[@type='submit' and @value='修正する']";
		WebElement editBtnElement = webDriver.findElement(By.xpath(editBtnXpath));
		scrollIntoView(editBtnElement);
		editBtnElement.click();

		//画面タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		//画面操作
		WebElement gakusyuukoumokuElement = webDriver.findElement(By.id("intFieldName_0"));
		gakusyuukoumokuElement.clear();

		clickSubmitBtn();

		//画面タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//エラー検証
		checkBoxError(By.id("intFieldName_0"));

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		//画面操作
		WebElement gakusyuukoumokuElement = webDriver.findElement(By.id("intFieldName_0"));
		Select rikaidoSelect = new Select(webDriver.findElement(By.id("intFieldValue_0")));

		gakusyuukoumokuElement.clear();
		gakusyuukoumokuElement.sendKeys("テスト");
		rikaidoSelect.selectByIndex(0);

		clickSubmitBtn();

		//画面タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//エラー検証
		checkBoxError(By.id("intFieldValue_0"));

		//エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		//画面操作
		Select rikaidoSelect = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		WebElement mokuhyounotasseidoElement = webDriver.findElement(By.id("content_0"));

		rikaidoSelect.selectByIndex(3);
		scrollIntoView(mokuhyounotasseidoElement);
		mokuhyounotasseidoElement.clear();
		mokuhyounotasseidoElement.sendKeys("数値以外を入れるとエラーになります。");

		clickSubmitBtn();

		//画面タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//エラー検証
		checkBoxError(By.id("content_0"));

		//エビデンス取得
		mokuhyounotasseidoElement = webDriver.findElement(By.id("content_0"));
		scrollIntoView(mokuhyounotasseidoElement);
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		//画面操作
		WebElement mokuhyounotasseidoElement = webDriver.findElement(By.id("content_0"));
		scrollIntoView(mokuhyounotasseidoElement);
		mokuhyounotasseidoElement.clear();
		mokuhyounotasseidoElement.sendKeys("11");

		clickSubmitBtn();

		//画面タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//エラー検証
		checkBoxError(By.id("content_0"));

		//エビデンス取得
		mokuhyounotasseidoElement = webDriver.findElement(By.id("content_0"));
		scrollIntoView(mokuhyounotasseidoElement);
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		//画面操作
		WebElement mokuhyounotasseidoElement = webDriver.findElement(By.id("content_0"));
		WebElement syokannElement = webDriver.findElement(By.id("content_1"));

		scrollIntoView(mokuhyounotasseidoElement);
		mokuhyounotasseidoElement.clear();
		scrollIntoView(syokannElement);
		syokannElement.clear();

		clickSubmitBtn();

		//画面タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//エラー検証
		checkBoxError(By.id("content_0"));
		checkBoxError(By.id("content_1"));

		//エビデンス取得
		mokuhyounotasseidoElement = webDriver.findElement(By.id("content_0"));
		scrollIntoView(mokuhyounotasseidoElement);
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		///画面操作
		String stringOver2001 = "あ".repeat(2001);

		WebElement mokuhyounotasseidoElement = webDriver.findElement(By.id("content_0"));
		WebElement syokannElement = webDriver.findElement(By.id("content_1"));
		WebElement issyuukannnohurikaeriElement = webDriver.findElement(By.id("content_2"));

		scrollIntoView(mokuhyounotasseidoElement);
		mokuhyounotasseidoElement.clear();
		mokuhyounotasseidoElement.sendKeys("10");
		scrollIntoView(syokannElement);
		syokannElement.clear();
		syokannElement.sendKeys(stringOver2001);
		scrollIntoView(issyuukannnohurikaeriElement);
		issyuukannnohurikaeriElement.clear();
		issyuukannnohurikaeriElement.sendKeys(stringOver2001);

		clickSubmitBtn();

		//画面タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//エラー検証
		checkBoxError(By.id("content_1"));
		checkBoxError(By.id("content_2"));

		//エビデンス取得
		syokannElement = webDriver.findElement(By.id("content_1"));
		scrollIntoView(syokannElement);
		getEvidence(new Object() {
		});
	}

}
