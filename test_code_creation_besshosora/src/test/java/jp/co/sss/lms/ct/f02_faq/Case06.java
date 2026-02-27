package jp.co.sss.lms.ct.f02_faq;

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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//画面操作
		WebElement dropDownElement = webDriver.findElement(By.cssSelector("li.dropdown"));
		dropDownElement.click();

		WebElement linkElement = webDriver.findElement(By.linkText("ヘルプ"));
		linkElement.click();

		//画面タイトル検証
		checkTitleH2("ヘルプ");

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//画面操作
		WebElement linkElement = webDriver.findElement(By.linkText("よくある質問"));
		linkElement.click();

		//開いたタブに移動
		String currentTab = webDriver.getWindowHandle();
		for (String newTab : webDriver.getWindowHandles()) {
			if (!newTab.equals(currentTab)) {
				webDriver.switchTo().window(newTab);
				break;
			}
		}

		//画面タイトル検証
		checkTitleH2("よくある質問");

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//画面操作
		WebElement categorySearchElement = webDriver.findElement(By.linkText("【研修関係】"));
		categorySearchElement.click();

		//検索結果検証
		List<WebElement> searchResultElements = webDriver.findElements(By.cssSelector("dt.mb10"));
		assertEquals("Q.キャンセル料・途中退校について", searchResultElements.get(0).getText());
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？", searchResultElements.get(1).getText());

		//エビデンス取得
		scrollBy("800");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		//画面操作
		List<WebElement> searchResultElements = webDriver.findElements(By.cssSelector("dt.mb10"));
		searchResultElements.get(0).click();

		//想定質問回答検証
		final String EXPECTED_QUESTION_ANSEWER = "A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。";
		WebElement questionAnswerElement = webDriver.findElement(By.cssSelector("dd.fs18"));
		assertEquals(EXPECTED_QUESTION_ANSEWER, questionAnswerElement.getText());

		//エビデンス取得
		scrollBy("800");
		getEvidence(new Object() {
		});
	}

}
