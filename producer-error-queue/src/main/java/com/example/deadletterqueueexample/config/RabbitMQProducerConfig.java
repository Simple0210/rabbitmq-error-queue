package com.example.deadletterqueueexample.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfig {


    public static final String SIMPLE_EXCHANGE = "simple_exchange";
    public static final String ERROR_EXCHANGE = "error_exchange";
    public static final String SIMPLE_QUEUE = "simple_queue";
    public static final String ERROR_QUEUE = "error_queue";
    public static final String ERROR_RK = "error_rk";
    public static final String SIMPLE_RK = "simple_rk";

    @Bean
    public DirectExchange simpleExchange() {
        return new DirectExchange(SIMPLE_EXCHANGE);
    }

    @Bean
    public DirectExchange errorExchange() {
        return new DirectExchange(ERROR_EXCHANGE);
    }

    @Bean
    public Queue simpleQueue() {
        return QueueBuilder.durable(SIMPLE_QUEUE)
                .withArgument("x-dead-letter-exchange", ERROR_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ERROR_RK)
                .build();
    }

    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable(ERROR_QUEUE)
                .build();
    }

    @Bean
    public Binding simpleBind(Queue simpleQueue, DirectExchange simpleExchange) {
        return BindingBuilder.bind(simpleQueue).to(simpleExchange).with(SIMPLE_RK);
    }

    @Bean
    public Binding errorBind(Queue errorQueue, DirectExchange errorExchange) {
        return BindingBuilder.bind(errorQueue).to(errorExchange).with(ERROR_RK);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}