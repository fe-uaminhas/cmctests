package test.cmc.po;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class watchList {
    WebDriver watchlist;
    String watchlistURL = "https://coinmarketcap.com/watchlist/";

    public watchList(WebDriver driver) {
        this.watchlist = driver;
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // watchlist page locators
    By rows = By.xpath("//table//tbody//tr[@style]");

    //Opens a new tab and
    public void openWatchlistPageInNewTab() {
        JavascriptExecutor jse = (JavascriptExecutor)watchlist;
        jse.executeScript("window.open()");

        ArrayList<String> tabs = new ArrayList<String>(watchlist.getWindowHandles());
        watchlist.switchTo().window(tabs.get(1));
    }

    //gets watchlist page of CMC
    public void open() {
        watchlist.get(watchlistURL);
    }

    //gets the count of currencies watched
    public int numberOfWatchedCurrencies() {
        int count = watchlist.findElements(rows).size();
        return count;
    }
}
