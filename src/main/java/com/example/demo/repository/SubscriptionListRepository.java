package com.example.demo.repository;

import com.example.demo.entity.SubscriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionListRepository extends JpaRepository<SubscriptionItem, Long> {
    List<SubscriptionItem> findBySubscriptionId(Long subscriptionId);

    void deleteBySubscriptionId(Long subscriptionId);

    @Query("SELECT s FROM SubscriptionItem s WHERE s.subscription.paused = false AND s.subscription.nextDeliveryDate < :duration")
    List<SubscriptionItem> findByPausedFalseAndNextDeliveryDateBefore(@Param("duration") LocalDateTime duration);

}
