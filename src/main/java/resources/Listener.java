package resources;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;

public class Listener extends Base implements ITestListener {

	ExtentTest test;
	WebDriver driver;

	@Override
	public void onTestFailure(ITestResult arg0) {
		// Screenshot code

		try {
			driver = (WebDriver) arg0.getTestClass().getRealClass().getField("driver").get(arg0.getInstance()); // To
																												// bring
																												// the
																												// life
																												// of
																												// driver
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String filePath = null;

		try {
			filePath = takeScreenshot(arg0.getMethod().getMethodName(), driver); // "takeScreenshot" is the method in
																					// base.java
		} catch (IOException e) {
			e.printStackTrace();
		}

		test.addScreenCaptureFromPath(filePath, arg0.getMethod().getMethodName());
	}

	@Override
	public void onFinish(ITestContext arg0) {
		System.out.println("Finishing the test");
	}
}
