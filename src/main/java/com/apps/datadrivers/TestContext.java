package com.apps.datadrivers;

import com.apps.base.CommonTestBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TestContext extends CommonTestBase {
	
	private String testcaseName;
	
	public void setTestcaseNameInReports(String testcaseName) {
		setTestcaseName(testcaseName);
		tests.set(reports.createTest(testcaseName));
		consoleOutput("\n\n========================================");
		consoleOutput("Selenium Grid: "+seleniumGrid);
	
	}

}
