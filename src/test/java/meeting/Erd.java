package meeting;

import jig.erd.JigErd;
import meeting.infrastructure.datasource.MeetingDBTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class Erd {

    @Container
    static final MeetingDBTestContainer dbTestContainer = MeetingDBTestContainer.getInstance();

    @Test
    void run(@Autowired DataSource dataSource) {
        JigErd.run(dataSource);
    }
}
