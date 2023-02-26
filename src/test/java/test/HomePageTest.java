package test;

import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.WomenJacketsPageObject;
import resources.Base;

public class HomePageTest extends Base {

	HomePageObject homePage;
	WomenJacketsPageObject womenJacketPage;
	String price;

	@Test(description = "Navigate to womens jacket page")
	public void WomenClothing() {
		homePage = new HomePageObject(driver);
		homePage.getWomenjackets();
	}

	@Test(dependsOnMethods = "WomenClothing", description = "Validate the search result")
	public void validateSearchResults() {
		homePage.validateResultDisplayed();
	}

	@Test(dependsOnMethods = "validateSearchResults", description = "Sort jacket name on the basis of its review stars in descending order and get those products whose rating is 4 or more than that")
	public void sortedProducts() {
		womenJacketPage = new WomenJacketsPageObject(driver);
		womenJacketPage.sortJacketwithRating();
	}

	@Test(dependsOnMethods = "sortedProducts", description = "Validation on price range")
	public void getPriceRange() {
		price = prop.getProperty("price"); // get price range
		womenJacketPage.priceRange(price);
	}

}
