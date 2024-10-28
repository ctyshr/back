package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.PaymentMethod;
import com.example.demo.model.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.PaymentMethodRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public PaymentMethod addPaymentMethod(Long userId, PaymentMethod paymentMethod) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        paymentMethod.setType(paymentMethod.getType());
        paymentMethod.setCardNumber(paymentMethod.getCardNumber());
        paymentMethod.setExpiration(paymentMethod.getExpiration());
        paymentMethod.setDefault(paymentMethod.isDefault());



        paymentMethod.setUser(user);
        return paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethod> getUserPaymentMethods(Long userId) {
        return paymentMethodRepository.findByUserId(userId);
    }
}