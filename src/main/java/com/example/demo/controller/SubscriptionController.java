package com.example.demo.controller;

import com.example.demo.dto.subscription.SubscriptionWrapper;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.SubscriptionItem;
import com.example.demo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // POST: Create subscription with associated items
    @PostMapping
    public Subscription create(
            @RequestBody SubscriptionWrapper wrapper
    ) {
        return subscriptionService.createSubscriptionWithItems(wrapper.getSubscription(), wrapper.getItems());
    }

    // GET: All subscriptions for a user
    @GetMapping("/user/{userId}")
    public List<Subscription> getUserSubs(@PathVariable Long userId) {
        return subscriptionService.getUserSubscriptions(userId);
    }

    // GET: All subscription items for a specific subscription
    @GetMapping("/{id}/items")
    public List<SubscriptionItem> getSubscriptionItems(@PathVariable Long id) {
        return subscriptionService.getSubscriptionListBySubscriptionId(id);
    }

    // PATCH: Modify frequency or items
    @PatchMapping("/{id}")
    public Subscription patchSubscription(
            @PathVariable Long id,
            @RequestParam(required = false) Integer frequencyInDays,
            @RequestBody(required = false) List<SubscriptionItem> updatedItems
    ) {
        return subscriptionService.modifySubscription(id, frequencyInDays, updatedItems);
    }

    // PATCH: Pause subscription
    @PatchMapping("/{id}/pause")
    public void pause(@PathVariable Long id) {
        subscriptionService.pauseSubscription(id);
    }

    // PATCH: Resume subscription
    @PatchMapping("/{id}/resume")
    public void resume(@PathVariable Long id) {
        subscriptionService.resumeSubscription(id);
    }

    // DELETE: Cancel subscription
    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
    }
}
