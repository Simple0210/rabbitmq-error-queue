package com.example.deadletterqueueexample.controller;

import com.example.deadletterqueueexample.config.RabbitMQProducerConfig;
import com.example.deadletterqueueexample.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public void sendPaymentToRabbitMQ(@RequestBody Payment payment){
        rabbitTemplate.convertAndSend(RabbitMQProducerConfig.SIMPLE_EXCHANGE,RabbitMQProducerConfig.SIMPLE_RK,payment);
    }
}
