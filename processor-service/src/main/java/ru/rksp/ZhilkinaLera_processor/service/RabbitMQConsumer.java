package ru.rksp.ZhilkinaLera.processor.service;

import ru.rksp.ZhilkinaLera.processor.dto.PaymentEventDto;
import ru.rksp.ZhilkinaLera.processor.entity.PaymentEventEntity;
import ru.rksp.ZhilkinaLera.processor.repository.PaymentEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RabbitMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final PaymentEventRepository repository;

    @Autowired
    public RabbitMQConsumer(PaymentEventRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue:events.raw}")
    @Transactional
    public void receiveMessage(PaymentEventDto eventDto) {
        logger.info("Received payment event from RabbitMQ: {}", eventDto.getPayerFullName());

        try {
            // Преобразуем DTO в Entity
            PaymentEventEntity entity = new PaymentEventEntity();
            entity.setPayerFullName(eventDto.getPayerFullName());
            entity.setAmount(eventDto.getAmount());
            entity.setCurrency(eventDto.getCurrency());
            entity.setPaymentMethod(eventDto.getPaymentMethod());
            entity.setEventDate(eventDto.getEventDate());

            // Сохраняем в PostgreSQL
            repository.save(entity);

            logger.info("Payment event saved to PostgreSQL with ID: {}", entity.getId());

        } catch (Exception e) {
            logger.error("Error processing payment event: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process payment event", e);
        }
    }
}