package com.example.Artrental.novi.service;

import com.example.Artrental.novi.model.Art;
import com.example.Artrental.novi.model.Rent;
import com.example.Artrental.novi.payload.mollie.Json.amount;
import com.example.Artrental.novi.payload.mollie.Json.metadata;
import com.example.Artrental.novi.payload.mollie.PaymentCreateRequest;
import com.example.Artrental.novi.payload.mollie.PaymentCreateResponse;
import com.example.Artrental.novi.repository.ArtRepository;
import com.example.Artrental.novi.repository.RentRepository;
import com.example.Artrental.novi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static com.example.Artrental.novi.util.AppConstants.*;

@Service
public class PaymentService {
    @Autowired
    RentRepository rentRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ArtService.class);

    public ResponseEntity<PaymentCreateResponse> createPayment(Rent rent) throws MalformedURLException, URISyntaxException {
        String uri = API_MOLLIE_PAYMENT_URI;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "bearer " + TEST_API_MOLLIE_KEY);
        HttpEntity<String> request = new HttpEntity<String>("", headers);

        RequestEntity<PaymentCreateRequest> requestEntity = RequestEntity
                .post(new URL(uri).toURI())
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createPaymentRequest(rent));

        ResponseEntity<PaymentCreateResponse> result = restTemplate.exchange(requestEntity, PaymentCreateResponse.class);
        rent.setMolliePaymentId(result.getBody().getId());

        return result;
    }

    private PaymentCreateRequest createPaymentRequest(Rent rent){
        PaymentCreateRequest request = new PaymentCreateRequest();
        DecimalFormat f = new DecimalFormat("##.00");
        String value = String.valueOf(f.format(rent.getTotal()));
        amount amount = new amount();
        amount.setCurrency("EUR");
        amount.setValue(value);

        metadata metadata = new metadata();
        metadata.setOrder_id(String.valueOf(rent.getId()));

        request.setAmount(amount);
        request.setDescription("Order " + rent.getId());
        request.setMetadata(metadata);
        request.setRedirectUrl(AFTER_PAYMENT_REDIRECT_URL);
        //TODO: request.setWebhookUrl(); met NGROK
        return request;
    }
}
