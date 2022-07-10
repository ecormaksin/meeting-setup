package meeting.presentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.web.servlet.MockMvc;

@Configuration
public class WebDriverConfig {

    @Bean
    @Scope("singleton")
    public WebDriverFactory webDriverFactory(MockMvc mockMvc) {
        return new WebDriverFactory(mockMvc);
    }
}
