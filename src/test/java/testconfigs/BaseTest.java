package testconfigs;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

import static core.ConciseAPI.getDriver;
import static core.ConciseAPI.setDriver;

public class BaseTest {

    @Before
    public void setUpDriver() {
        setDriver(new FirefoxDriver());
    }

    @After
    public void closeDriver() {
        getDriver(Thread.currentThread()).quit();
    }
}
