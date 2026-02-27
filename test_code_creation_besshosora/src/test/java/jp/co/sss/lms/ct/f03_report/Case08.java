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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//画面操作
		String xpath = "//div[contains(@class,'panel-body')]"
				+ "//tr[td[normalize-space()='2022年10月2日(日)']]"
				+ "//input[@type='submit' and @value='詳細']";
		WebElement detailBtnElement = webDriver.findElement(By.xpath(xpath));
		scrollIntoView(detailBtnElement);
		detailBtnElement.click();

		//画面タイトル検証
		visibilityTimeout(By.cssSelector("li.active"), 10);
		WebElement titleElement = webDriver.findElement(By.cssSelector("li.active"));
		assertEquals("セクション詳細", titleElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//画面操作
		WebElement dailyReportBtn = webDriver
				.findElement(By.cssSelector("input[type='submit'][value='提出済み週報【デモ】を確認する']"));
		scrollIntoView(dailyReportBtn);
		dailyReportBtn.click();

		//タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		//画面操作
		WebElement gakusyuukoumokuElement = webDriver.findElement(By.id("intFieldName_0"));
		Select rikaidoSelect = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		WebElement mokuhyounotasseidoElement = webDriver.findElement(By.id("content_0"));
		WebElement syokannElement = webDriver.findElement(By.id("content_1"));
		WebElement issyuukannnohurikaeriElement = webDriver.findElement(By.id("content_2"));

		gakusyuukoumokuElement.clear();
		gakusyuukoumokuElement.sendKeys("テスト");
		rikaidoSelect.selectByVisibleText("3");
		mokuhyounotasseidoElement.clear();
		mokuhyounotasseidoElement.sendKeys("10");
		syokannElement.clear();
		syokannElement.sendKeys("受講生 レポート修正(週報) 正常系のテストです。");
		issyuukannnohurikaeriElement.clear();
		issyuukannnohurikaeriElement.sendKeys("テスト自動化はSelenium");

		clickSubmitBtn();

		//画面タイトル検証
		visibilityTimeout(By.cssSelector("li.active"), 10);
		WebElement titleElement = webDriver.findElement(By.cssSelector("li.active"));
		assertEquals("セクション詳細", titleElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		//画面操作
		String detailBtnXpath = "//tr["
				+ "td[1][normalize-space()='2022年10月2日(日)'] and "
				+ "td[2][normalize-space()='週報【デモ】']]"
				+ "//input[@type='submit' and @value='詳細']";
		WebElement detailBtnElement = webDriver.findElement(By.xpath(detailBtnXpath));
		scrollIntoView(detailBtnElement);
		detailBtnElement.click();

		//画面タイトル検証
		checkTitleH2("週報【デモ】 2022年10月2日");

		//週報内容検証
		String gakusyuukoumokuXpath = "//th[text()='学習項目']/../following-sibling::tr/td[1]";
		String rikaidoXpath = "//th[text()='学習項目']/../following-sibling::tr/td[2]";
		String mokuhyounotasseidoXpath = "//th[text()='目標の達成度']/following-sibling::td";
		String syokannXpath = "//th[text()='所感']/following-sibling::td";
		String issyuukannnohurikaeriXpath = "//th[text()='一週間の振り返り']/following-sibling::td";

		WebElement gakusyuukoumokuElement = webDriver.findElement(By.xpath(gakusyuukoumokuXpath));
		WebElement rikaidoElement = webDriver.findElement(By.xpath(rikaidoXpath));
		WebElement mokuhyounotasseidoElement = webDriver.findElement(By.xpath(mokuhyounotasseidoXpath));
		WebElement syokannElement = webDriver.findElement(By.xpath(syokannXpath));
		WebElement issyuukannnohurikaeriElement = webDriver.findElement(By.xpath(issyuukannnohurikaeriXpath));

		assertEquals("テスト", gakusyuukoumokuElement.getText());
		assertEquals("3", rikaidoElement.getText());
		assertEquals("10", mokuhyounotasseidoElement.getText());
		assertEquals("受講生 レポート修正(週報) 正常系のテストです。", syokannElement.getText());
		assertEquals("テスト自動化はSelenium", issyuukannnohurikaeriElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		}, "01");
		scrollBy("250");
		getEvidence(new Object() {
		}, "02");
	}
}
