package jp.co.sss.lms.ct.f02_faq;

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

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//画面操作
		WebElement dropDownElement = webDriver.findElement(By.cssSelector("li.dropdown"));
		dropDownElement.click();

		WebElement linkElement = webDriver.findElement(By.cssSelector("a[href='/lms/help']"));
		linkElement.click();

		//画面タイトル検証
		WebElement titleElement = webDriver.findElement(By.tagName("h2"));
		assertEquals("ヘルプ", titleElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//画面操作
		WebElement linkElement = webDriver.findElement(By.cssSelector("a[href='/lms/faq']"));
		linkElement.click();

		//開いたタブに移動
		String currentTab = webDriver.getWindowHandle();
		System.out.println(currentTab);
		for (String newTab : webDriver.getWindowHandles()) {
			if (!newTab.equals(currentTab)) {
				webDriver.switchTo().window(newTab);
				break;
			}
		}

		//画面タイトル検証
		WebElement titleElement = webDriver.findElement(By.tagName("h2"));
		assertEquals("よくある質問", titleElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		//画面操作
		WebElement keywordElement = webDriver.findElement(By.id("form"));
		keywordElement.clear();
		keywordElement.sendKeys("助成金");

		WebElement searchBtnElement = webDriver.findElement(By.cssSelector("input[type='submit'][value='検索']"));
		searchBtnElement.click();

		//検索結果検証
		WebElement searchResultElement = webDriver.findElement(By.cssSelector("dt.mb10"));
		assertEquals("Q.助成金書類の作成方法が分かりません", searchResultElement.getText());

		//エビデンス取得
		scrollBy("190");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		//画面操作
		WebElement searchBtnElement = webDriver.findElement(By.cssSelector("input[type='button'][value='クリア']"));
		searchBtnElement.click();

		//「キーワード」テキストボックス検証
		WebElement keywordElement = webDriver.findElement(By.id("form"));
		assertEquals("", keywordElement.getAttribute("value"));

		//エビデンス取得
		scrollTo("0");
		getEvidence(new Object() {
		});
	}

}
