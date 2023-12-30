package practice.telebot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@PropertySource("classpath:application.properties")
public class botConfig {
    
    @Value("${telegram.token}")
    private String botToken;

    @Bean
    public Bot createBot() {
        Bot bot = new Bot();
        bot.setBotToken(botToken);
        return bot;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("application.properties"));
        return configurer;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/json").build();
    }

    @Bean
    public GetFoodSvc getFoodSvc() {
        return new GetFoodSvc();
    }
}
