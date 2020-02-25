package com.perseus;

import java.util.concurrent.TimeUnit;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Excelreport {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "/usr/local/share/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.SECONDS);
		driver.get("http://localhost:9000/login.faces");
		String Username = "risk1";
		String Password = "risk1";
		driver.findElement(By.xpath("//*[@id=\"input_Username\"]")).sendKeys(Username); 
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"input_Passcode\"]")).sendKeys(Password);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"form-submit\"]")).click(); 
	
	}
	
}