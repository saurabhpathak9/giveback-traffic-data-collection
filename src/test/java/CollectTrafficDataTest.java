import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class CollectTrafficDataTest {

    private WebDriver driver;
    private static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
    private final static String urlEcoworld8btoEcoworldMainGate = "https://www.google.com/maps/dir/Morgan+Stanley,+Campus+8B,+Sarjapur+-+Marathalli+Outer+Ring+Road,+Varthur+Hobli," +
            "+RMZ+Ecoworld,+Bengaluru,+Karnataka+560103,+India/12.9297078,77.6849111/@12.9248697,77.6803899,16z" +
            "/data=!3m1!4b1!4m9!4m8!1m5!1m1!1s0x3bae130edc8aba17:0xf74ec741ed2eb403!2m2!1d77.68362!2d12.9197275!1m0!3e0";

    private final static String timeCssSelector = "body.keynav-mode-off.highres.screen-mode:nth-child(2) " +
            "div.vasquette.pane-open-mode div.widget-pane.widget-pane-visible " +
            "div.widget-pane-content.scrollable-y:nth-child(1) " +
            "div.widget-pane-content-holder div.section-layout.section-layout-root " +
            "div.section-layout div.section-directions-trip.clearfix.selected " +
            "div.section-directions-trip-description div:nth-child(1) " +
            "div.section-directions-trip-numbers:nth-child(1) " +
            "div.section-directions-trip-duration.delay-heavy:nth-child(1) > span:nth-child(1)";


    @Test
    public void testTimeNeededfromEcoworld8btoEcoworldMainGate() throws IOException {
        driver.get(urlEcoworld8btoEcoworldMainGate);
        String title = driver.getTitle();
        System.out.println("title = " + title);
        WebElement timeElement = driver.findElement(By.cssSelector(timeCssSelector));
        // System.out.println("timeElement.getText() = " + timeElement.getText());
        String timeNeeded = timeElement.getText().split(" ")[0];
        // System.out.println("timeNeeded = " + timeNeeded);
        FileWriter fileWriter = new FileWriter("src/test/resources/time-needed-data.csv", true); //Set true for append mode
        PrintWriter printWriter = new PrintWriter(fileWriter);
        LocalDate currentDate = LocalDate.now();
        String record = currentDate + "," + currentDate.getDayOfWeek() + ",1," + timeFormatter.format(new Date()) + "," + timeNeeded;
        printWriter.println(record);  //New line
        printWriter.close();
    }

    @BeforeTest
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}