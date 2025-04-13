package Steps;

import java.io.IOException;
import org.openqa.selenium.chrome.ChromeDriver;
import TestCases.TestBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Steps extends TestBase {
	public Steps() throws IOException {
		super();
	}
//	@Before
//	@Given("Setup Driver")
//	public void setup_driver() {
//		WebDriverManager.chromedriver().setup();
//		driver=new ChromeDriver();
//		driver.manage().window().maximize();
//	}
//	@After
//	public void end() {
//		driver.quit();
//	}

	@Given("Iam in {string}")
	public void iam_in(String string) {
		NavigateToURL(string);
	}

	@When("I Enter {string} in {string} By Xpath")
	public void i_enter_in_by_xpath(String string, String string2) {
		waitelementByXpath(string2);
	    WriteByXpath(string2, string);
	}
	@When("I Search for {string} in {string} By Xpath")
	public void i_search_for_by_xpath(String string, String string2) {
		waitelementByXpath(string2);
	    WriteByXpath(string2, string);
	    SubmitByXpath(string2);
	}
	@When("I Search for {string} in {string} By id")
	public void i_search_for_by_id(String string, String string2) {
		waitelementByid(string2);
	    WriteByid(string2, string);
	    SubmitByid(string2);
	}

	@When("I Click on {string} By Xpath")
	public void i_click_on_by_xpath(String string) {
		waitelementByXpath(string);
	    ClickByXpath(string);
	}

	@Then("Verify that {string} is Displayed By Xpath")
	public void verify_that_is_displayed_by_xpath(String string) {
		waitelementByXpath(string);
	   assertdisplayingByXpath(string);
	}
	@When("I Upload file in {string} with {string} file name By Xpath")
	public void i_upload_file_in_with_file_name_by_xpath(String string, String string2) {
		waitelementByXpath(string);
	   uploadfileByXpath(string, string2);
	}

	@When("I Upload file in {string} with {string} file name with Robot Class By Xpath")
	public void i_upload_file_in_with_file_name_with_robot_class_by_xpath(String string, String string2) throws InterruptedException {
	  waitelementByXpath(string);
		RobotClassByXpath(string, string2);
	}

	@When("Scroll to {string} By Xpath")
	public void scroll_to_by_xpath(String string) {
	   scrollByXpath(string);
	   waitelementByXpath(string);
	}

	@Then("Verify that {string} is equal {string} By Xpath")
	public void verify_that_is_equal_by_xpath(String string, String string2) {
		waitelementByXpath(string);
	    AssertequalmethodByXpath(string, string2);
	}

	@Then("Verify that {string} is contain {string} By Xpath")
	public void verify_that_is_contain_by_xpath(String string, String string2) {
		waitelementByXpath(string);
	    AssertContainByXpath(string, string2);
	}
	@When("I Accept Alert")
	public void i_accept_alert() throws InterruptedException {
		WaitAlert();
	   AcceptALert();
	}

	@When("I Dismiss ALert")
	public void i_dismiss_a_lert() throws InterruptedException {
	    WaitAlert();
	    DismissALert();
	}

	@When("I Submit {string} By Xpath")
	public void i_submit_by_xpath(String string) {
		waitelementByXpath(string);
	    SubmitByXpath(string);
	}

	@When("I Select {string} From {string} dropdown list By Xpath")
	public void i_select_from_dropdown_list_by_xpath(String string, String string2) {
		waitelementByXpath(string2);
	    SelectByXpath(string2, string);
	}

	@Then("Verify that {string} is Selected By Xpath")
	public void verify_that_is_selected_by_xpath(String string) {
		waitelementByXpath(string);
	    IsSelectedByXpath(string);
	}
	@When("I Click Right Click on {string} By Xpath")
	public void i_click_right_click_on_by_xpath(String string) {
		waitelementByXpath(string);
	  RightclickByXpath(string);
	}

	@When("I Hover on {string} By Xpath")
	public void i_hover_on_by_xpath(String string) {
		waitelementByXpath(string);
	    HoverByXpath(string);
	}

	@When("I Double Click on {string} By Xpath")
	public void i_double_click_on_by_xpath(String string) {
		waitelementByXpath(string);
	    DoubleclickByXpath(string);
	}

	@When("I Drag {string} and Drop {string} By Xpath")
	public void i_drag_and_drop_by_xpath(String string, String string2) {
		waitelementByXpath(string);
	    DragAndDropByXpath(string, string2);
	}

	@When("I Clear {string} By Xpath")
	public void i_clear_by_xpath(String string) {
		waitelementByXpath(string);
	    ClearByXpath(string);
	}
	@When("I Download file by clicking on {string} By Xpath")
	public void i_download_file_by_clicking_on_by_xpath(String string) {
		waitelementByXpath(string);
	    downloadfileByXpath(string);
	}

	@When("I Forward")
	public void i_forward() {
	    Forward();
	}

	@When("I Back")
	public void i_back() {
	    Back();
	}

	@When("I Refresh")
	public void i_refresh() {
	    Refresh();
	}
	@When("I Enter {string} in {string} By id")
	public void i_enter_in_by_id(String string, String string2) {
		waitelementByid(string2);
	    WriteByid(string2, string);
	}

	@When("I Click on {string} By id")
	public void i_click_on_by_id(String string) {
		waitelementByid(string);
	    ClickByid(string);
	}

	@Then("Verify that {string} is Displayed By id")
	public void verify_that_is_displayed_by_id(String string) {
		waitelementByid(string);
	   assertdisplayingByid(string);
	}
	@When("I Upload file in {string} with {string} file name By id")
	public void i_upload_file_in_with_file_name_by_id(String string, String string2) {
		waitelementByid(string);
	   uploadfileByid(string, string2);
	}

	@When("I Upload file in {string} with {string} file name with Robot Class By id")
	public void i_upload_file_in_with_file_name_with_robot_class_by_id(String string, String string2) throws InterruptedException {
		waitelementByid(string);
		RobotClassByid(string, string2);
	}

	@When("Scroll to {string} By id")
	public void scroll_to_by_id(String string) {
	   scrollByid(string);
	   waitelementByid(string);
	}

	@Then("Verify that {string} is equal {string} By id")
	public void verify_that_is_equal_by_id(String string, String string2) {
		waitelementByid(string);
	    AssertequalmethodByid(string, string2);
	}

	@Then("Verify that {string} is contain {string} By id")
	public void verify_that_is_contain_by_id(String string, String string2) {
	    AssertContainByid(string, string2);
	}
	@When("I Submit {string} By id")
	public void i_submit_by_id(String string) {
		waitelementByid(string);
	    SubmitByid(string);
	}

	@When("I Select {string} From {string} dropdown list By id")
	public void i_select_from_dropdown_list_by_id(String string, String string2) {
		waitelementByid(string2);
	    SelectByid(string2, string);
	}

	@Then("Verify that {string} is Selected By id")
	public void verify_that_is_selected_by_id(String string) {
		waitelementByid(string);
	    IsSelectedByid(string);
	}
	@When("I Click Right Click on {string} By id")
	public void i_click_right_click_on_by_id(String string) {
		waitelementByid(string);
	  RightclickByid(string);
	}

	@When("I Hover on {string} By id")
	public void i_hover_on_by_id(String string) {
		waitelementByid(string);
	    HoverByid(string);
	}

	@When("I Double Click on {string} By id")
	public void i_double_click_on_by_id(String string) {
		waitelementByid(string);
	    DoubleclickByid(string);
	}

	@When("I Drag {string} and Drop {string} By id")
	public void i_drag_and_drop_by_id(String string, String string2) {
		waitelementByid(string);
	    DragAndDropByid(string, string2);
	}

	@When("I Clear {string} By id")
	public void i_clear_by_id(String string) {
		waitelementByid(string);
	    ClearByid(string);
	}
	@When("I Download file by clicking on {string} By id")
	public void i_download_file_by_clicking_on_by_id(String string) {
		waitelementByid(string);
	    downloadfileByid(string);
	}


	

}
