package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		List<WebElement> lectureCategoryElements = webDriver.findElements(By.cssSelector("div.panel-body"));

		outer: for (WebElement lectureCategoryElement : lectureCategoryElements) {
			List<WebElement> sessionElements = lectureCategoryElement.findElements(By.tagName("tr"));
			for (WebElement sessionElement : sessionElements) {
				boolean isSubmit = sessionElement.findElement(By.cssSelector("td.w10per")).getText().equals("未提出");
				if (isSubmit) {
					WebElement btnElement = sessionElement
							.findElement(By.cssSelector("input[type='submit'][value='詳細']"));
					((JavascriptExecutor) webDriver).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
							btnElement);
					btnElement.click();
					break outer;
				}
			}
		}

		//画面タイトル検証
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
		WebElement dailyReportBtn = webDriver.findElement(By.cssSelector("input[type='submit']"));
		dailyReportBtn.click();

		//タイトル検証
		WebElement titleElement = webDriver.findElement(By.tagName("legend"));
		assertEquals("報告レポート", titleElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//画面操作
		WebElement textareaElement = webDriver.findElement(By.tagName("textarea"));
		textareaElement.clear();
		textareaElement.sendKeys("レポート新規登録(日報) 正常系のテストを行います。");

		WebElement submitBtnElement = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtnElement.click();

		//ボタンが変更されているか検証
		WebElement dailyReportBtn = webDriver.findElement(By.cssSelector("input[type='submit']"));
		assertEquals("提出済み日報【デモ】を確認する", dailyReportBtn.getAttribute("value"));

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

}
