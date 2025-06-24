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

    @GetMapping("/user/deliveries/{userId}")
    public List<Delivery> getAllDeliveriesByUser(@PathVariable Long userId){
        return this.deliveryService.getAllDeliveriesByUser(userId);
    }
}
