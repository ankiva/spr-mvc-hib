package com.sprhib.iu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MemberUiTest {


	public static void main(String[] args){
		WebDriver driver = new FirefoxDriver();
		testMember(driver);
		driver.quit();
	}
	
	public static void testMember(WebDriver driver){
        driver.get("http://localhost:8080/spring");

        navigateTo(driver, "member-add-link");
        
        Date d = new Date();
        String firstName = "memberuif" + new SimpleDateFormat("yyyyMMddhh24mmss").format(d);
        String lastName = "memberuil" + new SimpleDateFormat("yyyyMMddhh24mmss").format(d);
        
        WebElement firstNameInput = driver.findElement(By.id("member-firstname"));
        firstNameInput.sendKeys(firstName);
        WebElement lastNameInput = driver.findElement(By.id("member-lastname"));
        lastNameInput.sendKeys(lastName);
        firstNameInput.submit();
        
        navigateTo(driver, "member-list-link");
        
        Assert.assertTrue(driver.getPageSource().contains(firstName));
        Assert.assertTrue(driver.getPageSource().contains(lastName));
        
        WebElement elem = findAction(driver, firstName, lastName, "membereditlink");
        elem.click();
        
        String newFirstName = firstName.replaceFirst(".", "s");
        String newLastName = lastName.replaceFirst(".", "s");
        
        WebElement firstNameEditBox = driver.findElement(By.id("member-edit-firstname"));
        firstNameEditBox.clear();
        firstNameEditBox.sendKeys(newFirstName);
        WebElement lastNameEditBox = driver.findElement(By.id("member-edit-lastname"));
        lastNameEditBox.clear();
        lastNameEditBox.sendKeys(newLastName);
        firstNameEditBox.submit();
        
        navigateTo(driver, "member-list-link");
        
        Assert.assertFalse(driver.getPageSource().contains(firstName));
        Assert.assertTrue(driver.getPageSource().contains(newFirstName));
        Assert.assertFalse(driver.getPageSource().contains(lastName));
        Assert.assertTrue(driver.getPageSource().contains(newLastName));
        
        Assert.assertNull(findRow(driver, firstName, lastName));
        Assert.assertNotNull(findRow(driver, newFirstName, newLastName));
        
        elem = findAction(driver, newFirstName, newLastName, "memberdeletelink");
        elem.click();
        
        navigateTo(driver, "member-list-link");
        
        Assert.assertFalse(driver.getPageSource().contains(firstName));
        Assert.assertFalse(driver.getPageSource().contains(newFirstName));
        Assert.assertFalse(driver.getPageSource().contains(lastName));
        Assert.assertFalse(driver.getPageSource().contains(newLastName));
	}
	
	private static void navigateTo(WebDriver driver, String linkId){
		WebElement element = driver.findElement(By.id("members-link"));
		element.click();
		WebElement element1 = driver.findElement(By.id(linkId));
		element1.click();
	}
	
	private static WebElement findRow(WebDriver driver, String memberFirstName, String memberLastName){
		List<WebElement> rows = driver.findElement(By.id("member-list-table")).findElements(By.tagName("tr"));
        if(rows != null && !rows.isEmpty()){
        	for(WebElement row : rows){
        		WebElement firstNameCell = row.findElement(By.name("memberfirstnamecell"));
        		if(firstNameCell != null && firstNameCell.getText().equals(memberFirstName)){
        			WebElement lastNameCell = row.findElement(By.name("memberlastnamecell"));
        			if(lastNameCell != null && lastNameCell.getText().equals(memberLastName)){
        				return row;
        			}
        		}
        	}
        }
        return null;
	}
	
	private static WebElement findAction(WebDriver driver, String memberFirstName, String memberLastName, String linkName){
		WebElement row = findRow(driver, memberFirstName, memberLastName);
		if(row != null){
			return row.findElement(By.name(linkName));
		}
		return null;
	}
}
