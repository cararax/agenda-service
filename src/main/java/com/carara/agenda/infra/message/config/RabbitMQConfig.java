package com.carara.agenda.infra.message.config;


import com.carara.agenda.infra.message.ResultListener;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    //Client
    @Value("${rabbitmq.result.exchange}")
    public String resultExchange;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(resultExchange);
    }

    @Bean
    public ResultListener client() {
        return new ResultListener();
    }
}
