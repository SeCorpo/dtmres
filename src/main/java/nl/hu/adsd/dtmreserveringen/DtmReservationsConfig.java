package nl.hu.adsd.dtmreserveringen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.filter.CommonsRequestLoggingFilter;


@Configuration
public class DtmReservationsConfig {

	
    @Value("${spring.mail.username}")
    private String emailUsername;
	
	
    @Value("${spring.mail.password}")
    private String emailPassword;

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
		

		Properties envProps = new Properties();
		try{
			envProps.load((new FileInputStream(".env")));
	
		} catch (IOException e){
			e.printStackTrace();
		}



		mailSender.setUsername(emailUsername);
	    mailSender.setPassword(emailPassword);
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");

	    return mailSender;
	}

	// @Bean
	// public SimpleMailMessage emailTemplate()
	// {
	// 	SimpleMailMessage message = new SimpleMailMessage();
	// 	message.setTo("somebody@gmail.com");
	// 	message.setFrom("admin@gmail.com");
	//     message.setText("FATAL - Application crash. Save your job !!");
	//     return message;
	// }
}
