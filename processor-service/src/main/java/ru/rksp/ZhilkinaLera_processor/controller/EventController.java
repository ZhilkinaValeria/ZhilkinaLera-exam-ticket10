package ru.rksp.ZhilkinaLera.processor.controller;

import ru.rksp.ZhilkinaLera.processor.dto.CountResponse;
import ru.rksp.ZhilkinaLera.processor.repository.PaymentEventRepository;
import ru.rksp.ZhilkinaLera.processor.service.ClickHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "Events Processor", description = "API для обработки событий платежей")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final PaymentEventRepository repository;
    private final ClickHouseService clickHouseService;

    @Autowired
    public EventController(PaymentEventRepository repository, ClickHouseService clickHouseService) {
        this.repository = repository;
        this.clickHouseService = clickHouseService;
    }

    @PostMapping("/count")
    @Operation(summary = "Получить количество записей и сохранить в ClickHouse",
            description = "Возвращает количество записей в PostgreSQL и сохраняет его в ClickHouse")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получено и сохранено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    public ResponseEntity<CountResponse> getCountAndSave() {
        try {
            // Получаем количество записей из PostgreSQL
            long count = repository.count();
            logger.info("Total records in PostgreSQL: {}", count);

            // Сохраняем в ClickHouse
            clickHouseService.saveCountToClickHouse(count);

            // Возвращаем ответ
            CountResponse response = new CountResponse(count);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error processing count request: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/health")
    @Operation(summary = "Проверка здоровья сервиса")
    public ResponseEntity<String> healthCheck() {
        boolean clickhouseHealthy = clickHouseService.testConnection();

        String status = String.format(
                "Processor Service is running. ClickHouse connection: %s",
                clickhouseHealthy ? "OK" : "FAILED"
        );

        return ResponseEntity.ok(status);
    }
}