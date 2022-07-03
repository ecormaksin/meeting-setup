package meeting.infrastructure.datasource;

import org.testcontainers.containers.PostgreSQLContainer;

public class MeetingDBTestContainer extends PostgreSQLContainer<MeetingDBTestContainer> {

    private static final String IMAGE_VERSION = "postgres:14.3";
    private static MeetingDBTestContainer container;

    private MeetingDBTestContainer() {
        super(IMAGE_VERSION);
    }

    public static MeetingDBTestContainer getInstance() {
        if (container == null) {
            container = new MeetingDBTestContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        // do nothing, JVM handles shut down
    }
}
