package com.sprhib.iu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class UiTest {

	public static void main(String[] args){
		WebDriver driver = new FirefoxDriver();
		OrgUiTest.testOrg(driver);
		TeamUiTest.testTeam(driver);
		MemberUiTest.testMember(driver);
		LanguageSwitcherUiTest.testLangSwitch(driver);
		driver.quit();
	}
}
