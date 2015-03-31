package com.sprhib.iu;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LanguageSwitcherUiTest {

	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		testLangSwitch(driver);
		driver.quit();
	}

	public static void testLangSwitch(WebDriver driver) {
		driver.get("http://localhost:8080/spring");
		if (driver.findElement(By.id("langbutton")).getText().equals("English")) {
			testEt(driver);
			testEn(driver);
		} else {
			testEn(driver);
			testEt(driver);
		}
	}

	private static void testEt(WebDriver driver) {
		driver.findElement(By.id("langbutton")).click();
		driver.findElement(By.id("lang_link_et")).click();
		Assert.assertEquals(driver.findElement(By.id("homelink")).getText(), "Kodu");
	}

	private static void testEn(WebDriver driver) {
		driver.findElement(By.id("langbutton")).click();
		driver.findElement(By.id("lang_link_en")).click();
		Assert.assertEquals(driver.findElement(By.id("homelink")).getText(), "Home");
	}
}
