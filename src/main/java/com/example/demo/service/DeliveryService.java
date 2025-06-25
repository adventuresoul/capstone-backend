package com.example.demo.service;

import com.example.demo.entity.Delivery;
import com.example.demo.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;


    public List<Delivery> getAllDeliveriesByUser(Long id) {
        return this.deliveryRepository.findByUserId(id);
    }

    // Optional: Get deliveries by user and status
    public List<Delivery> getDeliveriesByUserAndStatus(Long userId, String status) {
        return deliveryRepository.findByUserIdAndStatus(userId, status);
    }

    // Optional: Get all deliveries with a specific status
    public List<Delivery> getDeliveriesByStatus(String status) {
        return deliveryRepository.findByStatus(status);
    }

}
