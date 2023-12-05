package nl.hu.adsd.dtmreserveringen;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class DtmReservationsConfig {
    /**
     * This provides a configuration for the logger. It determines what needs to be logged and how.
     *
     * @return A request logging filter configured to some non-default settings
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }

    
	@Bean
	public JavaMailSender getJavaMailSender()
	{
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        

	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);

	    mailSender.setUsername("davidjanssen2001@gmail.com");
	    mailSender.setPassword("pcyzbxxqphromfin");

	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");

	    return mailSender;
	}

	@Bean
	public SimpleMailMessage emailTemplate()
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("somebody@gmail.com");
		message.setFrom("admin@gmail.com");
	    message.setText("FATAL - Application crash. Save your job !!");
	    return message;
	}
}
