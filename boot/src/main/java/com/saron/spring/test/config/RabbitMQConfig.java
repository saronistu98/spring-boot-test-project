package com.saron.spring.test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saron.spring.test.config.rabbitMQ.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.Scheduled;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.lang.Long.MAX_VALUE;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;
    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitMQProperties.getVirtualHost());
        connectionFactory.setHost(rabbitMQProperties.getHostname());
        connectionFactory.setPort(rabbitMQProperties.getPort());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate localAMQPTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(getJsonConverter());
        rabbitTemplate.setReplyTimeout(80000);
        rabbitTemplate.setReceiveTimeout(80000);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin localRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter getJsonConverter() {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().createXmlMapper(false).build();
        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, true);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Scheduled(fixedDelay = MAX_VALUE, initialDelayString = "#{${rabbit-mq.listener-containers-delay}}")
    public void startListenersAfterDelay() {
        rabbitListenerEndpointRegistry.getListenerContainers()
                .forEach(Lifecycle::start);
    }

}
