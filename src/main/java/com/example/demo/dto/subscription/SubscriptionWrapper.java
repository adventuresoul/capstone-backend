package com.example.demo.dto.subscription;

import com.example.demo.entity.Subscription;
import com.example.demo.entity.SubscriptionItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SubscriptionWrapper {
    Subscription subscription;

    List<SubscriptionItem> items;
}
