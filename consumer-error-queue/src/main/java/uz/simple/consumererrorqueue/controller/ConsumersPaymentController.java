package uz.simple.consumererrorqueue.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.RestController;
import uz.simple.consumererrorqueue.config.RabbitMQConsumerConfig;
import uz.simple.consumererrorqueue.exceptions.MinusAmountException;
import uz.simple.consumererrorqueue.model.Payment;

import java.util.Date;

@RestController
public class ConsumersPaymentController {

    @RabbitListener(queues = {RabbitMQConsumerConfig.SIMPLE_QUEUE})
    public void handlePayment(Payment payment) throws MinusAmountException {
        /*
        PAYMENT OBEKTNING PAYMENTAMOUNTI 0 DAN KICHIK BO`LSA EXCEPTION QAYTARADI VA XABAR ERRORQUEUEGA TUSHADI
         */
        if (payment.getPaymentAmount()<0){
            throw new MinusAmountException("Amount < 0 ------->>> "+(new Date().getTime()));
        }else {
            System.out.println(payment);
        }
    }
}
