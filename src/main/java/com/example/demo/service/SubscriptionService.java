package com.example.demo.service;

import com.example.demo.entity.Subscription;
import com.example.demo.entity.SubscriptionItem;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SubscriptionListRepository;
import com.example.demo.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubscriptionListRepository subscriptionListRepository;

    public Subscription createSubscriptionWithItems(Subscription subscription, List<SubscriptionItem> items) {
        // Calculate next delivery
        subscription.setNextDeliveryDate(LocalDateTime.now().plusMinutes(subscription.getFrequency()));
        Subscription savedSubscription = subscriptionRepository.save(subscription);

        // Save subscription items
        for (SubscriptionItem item : items) {
            item.setSubscription(savedSubscription); // set the foreign key
            subscriptionListRepository.save(item);
        }

        return savedSubscription;
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    public List<SubscriptionItem> getSubscriptionListBySubscriptionId(Long subscriptionId) {
        return this.subscriptionListRepository.findBySubscriptionId(subscriptionId);
    }

    public Subscription modifySubscription(Long id, int frequency, List<SubscriptionItem> updatedItems) {
        Subscription s = subscriptionRepository.findById(id).orElseThrow();

        s.setFrequency(frequency);
        s.setNextDeliveryDate(LocalDateTime.now().plusMinutes(frequency));
        subscriptionRepository.save(s);

        // Delete old items and save new ones
        subscriptionListRepository.deleteBySubscriptionId(id);

        for (SubscriptionItem item : updatedItems) {
            item.setSubscription(s);
            subscriptionListRepository.save(item);
        }

        return s;
    }

    public void pauseSubscription(Long id) {
        Subscription s = subscriptionRepository.findById(id).orElseThrow();
        s.setPaused(true);
        subscriptionRepository.save(s);
    }

    public void resumeSubscription(Long id) {
        Subscription s = subscriptionRepository.findById(id).orElseThrow();
        s.setPaused(false);
        subscriptionRepository.save(s);
    }

    public void deleteSubscription(Long id) {
        // Delete items first to maintain referential integrity
        subscriptionListRepository.deleteBySubscriptionId(id);
        subscriptionRepository.deleteById(id);
    }
}
