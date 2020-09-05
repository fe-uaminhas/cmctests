package test.cmc.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import test.cmc.po.*;

import java.util.ArrayList;
import java.util.List;

public class CMCFrontEndStepDefs {

    private WebDriver driver = new FirefoxDriver();
    private HomePage homePage;
    private support support;
    private watchList watchlistPage;
    private int expectedCountOfWatchedCurrencies = 0;

    public CMCFrontEndStepDefs() {
        this.homePage = new HomePage(driver);
        this.watchlistPage = new watchList(driver);
        this.support = new support(driver);
    }

    @After
    public void tearDown() {
        if (driver !=null) driver.quit();
    }

    @Given("^CMC Homepage is opened$")
    public void cmc_homepage_is_opened() throws InterruptedException {
        homePage.open();
        homePage.setCloseCookiePolicyBanner();
    }

    @When("^user clicks View All to display all results$")
    public void user_clicks_view_all_to_display_all_results()  {
        homePage.clickBtnViewAll();
    }

    @Then("^(\\d+) results are displayed$")
    public void hundred_results_are_displayed(int expectedResults) throws InterruptedException {
        Thread.sleep(3000); //Explicit wait for the page to load fully
        int actualCount = homePage.numberOfListedCryptocurrencies();
        Assert.assertEquals(expectedResults, actualCount);
    }

    @When("^user randomly selects (\\d+) to (\\d+) cryptos and add to watchlist$")
    public void user_randomly_selects_5_to_10_cryptos_and_add_to_watchlist(int min, int max) throws InterruptedException {
        //calling randomNumberofCryptocurrency method to get a random count ..
        //that many random cryptocurrencies will be selected.
        int randomCountOfCC = support.getRandomInteger(min, max);
        expectedCountOfWatchedCurrencies =randomCountOfCC;
        System.out.printf("Count of random Cryptoccurencies to chose: %d\n", randomCountOfCC);

        //Selecting random CC IDs to watch for the count calculated randomly
        List<Integer> randomCCIDList = new ArrayList<Integer>();
        int j=0;
        do {
            j = support.getRandomInteger(1, 100); //homepage displays first 100 entries only
            if (!randomCCIDList.contains(j)) { // checks for any possible duplicates
                randomCCIDList.add(j); // adds to the list only unique values
            }
        } while (randomCCIDList.size() < randomCountOfCC);

        //Adding selected Ids to watchlist
        for (int i : randomCCIDList) {
            System.out.printf("Adding CC ID %d to watchlist \n",i);
            homePage.addToWatchlist(i);

        }
    }

    @And("^In a new browser tab, cmc watchlist is opened$")
    public void in_a_new_browser_tab_cmc_watchlist_is_opened()  {
        watchlistPage.openWatchlistPageInNewTab();
        watchlistPage.open();
    }

    @Then("^selected items are displayed correctly in watchlist$")
    public void selected_items_are_displayed_correctly_in_watchlist()  {
        int actualCount = watchlistPage.numberOfWatchedCurrencies();
        Assert.assertEquals(expectedCountOfWatchedCurrencies, actualCount);
    }

    @When("^any of the three full list items are selected in cryptocurrencies tab (.+)$")
    public void any_of_the_three_full_list_items_are_selected_in_cryptocurrencies_tab(String group) throws InterruptedException {
        homePage.openTabMenuCryptocurrencies();
        Thread.sleep(2500);
        if (group.equals("All Cryptocurrencies")) {
            homePage.showAllCryptocurrenciesFullList();
            //Thread.sleep(30000);
        }
        if (group.equals("Coins Only")) {
            homePage.showCoinsOnlyFullList();
        }
        if (group.equals("Tokens Only")) {
            homePage.showTokensOnlyFullList();
        }
    }

    @And("^available filters are applied (.+), (.+), & (.+)$")
    public void available_filters_are_applied_(String marketcap, String price, String volume) throws InterruptedException {
        Thread.sleep(10000);
        homePage.showFiltersMenu();
        Thread.sleep(3000); //sometimes filters don't appear after clicking the button
        homePage.applyFilterMarketCap(marketcap);
        Thread.sleep(1000);
        homePage.applyFilterPrice(price);
        Thread.sleep(1000);
        homePage.applyFilterVolume(volume);
        Thread.sleep(1000);

    }

    @Then("^filtered results should be displayed$")
    public void filtered_results_should_be_displayed() {

    }

}



