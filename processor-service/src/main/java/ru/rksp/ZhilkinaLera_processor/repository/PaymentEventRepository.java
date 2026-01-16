package ru.rksp.ZhilkinaLera.processor.repository;

import ru.rksp.ZhilkinaLera.processor.entity.PaymentEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentEventRepository extends JpaRepository<PaymentEventEntity, Long> {
    long count();
}