package pageObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import resources.AbstractComponents;

public class WomenJacketsPageObject extends AbstractComponents {
	WebDriver driver;
	boolean rangeFunctionality;

	String value;
	String productPrice;
	int count;

	public WomenJacketsPageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//div[@role='tab'])[5]")
	private WebElement price;

	@FindBy(css = ".item.product.product-item")
	private List<WebElement> productList;

	@FindBy(css = ".filter-value")
	private WebElement filterPriceRange;

	@FindBy(css = ".item.product")
	private List<WebElement> allProducts;

	private By productDisplayed = By.cssSelector(".product-item-link");

	private By ratingResult = By.cssSelector(".rating-result");

	private By productDisplayedPrice = By.cssSelector(".price-wrapper ");

	public void sortJacketwithRating() {
		ArrayList<String> list = new ArrayList<String>(); // for name & rating
		Map<String, String> productNameandPrice = new HashMap<String, String>(); // for name and price with rating > 4

		for (int i = 0; i < allProducts.size(); i++) {
			String productName = allProducts.get(i).findElement(productDisplayed).getText();

			try {
				value = allProducts.get(i).findElement(ratingResult).getAttribute("title"); // Rating value

			} catch (NoSuchElementException e) {
				value = "0%";
			}

			String split[] = value.split("%");
			int ratingValue = Integer.parseInt(split[0]); // Removing % from ratingValue

			double number = (double) ratingValue / 20;
			double avgStarRating = Math.round(number * 10.0) / 10.0; // 4.4 'Average star rating'

			if (avgStarRating >= 4) {
				productPrice = allProducts.get(i).findElement(productDisplayedPrice).getText();
				productNameandPrice.put(productName, productPrice); // Add product name and its price to a MAP
				count++;
			}

			String ratingWithProductName = avgStarRating + " * avg rating for " + productName;
			list.add(ratingWithProductName); // add Product name with rating to list

		}

		Collections.sort(list, Collections.reverseOrder());

		System.out.println("Average rating with product name in descending order : ");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		System.out.println("\n\nWomen Jackets with rating 4 or more than that: "+ count +"\n");

		for (Map.Entry<String, String> entry : productNameandPrice.entrySet()) {
			System.out.println("Product Name : " + entry.getKey() + ", Price: " + entry.getValue());
		}
	}

	public void priceRange(String priceRange) {
		price.click();
		wait2sec();

		driver.findElement(By.partialLinkText(priceRange)).click(); // $50.00 - $59.99

		String filteredRange = filterPriceRange.getText().replace("$", ""); // 50.00 - 59.99
		System.out.println("Filter range : " + filteredRange);

		String split[] = filteredRange.split("-");
		double priceLowerRange = Double.parseDouble(split[0].trim()); // 50.00
		double priceUpperRange = Double.parseDouble(split[1].trim()); // 59.99

		for (int i = 0; i < productList.size(); i++) {

			String priceValue = productList.get(i).findElement(By.cssSelector(".price-wrapper")) // Product price
																									// (String format)
					.getAttribute("data-price-amount");

			Double productPrice = Double.parseDouble(priceValue); // Product price

			if (productPrice >= priceLowerRange && productPrice <= priceUpperRange) {
				rangeFunctionality = true;
			}

			Assert.assertTrue(rangeFunctionality); // validate whether Jackets are falling under this range

		}

		System.out.println("Total products with this price range : " + productList.size());
		System.out.println("Price range functionality is working as expected..");
	}
}
