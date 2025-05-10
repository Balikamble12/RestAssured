package demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class IRCTCDatePickerFixed {
	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get("https://www.irctc.co.in/nget/train-search");

		// Open calendar and get the current date value
		WebElement dateInput = driver.findElement(By.xpath("(//input[@type=\"text\"])[3]"));
		dateInput.click();
		String currentDate = dateInput.getAttribute("value");
		System.out.println("Current date: " + currentDate);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		try {
			LocalDate date = LocalDate.parse(currentDate, formatter);
			LocalDate newDate = date.plusDays(8);
			System.out.println("Date after 8 days: " + newDate.format(formatter));

			String newDay = String.valueOf(newDate.getDayOfMonth());
			int newYear = newDate.getYear();
			String newMonthName = newDate.getMonth().name().toLowerCase();

			// Read the currently displayed calendar header
			String displayedMonthYear = driver.findElement(By.className("ui-datepicker-title")).getText();
			System.out.println("Calendar shows: " + displayedMonthYear);

			// Navigate calendar if needed
			while (!displayedMonthYear.contains(String.valueOf(newYear))
					|| !displayedMonthYear.toLowerCase().contains(newMonthName)) {
				driver.findElement(By.xpath("//a[@title='Next']")).click();
				Thread.sleep(500);
				displayedMonthYear = driver.findElement(By.className("ui-datepicker-title")).getText();
			}

			// Select the day
			driver.findElement(By.xpath("//a[text()='" + newDay + "']")).click();

		} catch (DateTimeParseException e) {
			System.out.println("Error parsing the date: " + e.getMessage());
		}

		// Optional: Close the browser after a delay
		Thread.sleep(2000);
		driver.quit();
	}
}
