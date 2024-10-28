    package com.example.demo.controller;

    import com.example.demo.model.PaymentMethod;
    import com.example.demo.service.PaymentMethodService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import java.util.List;
    import com.example.demo.model.PaymentMethod;

    @CrossOrigin(origins = "http://localhost:8080")
    @RestController
    @RequestMapping("/api/payment-methods")
    public class PaymentMethodController {

        @Autowired
        private PaymentMethodService paymentMethodService;


        @PostMapping("/{userId}")
        public ResponseEntity<PaymentMethod> addPaymentMethod(@PathVariable Long userId, @RequestBody PaymentMethod paymentMethod) {
            PaymentMethod newPaymentMethod = paymentMethodService.addPaymentMethod(userId, paymentMethod);
            return ResponseEntity.ok(newPaymentMethod);
        }


            @GetMapping("/{userId}")
            public ResponseEntity<List<PaymentMethod>> getUserPaymentMethods(@PathVariable Long userId) {
                List<PaymentMethod> paymentMethods = paymentMethodService.getUserPaymentMethods(userId);
                return ResponseEntity.ok(paymentMethods);
            }
    }