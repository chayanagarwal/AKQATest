package StepDefinitions;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CartSteps {
	
	WebDriver driver = null;
	String item_name;
	String item_qty;
	
	@Given("browser is open")
	public void browser_is_open() {
		System.out.println("Inside Step: browser is open");
		
		String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);
		
		//Set chrome driver from location
		System.setProperty("webdriver.chrome.driver",projectPath+"/src/test/resources/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		
		//Set driver timeouts
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
	}
	
	@And("user is on bunnings {string}")
	public void user_is_on_google_search_page(String website) {
		System.out.println("Inside Step: user is on"+website);
		
		//Navigate to website url passed as argument
		driver.navigate().to(website);
	}

	@When("user enters text in search box")
	public void user_enters_text_in_search_box() {
		System.out.println("Inside Step: user enters text in search box");
		
		//Find Search bar and enter the item to search
		driver.findElement(By.name("q")).sendKeys("paint");
		
		driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
	}

	@And("puts click & collect filter")
	public void puts_click_and_collect_filter() throws InterruptedException {
		System.out.println("Inside Step: puts click & collect filter");
		
		Thread.sleep(1000);
		// Find and select the filter button
		driver.findElement(By.className("filterButton")).click();
		
		// Find and select the Store & Availability option
		driver.findElement(By.cssSelector("#panel1bh-header > div.MuiButtonBase-root.MuiIconButton-root.MuiAccordionSummary-expandIcon.MuiIconButton-edgeEnd")).click();
		
		// Find and select the click & collect option
		driver.findElement(By.xpath("(//*[contains(@class,'productranges')]/button)[1]")).click();
		Thread.sleep(1000);
		
		// After applying the desired filter press show results
		driver.findElement(By.className("showResultButton")).click();
	}
	@And("Adds item to cart")
	public void Adds_item_to_cart() throws InterruptedException {
		System.out.println("Inside Step: Adds item to cart");
		
		//Find store element which is to be added to cart
		WebElement item = driver.findElement(By.xpath("(//*[contains(@class,'productTileTitle')]/p)[1]"));
		
		// Add item to cart
		driver.findElement(By.xpath("(//*[contains(@data-locator,'atcButton')])[1]")).click();
		Thread.sleep(1000);
		
		// After adding to cart take note of quantity of element added
		WebElement item_quantity = driver.findElement(By.xpath("(//*[contains(@data-locator,'Input_Quantity')])[1]"));
		
		//Store the item name & quantity to verified in cart
		item_name = item.getText();
		item_qty = item_quantity.getAttribute("value");
	}
	@And("goes to cart")
	public void goes_to_cart() {
		System.out.println("Inside Step: goes to cart");
		
		//Go to cart
		driver.findElement(By.cssSelector("#icon-cart")).click();
	}

	@Then("the item put into cart is present properly")
	public void verify_cart() throws InterruptedException {
		System.out.println("Inside Step: the item put into cart is present properly");
		
		// Find item name info and store to verify 
		WebElement cart_item = driver.findElement(By.xpath("(//*[contains(@datalocator,'Product_Name')])[1]"));
		String cart_item_name = cart_item.getText();
		
		// Find item quantity info and store to verify
		WebElement cart_item_quantity = driver.findElement(By.xpath("(//*[contains(@data-locator,'Input_Quantity')])[1]"));
		String cart_item_qty = cart_item_quantity.getAttribute("value");
		
		// Assert if the item name and quantity do not match properly
		Assert.assertEquals(item_name, cart_item_name);
		Assert.assertEquals(item_qty, cart_item_qty);
		
		// Close and quit driver
		driver.close();
		driver.quit();
	}

}
