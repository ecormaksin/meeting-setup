package meeting;

import meeting.infrastructure.datasource.DBUnitBaseTest;
import meeting.presentation.TestWebConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@AutoConfigureMockMvc
@Import({TestWebConfig.class})
public abstract class MeetingIntegrationTest extends DBUnitBaseTest {
}
