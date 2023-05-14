package com.example.paymentservice.controller;


import com.example.paymentdatamodel.entity.PaymentInfo;
import com.example.paymentservice.config.paypal.config.PaypalPaymentIntent;
import com.example.paymentservice.config.paypal.config.PaypalPaymentMethod;
import com.example.paymentservice.config.paypal.service.PaypalService;
import com.example.paymentservice.config.paypal.utils.Utils;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.request.PaymentRequest;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.JSONFormatter;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/payment", produces = "application/json")
@RequiredArgsConstructor
public class PaymentController {
    public static final String URL_PAYPAL_SUCCESS = "/success";
    public static final String URL_PAYPAL_CANCEL = "/cancel";

    private final PaypalService paypalService;

    private final PaymentRepository paymentRepository;

    @GetMapping("/")
    public Iterable<PaymentInfo> showAll() {
        return paymentRepository.findAll();
    }

    @GetMapping("/{paymentId}")
    public PaymentInfo showDetail(@PathVariable("paymentId") Integer paymentId) {

        Optional<PaymentInfo> optionalPaymentInfo = paymentRepository.findById(paymentId);
        return optionalPaymentInfo.orElse(null);
    }

    @GetMapping("/detail/{paypalId}")
    public String detailPayment(@PathVariable("paypalId") String paypalId) throws PayPalRESTException {

        return JSONFormatter.toJSON(Payment.get(paypalService.getApiContext(), String.valueOf(paypalId)));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String pay(HttpServletRequest request,
                      @RequestBody PaymentRequest pr
    ) {
        String cancelUrl = "http://localhost:8009/payment" + URL_PAYPAL_CANCEL;
        String successUrl = "http://localhost:8009/payment" + URL_PAYPAL_SUCCESS + "?userId=" +
                pr.getUserId()+"&orderId="+pr.getOrderId();
        try {
            Payment payment = paypalService.createPayment(
                    pr.getSubTotal(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay() {

        return "cancel";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             @RequestParam("userId") Long userId,
                             @RequestParam("orderId") Integer orderId) {
        try {
            //if user order token jj do
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setOrderId(orderId);
                paymentInfo.setUserId(userId);
                paymentInfo.setPaymentDate(new Date());
                paymentInfo.setAmount(Float.valueOf(payment.getTransactions().get(0).getAmount().getTotal()));
                paymentInfo.setPaymentType("paypal");
                paymentInfo.setStatus("da xu ly");
                paymentInfo.setPayerId(payerId);
                paymentInfo.setPaypalId(paymentId);
                PaymentInfo p = paymentRepository.save(paymentInfo);
                return JSONFormatter.toJSON(p);
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "null";
    }
}
