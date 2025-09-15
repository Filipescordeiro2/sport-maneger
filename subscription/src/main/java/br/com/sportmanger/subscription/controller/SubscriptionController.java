package br.com.sportmanger.subscription.controller;

import br.com.sportmanger.subscription.dto.request.SubscriptionRequest;
import br.com.sportmanger.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public Object createSubscription(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        return subscriptionService.createSubscription(subscriptionRequest);
    }


}
