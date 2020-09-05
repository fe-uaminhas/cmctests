package test.cmc.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

//This support classes is created to add supporting functions / methods
//for the FrontEnd Testing tasks
public class support {

    WebDriver support;
    public support(WebDriver driver) {
        this.support = driver;
    }

    String rowLocator = "//table//tbody//tr[@style][%s]//td[%s]";
    //This method checks the validity of range and generated random numbers
    //within the given range
    //Used in Front-End Task 2
    public static int getRandomInteger(int min, int max){
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range given");
        }
        return ((int) (Math.random()*(max - min))) + min;
    }

    //Converts the String Values into Integers
    public static int [] getFilterRangeIntegers(String range){
        String[] parts = range.split(" - ");
        int min = Integer.parseInt(parts[0]);
        int max = Integer.parseInt(parts[1]);
        int A[] ={min, max};
        return A;
    }

    //Following methods are to find CC details from their IDs on CMC web
    public String getCCNamefromId(int id) {
        String name = "";
        String s = Integer.toString(id);
        rowLocator = String.format(rowLocator, s, "2");
        name = support.findElement(By.xpath(rowLocator)).getText();
        System.out.println(name);
        return name;
    }
}
