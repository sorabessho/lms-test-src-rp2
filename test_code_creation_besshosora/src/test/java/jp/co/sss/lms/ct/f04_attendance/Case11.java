package jp.co.sss.lms.ct.f04_attendance;

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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
		pageLoadTimeout(10);
		WebElement titleElement = webDriver.findElement(By.tagName("h2"));
		assertEquals("勤怠管理", titleElement.getText());

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
		pageLoadTimeout(10);
		WebElement titleElement = webDriver.findElement(By.tagName("h2"));
		assertEquals("勤怠管理", titleElement.getText());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		//画面操作
		//全てのプルダウンボックスがはいっている日付の親要素を取得
		String attendanceTrXpath = "//*[@id='main']/div/div/form/table/tbody/tr";
		List<WebElement> attendanceTrElements = webDriver.findElements(By.xpath(attendanceTrXpath));
		//各日付の各プルダウンボックスを操作
		for (int i = 0; i < attendanceTrElements.size(); i++) {
			WebElement attendanceTrElement = attendanceTrElements.get(i);
			Select startHourSelect = new Select(attendanceTrElement.findElement(By.id("startHour" + i)));
			startHourSelect.selectByValue("8");

			Select startMinuteSelect = new Select(attendanceTrElement.findElement(By.id("startMinute" + i)));
			startMinuteSelect.selectByValue("55");

			Select endHourSelect = new Select(attendanceTrElement.findElement(By.id("endHour" + i)));
			endHourSelect.selectByValue("18");

			Select endMinuteSelect = new Select(attendanceTrElement.findElement(By.id("endMinute" + i)));
			endMinuteSelect.selectByValue("15");
		}

		WebElement updateBtnElement = webDriver.findElement(By.cssSelector("input[type='submit'][value='更新']"));
		scrollIntoView(updateBtnElement);
		updateBtnElement.click();

		acceptJSAlert();

		//画面タイトル検証
		pageLoadTimeout(10);
		WebElement titleElement = webDriver.findElement(By.tagName("h2"));
		assertEquals("勤怠管理", titleElement.getText());

		//勤怠情報更新検証
		String checkAttendanceTrXpath = "//*[@id='main']/div[@class='row']/div/table/tbody/tr";
		List<WebElement> checkAttendanceTrElements = webDriver.findElements(By.xpath(checkAttendanceTrXpath));
		for (WebElement checkAttendanceTrElement : checkAttendanceTrElements) {
			WebElement startElement = checkAttendanceTrElement.findElement(By.xpath("./td[3]"));
			WebElement endElement = checkAttendanceTrElement.findElement(By.xpath("./td[4]"));

			assertEquals("08:55", startElement.getText());
			assertEquals("18:15", endElement.getText());
		}

		//エビデンス取得
		scrollBy("100");
		getEvidence(new Object() {
		});
	}

}
