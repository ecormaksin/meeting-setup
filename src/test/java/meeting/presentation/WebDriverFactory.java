package meeting.presentation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;

public class WebDriverFactory implements FactoryBean<WebDriverFactory> {

    private final HtmlUnitDriver driver;

    WebDriverFactory(MockMvc mockMvc) {
        this.driver = MockMvcHtmlUnitDriverBuilder
                .mockMvcSetup(mockMvc)
                .build();
    }

    @Override
    public WebDriverFactory getObject() throws Exception {
        return this;
    }

    @Override
    public Class<WebDriverFactory> getObjectType() {
        return WebDriverFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public WebDriver webDriver() {
        return driver;
    }
}
