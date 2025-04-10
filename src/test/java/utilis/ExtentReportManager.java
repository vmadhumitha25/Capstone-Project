package utilis;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    public static ExtentReports extent;

    public static ExtentReports getInstance() {
    		String path="C:\\Users\\Tommy\\eclipse-workspace\\Capstone-Project\\reports\\capstone.html";
    		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
    		reporter.config().setReportName("Reporter");
    		reporter.config().setDocumentTitle("Test Report");
    		ExtentReports extent = new ExtentReports();
    		extent.attachReporter(reporter);
    		
    		return extent;
    	}
}
