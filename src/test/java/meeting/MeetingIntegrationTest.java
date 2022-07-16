package meeting;

import meeting.infrastructure.datasource.DBUnitBaseTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class MeetingIntegrationTest extends DBUnitBaseTest {
}
