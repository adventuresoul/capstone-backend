package com.example.demo.controller;

import com.example.demo.entity.Delivery;
import com.example.demo.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    // Get all deliveries by user
    @GetMapping("/user/deliveries/{userId}")
    public List<Delivery> getAllDeliveriesByUser(@PathVariable Long userId){
        return this.deliveryService.getAllDeliveriesByUser(userId);
    }

    // Optional: Get deliveries by user and status (e.g., OUT FOR DELIVERY)
    @GetMapping("/user/{userId}/status/{status}")
    public List<Delivery> getDeliveriesByUserAndStatus(
            @PathVariable Long userId,
            @PathVariable String status) {
        return deliveryService.getDeliveriesByUserAndStatus(userId, status);
    }

    // Optional: Get all deliveries with a specific status
    @GetMapping("/status/{status}")
    public List<Delivery> getDeliveriesByStatus(@PathVariable String status) {
        return deliveryService.getDeliveriesByStatus(status);
    }


}
