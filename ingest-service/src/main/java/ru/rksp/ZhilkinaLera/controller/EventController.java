package ru.rksp.ZhilkinaLera.controller;

import ru.rksp.ZhilkinaLera.dto.PaymentEventDto;
import ru.rksp.ZhilkinaLera.config.RabbitMQConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Events", description = "API для работы с событиями платежей")
public class EventController {

    private final RabbitTemplate rabbitTemplate;

    public EventController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/events")
    @Operation(summary = "Создание нового события платежа",
            description = "Принимает событие платежа и отправляет его в RabbitMQ очередь events.raw")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Событие успешно отправлено"),
            @ApiResponse(responseCode = "400", description = "Неверный формат данных"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<String> createEvent(@RequestBody PaymentEventDto eventDto) {
        try {
            rabbitTemplate.convertAndSend(
                    "",
                    RabbitMQConfig.EVENTS_RAW_QUEUE,
                    eventDto
            );

            String response = String.format(
                    "Событие платежа от %s успешно отправлено в очередь %s",
                    eventDto.getPayerFullName(),
                    RabbitMQConfig.EVENTS_RAW_QUEUE
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при отправке события: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    @Operation(summary = "Проверка работоспособности сервиса")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Ingest Service is running!");
    }
}