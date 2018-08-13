package extensions;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;

public class TestListener implements ITestListener {
    private static String fileSeperator = System.getProperty("file.separator");

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("***** Error " + result.getName() + " test has failed *****");

        WebDriver driver = TestBase.driver;

        String testClassName = getTestClassName(result.getInstanceName()).trim();

        String testMethodName = result.getName().toString().trim();
        String screenShotName = testMethodName + ".png";

        if (driver != null) {
            String imagePath = ".." + fileSeperator + "Screenshots"
                    + fileSeperator + "Results" + fileSeperator + testClassName
                    + fileSeperator
                    + takeScreenShot(driver, screenShotName, testClassName);
            System.out.println("Screenshot can be found : " + imagePath);
        }
    }

    private static String takeScreenShot(WebDriver driver,
                                         String screenShotName, String testName) {
        try {
            File file = new File("Screenshots" + fileSeperator + "Results");
            if (!file.exists()) {
                System.out.println("File created " + file);
            }

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File targetFile = new File("Screenshots" + fileSeperator + "Results" + fileSeperator + testName, screenShotName);
            FileUtils.copyFile(screenshotFile, targetFile);

            return screenShotName;
        } catch (Exception e) {
            System.out.println("An exception occured while taking screenshot " + e.getCause());
            return null;
        }
    }

    private String getTestClassName(String testName) {
        String[] reqTestClassname = testName.split("\\.");
        int i = reqTestClassname.length - 1;
        System.out.println("Required Test Name : " + reqTestClassname[i]);
        return reqTestClassname[i];
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {}

    @Override
    public void onTestSuccess(ITestResult iTestResult) {}

    @Override
    public void onTestSkipped(ITestResult iTestResult) {}

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {}

    @Override
    public void onStart(ITestContext iTestContext) {}

    @Override
    public void onFinish(ITestContext iTestContext) {}

}