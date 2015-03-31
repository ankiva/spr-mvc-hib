package com.sprhib.iu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TeamUiTest {

	public static void main(String[] args){
		WebDriver driver = new FirefoxDriver();
		testTeam(driver);
		driver.quit();
	}
	
	public static void testTeam(WebDriver driver){
        driver.get("http://localhost:8080/spring");

        navigateTo(driver, "team-add-link");
        
        WebElement nameInput = driver.findElement(By.id("team-name"));
        String name = "teamui" + new SimpleDateFormat("yyyyMMddhh24mmss").format(new Date());
        nameInput.sendKeys(name);
        nameInput.submit();
        
        navigateTo(driver, "team-list-link");
        
        Assert.assertTrue(driver.getPageSource().contains(name));
        
        WebElement elem = findAction(driver, name, "teameditlink");
        elem.click();
        
        String newName = name.replace('t', 'k');
        
        WebElement editBox = driver.findElement(By.id("team-edit-name"));
        editBox.clear();
        editBox.sendKeys(newName);
        editBox.submit();
        
        navigateTo(driver, "team-list-link");
        
        Assert.assertFalse(driver.getPageSource().contains(name));
        Assert.assertTrue(driver.getPageSource().contains(newName));
        
        Assert.assertNull(findRow(driver, name));
        Assert.assertNotNull(findRow(driver, newName));
        
        elem = findAction(driver, newName, "teamdeletelink");
        elem.click();
        
        navigateTo(driver, "team-list-link");
        
        Assert.assertFalse(driver.getPageSource().contains(name));
        Assert.assertFalse(driver.getPageSource().contains(newName));
	}
	
	private static void navigateTo(WebDriver driver, String linkId){
		WebElement element = driver.findElement(By.id("teams-link"));
		element.click();
		WebElement element1 = driver.findElement(By.id(linkId));
		element1.click();
	}
	
	private static WebElement findRow(WebDriver driver, String teamName){
		List<WebElement> rows = driver.findElement(By.id("team-list-table")).findElements(By.tagName("tr"));
        if(rows != null && !rows.isEmpty()){
        	for(WebElement row : rows){
        		WebElement nameCell = row.findElement(By.name("teamnamecell"));
        		if(nameCell != null && nameCell.getText().equals(teamName)){
        			return row;
        		}
        	}
        }
        return null;
	}
	
	private static WebElement findAction(WebDriver driver, String teamName, String linkName){
		WebElement row = findRow(driver, teamName);
		if(row != null){
			return row.findElement(By.name(linkName));
		}
		return null;
	}
}
