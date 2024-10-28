package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping("/{userId}")
    public ResponseEntity<Address> addAddress(@PathVariable Long userId, @RequestBody Address address) {

        Address newAddress = addressService.addAddress(userId, address);
        System.out.println("1"+ResponseEntity.ok(newAddress));
        return ResponseEntity.ok(newAddress);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long userId) {
        List<Address> addresses = addressService.getUserAddresses(userId);
        return ResponseEntity.ok(addresses);
    }
}