package test.cmc.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.util.concurrent.TimeUnit;

public class HomePage {

    WebDriver homepage;
    public HomePage(WebDriver driver) {
        this.homepage = driver;
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    private String url = "https://coinmarketcap.com/";

    // defining web elements / locators here
    String ellipsis = "//table//tbody//tr[@style][%s]//td[last()]//*[local-name()=\"svg\"]";
    By btnViewAll = By.xpath("//a[contains(., \"View All\")]");
    By rows = By.xpath("//table//tbody//tr[@style]");
    By addToWatchlist = By.xpath("//span[contains(., \"Add to Watchlist\")]");

    // Cryptocurrency Tab drop down menu locators
    By menuCryptocurrencyTab = By.xpath("//ul//span[contains(., \"Cryptocurrencies\")]");
    By fullListAllCryptocurrencies = By.xpath("//ul[@role]//li[.//a[contains(., \"Full List\")]][1]");
    By fullListCoinsOnly = By.xpath("//ul[@role]//li[.//a[contains(., \"Full List\")]][2]");
    By fullListTokensOnly = By.xpath("//ul[@role]//li[.//a[contains(., \"Full List\")]][3]");

    // Filters related locators
    By btnFilters = By.xpath("//button[contains(., \"Filters\")]");

    By btnfilterMarketCap = By.xpath("//button[contains(., \"Market Cap\")]");
    By btnfilterPrice = By.xpath("//button[contains(., \"Price\")]");
    By btnfilterVolume = By.xpath("//button[contains(., \"Volume\")]");


    By minValueforfilter = By.xpath("//div[contains(@class, \"filters\")]//div[contains(@class, \"dropdown\")]//div[contains(@class, \"input\")][1]//input");
    By maxValueforfilter = By.xpath("//div[contains(@class, \"filters\")]//div[contains(@class, \"dropdown\")]//div[contains(@class, \"input\")][2]//input");
    By applyfilter = By.xpath("//button[contains(., \"Apply\")]");
    By closeCookiePolicyBanner = By.xpath("//*[@id=\"cmc-cookie-policy-banner\"]/div[2]");


    // web driver actions are implemented below
    public void clickBtnViewAll() {

        homepage.findElement(btnViewAll).click();
    }

    // Opens CMC Homepage
    public void open() {

        homepage.get(url);
    }

    public void setCloseCookiePolicyBanner() throws InterruptedException {
        Thread.sleep(1000);
        homepage.findElement(closeCookiePolicyBanner).click();

    }

    //Gets the number of Listed CCs
    public int numberOfListedCryptocurrencies() {
        int count = homepage.findElements(rows).size();
        return count;
    }

    //randomly selected CC IDs are added to watchlist
    //Some extra Hard waits had to be added to counter network and system sluggishness
    public void addToWatchlist(int randomNumberInRange) throws InterruptedException {

        Thread.sleep(5000);
        WebElement rowToMenu = homepage.findElement(By.xpath(String.format(ellipsis,randomNumberInRange)));
        ExpectedConditions.elementToBeClickable(rowToMenu);
        ((JavascriptExecutor) homepage).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });", rowToMenu);
        Thread.sleep(3000);
        Actions actions = new Actions(homepage);
        actions.moveToElement(rowToMenu).click().build().perform();
        Thread.sleep(5000);
        homepage.findElement(addToWatchlist).click();
    }

    //Opens the dropdown menu of Cryptocurrencies Tab
    public void openTabMenuCryptocurrencies() {

        WebElement x = homepage.findElement(menuCryptocurrencyTab);
        ExpectedConditions.elementToBeClickable(menuCryptocurrencyTab);
        ((JavascriptExecutor) homepage).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });", x);
        x.click();

    }

    //Displays the Full list of All CCs on CMC
    public void showAllCryptocurrenciesFullList() {

        WebElement x = homepage.findElement(fullListAllCryptocurrencies);
        ((JavascriptExecutor) homepage).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });", x);
        x.click();
    }

    //Displays the Full list of Coins
    public void showCoinsOnlyFullList() {

        WebElement x = homepage.findElement(fullListCoinsOnly);
        ((JavascriptExecutor) homepage).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });", x);
        x.click();
    }

    //Displays the full list of Tokens
    public void showTokensOnlyFullList() {

        WebElement x = homepage.findElement(fullListTokensOnly);
        ((JavascriptExecutor) homepage).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });", x);
        x.click();
    }

    //Displays available filters
    public void showFiltersMenu() {
        homepage.findElement(btnFilters).click();
    }

    //Inserts and applies values on MarketCap Filter
    public void applyFilterMarketCap(String marketCap) {
        if (!"All".equals(marketCap)) {
            String[] parts = marketCap.split(" - ");
            String min = parts[0];
            String max = parts[1];

            homepage.findElement(btnfilterMarketCap).click();
            homepage.findElement(minValueforfilter).sendKeys(min);
            homepage.findElement(maxValueforfilter).sendKeys(max);
            homepage.findElement(applyfilter).click();
        }

    }

    //Inserts and applies values on Price Filter
    public void applyFilterPrice(String price) {
        if (!"All".equals(price)) {
            String[] parts = price.split(" - ");
            String min = parts[0];
            String max = parts[1];

            homepage.findElement(btnfilterPrice).click();
            homepage.findElement(minValueforfilter).sendKeys(min);
            homepage.findElement(maxValueforfilter).sendKeys(max);
            homepage.findElement(applyfilter).click();
        }

    }

    //Inserts and applies values on Volume Filter
    public void applyFilterVolume(String volume) {
        if (!"All".equals(volume)) {
            String[] parts = volume.split(" - ");
            String min = parts[0];
            String max = parts[1];

            homepage.findElement(btnfilterPrice).click();
            homepage.findElement(minValueforfilter).sendKeys(min);
            homepage.findElement(maxValueforfilter).sendKeys(max);
            homepage.findElement(applyfilter).click();
        }

    }

}