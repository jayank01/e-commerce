package com.epam.everest.LocalGoods.controller;

import com.epam.everest.LocalGoods.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{amount}")
    public ResponseEntity<?> paymentLink(@PathVariable double amount) throws StripeException {
        return new ResponseEntity<>(paymentService.paymentLink(amount), HttpStatus.OK);
    }
}
