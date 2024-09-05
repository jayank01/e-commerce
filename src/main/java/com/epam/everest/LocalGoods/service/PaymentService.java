package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.dto.Message;
import com.stripe.exception.StripeException;

public interface PaymentService {
    Message paymentLink(double amount) throws StripeException;
}
