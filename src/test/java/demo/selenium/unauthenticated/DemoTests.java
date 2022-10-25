package demo.selenium.unauthenticated;

import demo.selenium.base.BaseTest;
import demo.selenium.test.automation.pages.DropdownPage;
import demo.selenium.test.automation.pages.MainPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("Demo")
@DisplayName("Demo tests")
public class DemoTests extends BaseTest {

    @Tag("Smoke")
    @Feature("Demo web app testing")
    @ParameterizedTest()
    @DisplayName("TC#None | Verify text on page")
    @Description("TC#None | Expected result: text is valid")
    @CsvFileSource(resources = "/testdata/demo_data.csv", numLinesToSkip = 1)
    public void verifyTextTest(String url) {
        assertThat(
                application
                        .open(url)
                        .waitForHeader()
                        .readTextFromHeaderAndValidate())
                .withFailMessage("Wrong text displayed on header").isTrue();
    }

    @Tag("Regression")
    @Feature("Demo web app testing")
    @ParameterizedTest()
    @DisplayName("TC-1111 | Verify button and go to the next page")
    @Description("Expected result: Button is displayed. Next page is opened. Dropdown displayed. Proper option is selected.")
    @CsvFileSource(resources = "/testdata/demo_data.csv", numLinesToSkip = 1)
    public void verifyBannerIsNotDisplayedAndNoCookiesSetUpAfterClosingTest(String url) {
        MainPage HomePage = application
                .open(url)
                .waitForHeader();
        assertThat(
                HomePage
                        .verifyDropdownIsDisplayed())
                .withFailMessage("Dropdown button is not displayed")
                .isTrue();
        //Navigate to other page
        DropdownPage DropdownPage = HomePage
                .goToDropdownPage();
        assertThat(
                DropdownPage
                        .verifyDropdownIsDisplayed())
                .withFailMessage("Dropdown is not displayed")
                .isTrue();
        assertThat(
                DropdownPage
                        .clickDropdown()
                        .clickOptionOne()
                        .validateOptionOneIsSelected())
                .withFailMessage("Option one is not selected")
                .isTrue();
        softAssertions.assertAll();
    }

}