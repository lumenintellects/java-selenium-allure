package demo.selenium.test.automation.base.factory.loader;

import io.github.bonigarcia.wdm.WebDriverManager;

public enum EdgeDriverLoader {
    EDGE_DRIVER_LOADER;

    EdgeDriverLoader() { WebDriverManager.chromedriver().setup(); }

    public static void load() {
    }
}
