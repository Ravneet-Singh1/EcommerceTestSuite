package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import resources.AbstractComponents;

public class HomePageObject extends AbstractComponents {

	WebDriver driver;

	public HomePageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".level0.nav-2")
	private WebElement women;

	@FindBy(id = "ui-id-9")
	private WebElement tops;

	@FindBy(id = "ui-id-11")
	private WebElement jackets;

	@FindBy(css = ".toolbar-number")
	private WebElement toolbarNumber;

	@FindBy(css = ".item.product")
	private List<WebElement> itemsDisplayed;

	public void getWomenjackets() {

		wait2sec();
		Actions a = new Actions(driver);

		addWait(women);
		a.moveToElement(women).perform();

		addWait(tops);
		a.moveToElement(tops).perform();

		addWait(jackets);
		jackets.click();
	}

	public void validateResultDisplayed() {

		int displayedItemNumber = Integer.parseInt(toolbarNumber.getText());
		int totalItemsDisplayed = itemsDisplayed.size();

		Assert.assertEquals(displayedItemNumber, totalItemsDisplayed); // Validation

	}

}
