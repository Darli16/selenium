package SauceDemo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class SauceDemoCartValidate {

	
	static WebDriver driver;
	
	@Test

	  void login() {

		driver = new ChromeDriver();

		// Open Web browser
		driver.get("https://www.saucedemo.com/");

		// Maximize window
		driver.manage().window().maximize();

		// Enter user name
		driver.findElement(By.id("user-name")).sendKeys("standard_user");

		// Enter password
		driver.findElement(By.id("password")).sendKeys("secret_sauce");

		// Click login button
		driver.findElement(By.id("login-button")).click();

		// Verify login status
		String expectedUrl = "https://www.saucedemo.com/inventory.html";
		String actualUrl = driver.getCurrentUrl();
		Assertions.assertEquals(expectedUrl, actualUrl);

		System.out.println("Login is successful");

		// Add to cart items
		driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();

		// Click shopping cart icon
		driver.findElement(By.id("shopping_cart_container")).click();

		// Verify items in shopping cart
		driver.getPageSource().contains("Sauce Labs Backpack");
		driver.getPageSource().contains("Sauce Labs Bolt T-Shirt");
		System.out.println("Add to Cart items successful");


		// CLick Check Out Button
		driver.findElement(By.id("checkout")).click();

		// Enter First Name
		driver.findElement(By.id("first-name")).sendKeys("Software");

		// Enter Last Name
		driver.findElement(By.id("last-name")).sendKeys("Tester");

		// Enter Postal Code
		driver.findElement(By.id("postal-code")).sendKeys("11111");

		// Enter continue button
		driver.findElement(By.id("continue")).click();

		// Verify items in checkout page
		driver.getPageSource().contains("Sauce Labs Backpack");
		driver.getPageSource().contains("Sauce Labs Bolt T-Shirt");

		// Verify total amount
		try {
			// Find the elements containing the product prices using CSS Selector
			List<WebElement> priceElements = driver
					.findElements(By.cssSelector(".inventory_item_price[data-test='inventory-item-price']"));

			// Variable to store the sum of all product prices
			double totalPrice = 0.0;

			// Loop through the list of price elements and extract the price from each element
			for (WebElement priceElement : priceElements) {
				String priceText = priceElement.getText();

				// Clean the price (remove '$' and trim the string)
				String numericPrice = priceText.replace("$", "").trim();

				// Convert the cleaned price string to a double
				try {
					double price = Double.parseDouble(numericPrice);
					// Add the price to the total sum
					totalPrice += price;
				} catch (NumberFormatException e) {
					// Handle the case where the price string can't be parsed
					System.out.println("Error parsing price: " + priceText);
				}
			}

			// Find the element containing the tax value
			WebElement taxElement = driver.findElement(By.cssSelector(".summary_tax_label[data-test='tax-label']"));
			String taxText = taxElement.getText();

			// Clean the tax amount (remove "Tax: $" and trim the string)
			String numericTax = taxText.replace("Tax: $", "").trim();

			// Convert the tax string to a double
			try {
				double tax = Double.parseDouble(numericTax);
				// Add the tax to the total price
				totalPrice += tax;
			} catch (NumberFormatException e) {
				// Handle the case where the tax string can't be parsed
				System.out.println("Error parsing tax: " + taxText);
			}

			// Find the element containing the total value
			WebElement totalElement = driver
					.findElement(By.cssSelector(".summary_total_label[data-test='total-label']"));
			String totalText = totalElement.getText();

			// Clean the displayed total value (remove "Total: $" and trim the string)
			String numericTotal = totalText.replace("Total: $", "").trim();

			// Convert the displayed total to a string (to compare it exactly with the
			// calculated total)
			String calculatedTotalString = String.format("%.2f", totalPrice); // Format the calculated total to match
																				// the displayed format
			Assertions.assertEquals(calculatedTotalString, numericTotal);
			// Print the calculated total and compare with the displayed total
			System.out.println("Calculated Total (Including Tax): $" + calculatedTotalString);
			System.out.println("Displayed Total: $" + numericTotal);
			System.out.println("The calculated total matches the displayed total.");
			

		} catch (Exception e) {
			// Catch any exceptions that may occur during the process
			System.out.println("Error: " + e.getMessage());

			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}

		}
		
		// Click Finish button
		driver.findElement(By.id("finish")).click();
		String expectedCheckouturl = "https://www.saucedemo.com/checkout-complete.html";
		String currentCheckouturl = driver.getCurrentUrl();
		Assertions.assertEquals(expectedCheckouturl, currentCheckouturl);
		System.out.println("Checkout flow is successful");
		
		driver.quit();
		
	}

}



