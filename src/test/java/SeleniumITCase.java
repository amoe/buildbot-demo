
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class SeleniumITCase {
    @Test
    public void timelineClickOpensRelevantSessionPaper() throws Exception {
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1024,768));

        driver.get("http://192.168.122.43/");



        WebElement element = driver.findElement(By.className("paper-pad"));
        List<WebElement> canvases = driver.findElements(By.cssSelector("div#div_g canvas"));
        System.out.println("Found canvases:" + canvases);

        WebElement theCanvas = canvases.get(0);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", theCanvas);
//        Thread.sleep(500);

        Actions foo = new Actions(driver);
        foo.moveToElement(theCanvas).moveByOffset(10, 10).click().build().perform();

        // FIXME should be a a selenium wait.
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement innerMessagePaper = driver.findElement(By.id("inner-message-paper"));
        String elementText = innerMessagePaper.getText();

        assertThat(elementText).contains("FEBRUARY 2, 1835");
        driver.quit();
    }
}
