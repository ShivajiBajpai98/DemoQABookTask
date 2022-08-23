package testscript;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import resuables.TestBase;
import utilites.TestNGDataProviderUtility;

import java.util.ArrayList;
import java.util.List;

public class Book extends TestBase {
    @Test(priority = 1)
    public void verifyTitle() {
        String actualTitle = driver.getTitle();
        String expectedTitle = "ToolsQA";
        Assert.assertEquals(actualTitle, expectedTitle, "Title doest not match. we are not on right page");
    }


    @Test(dataProviderClass = TestNGDataProviderUtility.class, dataProvider = "BookData", priority = 2)
    public void searchBooks(String tileOrAuthorOrPublisher) {
        findElement_Only("searchBox_XPATH").sendKeys(tileOrAuthorOrPublisher);
        List<WebElement> bookList = findElements_Only("books_XPATH");
        for (WebElement bName : bookList) {
            String bookName = bName.getText();
            System.out.println(bookName);
            System.out.println(tileOrAuthorOrPublisher);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(bookName.contains(tileOrAuthorOrPublisher), true, "Book is not present");
            softAssert.assertAll();
        }

    }

    @Test
    public void secondScenario() {

        List<WebElement> links = findElements_Only("titleLinks_XPATH");
        // System.out.println("The size of the list is: " + links.size());
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        SoftAssert softAssert = new SoftAssert();
        String requriedPublisher = configuration.getProperty("requriedPublisher");
        ArrayList<String> author = new ArrayList<String>();
        // Loop through links, click on each link, navigate back, reload the link and continue.
        for (int i = 0; i < links.size(); ++i) {
            jse.executeScript("arguments[0].scrollIntoView()", links.get(i));
            String expectedBookTitle = links.get(i).getText();
            links.get(i).click();
            String actualBookTitle = findElement_Only("actualBookTitle_XPATH").getText();
            // System.out.println(actualBookTitle);
            softAssert.assertEquals(actualBookTitle, expectedBookTitle);
            String publisherName = findElement_Only("publisherName_XPATH").getText();
            // softAssert.assertEquals(publisherName.equalsIgnoreCase(requriedPublisher),true,"publisher name is not same as requried publisher");
            if (publisherName.equalsIgnoreCase(requriedPublisher)) {
                String authorName = findElement_Only("authorName_XPATH").getText();
                author.add(authorName);
                softAssert.assertAll();
                //System.out.println(author);
            }
            softAssert.assertAll();
            driver.navigate().back();
            // reloading the list or there will be stale-element exception
            links = findElements_Only("titleLinks_XPATH");
        }
        System.out.println(author);
        // print the link text and href values
            /*  for (int i = 0; i < links.size(); ++i)
            {
                System.out.println(links.get(i).getText() + "--> " + links.get(i).getAttribute("href"));
            }*/

    }

}


