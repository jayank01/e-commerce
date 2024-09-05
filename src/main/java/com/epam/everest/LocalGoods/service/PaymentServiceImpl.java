package com.epam.everest.LocalGoods.service;

import com.epam.everest.LocalGoods.dto.Message;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Override
    public Message paymentLink(double amount) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:3000/payment/success")
                        .setCancelUrl("http://localhost:3000/payment/failed")
                        .addLineItem(SessionCreateParams.LineItem.builder()
                                .setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("inr")
                                        .setUnitAmount((long) amount*100)
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Local Goods")
                                                .build())
                                        .build())
                                .build())
                        .build();

        Session session = Session.create(params);

        return Message.builder()
                .message(session.getUrl())
                .build();
    }
}
