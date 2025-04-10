package utilis;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import base.ProjectSpecification;

public class Listener extends ProjectSpecification implements ITestListener {
	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
		String screenShotpath = null;
		try {
			screenShotpath = screenshot(result.getMethod().getMethodName() + " pass");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.addScreenCaptureFromPath(screenShotpath, "Passed Test Screenshot");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.fail(result.getThrowable());

		String screenShotpath = null;
		try {
			screenShotpath = screenshot(result.getMethod().getMethodName() + " fail");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		test.addScreenCaptureFromPath(screenShotpath, "Failed Test Screenshot");

	}

}
