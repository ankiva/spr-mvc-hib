package com.sprhib.iu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class OrgUiTest {

	public static void main(String[] args){
		WebDriver driver = new FirefoxDriver();
		testOrg(driver);
		driver.quit();
	}
	
	public static void testOrg(WebDriver driver){
        driver.get("http://localhost:8080/spring");

        navigateTo(driver, "organization-add-link");
        
        WebElement nameInput = driver.findElement(By.id("org-name"));
        String name = "muuha" + new SimpleDateFormat("yyyyMMddhh24mmss").format(new Date());
        nameInput.sendKeys(name);
        nameInput.submit();
        
        navigateTo(driver, "organization-list-link");
        
        Assert.assertTrue(driver.getPageSource().contains(name));
        
        WebElement elem = findAction(driver, name, "orgeditlink");
        elem.click();
        
        String newName = name.replace('m', 'k');
        
        WebElement editBox = driver.findElement(By.id("org-edit-name"));
        editBox.clear();
        editBox.sendKeys(newName);
        editBox.submit();
        
        navigateTo(driver, "organization-list-link");
        
        Assert.assertFalse(driver.getPageSource().contains(name));
        Assert.assertTrue(driver.getPageSource().contains(newName));
        
        Assert.assertNull(findRow(driver, name));
        Assert.assertNotNull(findRow(driver, newName));
        
        
        elem = findAction(driver, newName, "orgdeletelink");
        elem.click();
        
        navigateTo(driver, "organization-list-link");
        
        Assert.assertFalse(driver.getPageSource().contains(name));
        Assert.assertFalse(driver.getPageSource().contains(newName));
        
	}
	
	private static void navigateTo(WebDriver driver, String linkId){
		WebElement element = driver.findElement(By.id("organizations-link"));
		element.click();
		WebElement element1 = driver.findElement(By.id(linkId));
		element1.click();
	}
	
	private static WebElement findRow(WebDriver driver, String orgName){
		List<WebElement> rows = driver.findElement(By.id("org-list-table")).findElements(By.tagName("tr"));
        if(rows != null && !rows.isEmpty()){
        	for(WebElement row : rows){
        		WebElement nameCell = row.findElement(By.name("orgnamecell"));
        		if(nameCell != null && nameCell.getText().equals(orgName)){
        			return row;
        		}
        	}
        }
        return null;
	}
	
	private static WebElement findAction(WebDriver driver, String orgName, String linkName){
		WebElement row = findRow(driver, orgName);
		if(row != null){
			return row.findElement(By.name(linkName));
		}
		return null;
	}
}
