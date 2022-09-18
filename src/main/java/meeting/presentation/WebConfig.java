package meeting.presentation;

import meeting.presentation.organizer.OrganizerNumberProxy;
import meeting.presentation.participant.ParticipantNumberProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class WebConfig {

    @Bean
    @Scope(
        value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OrganizerNumberProxy organizerNumberProxy() {
        return new OrganizerNumberProxy();
    }

    @Bean
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ParticipantNumberProxy participantNumberProxy() {
        return new ParticipantNumberProxy();
    }
}
