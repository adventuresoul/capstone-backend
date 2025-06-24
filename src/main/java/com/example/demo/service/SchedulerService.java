package com.example.demo.service;

import com.example.demo.entity.Delivery;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.SubscriptionItem;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.repository.SubscriptionListRepository;
import com.example.demo.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Scheduled(initialDelay = 3 * 60 * 1000, fixedRate = 10 * 60 * 1000) // Every 10 minutes
    public void generateDueDeliveries() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime range = now.plusMinutes(10);

        System.out.println("🔄 Scheduler triggered at: " + now);
        System.out.println("➡️  Checking for subscriptions due before: " + range);

        List<SubscriptionItem> dueSubscriptions = this.subscriptionListRepository
                .findByPausedFalseAndNextDeliveryDateBefore(range);

        System.out.println("📦 Found " + dueSubscriptions.size() + " subscription items due for delivery");

        for (SubscriptionItem item : dueSubscriptions) {
            Subscription subscription = item.getSubscription();

            Delivery delivery = new Delivery();
            delivery.setUser(subscription.getUser());
            delivery.setDeliveryDate(subscription.getNextDeliveryDate());
            delivery.setSubscriptionItem(item);
            delivery.setStatus("OUT FOR DELIVERY");

            deliveryRepository.save(delivery);

            System.out.println("✅ Created delivery for item ID: " + item.getId() +
                    ", Product ID: " + item.getProduct().getId() +
                    ", User ID: " + subscription.getUser().getId() +
                    ", Delivery time: " + delivery.getDeliveryDate());

            // Update subscription's next delivery date
            LocalDateTime oldDate = subscription.getNextDeliveryDate();
            subscription.setNextDeliveryDate(oldDate.plusMinutes(subscription.getFrequency()));
            subscriptionRepository.save(subscription);

            System.out.println("📅 Updated next delivery date for Subscription ID: " + subscription.getId() +
                    " → " + subscription.getNextDeliveryDate());
        }

        System.out.println("✅ Scheduler run completed.\n");
    }
}
