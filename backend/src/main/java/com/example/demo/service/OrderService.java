package com.example.demo.service;

import com.example.demo.dto.OrderDetailDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.OrderItemDetailDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.OrderDetial;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;


    public OrderDetial createOrder(OrderDetial orderDetial) {
        if (orderDetial.getOrderItems() != null) {
            for (OrderItem item : orderDetial.getOrderItems()) {
                item.setOrder(orderDetial);
            }
        }
        return orderRepository.save(orderDetial);
    }
    public List<OrderItemDTO> getOrderItemsBySellerId(Long sellerId) {
        return orderRepository.findOrderItemsBySellerId(sellerId);
    }
    public OrderDetial findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    public List<OrderDetial> getAllOrders() {
        return orderRepository.findAll();
    }


    public OrderDetial getOrderById(Long id) {
        Optional<OrderDetial> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }
    public List<OrderDetial> getOrdersByUserId(Long userId) {
        return orderRepository.findByCustomerName(userId);
    }
    public OrderItemDetailDTO getOrderItemDetail(Long orderItemId) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found"));

        OrderDetial orderDetial = orderItem.getOrder();

        User customer = userRepository.findById(orderDetial.getCustomerName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        return new OrderItemDetailDTO(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderDetial.getOrderDate(),
                customer.getUsername(),
                customer.getId()
        );
    }
    public OrderDetial updateOrder(Long id, OrderDetial updatedOrderDetial) {
        Optional<OrderDetial> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            OrderDetial existingOrder = orderOptional.get();

            existingOrder.setCustomerName(updatedOrderDetial.getCustomerName());
            existingOrder.setOrderDate(updatedOrderDetial.getOrderDate());
            existingOrder.setTotalAmount(updatedOrderDetial.getTotalAmount());
            existingOrder.setStatus(updatedOrderDetial.getStatus());
            existingOrder.setShippingAddress(updatedOrderDetial.getShippingAddress());
            existingOrder.setCity(updatedOrderDetial.getCity());
            existingOrder.setPostalCode(updatedOrderDetial.getPostalCode());
            existingOrder.setCountry(updatedOrderDetial.getCountry());
            existingOrder.setPaymentMethod(updatedOrderDetial.getPaymentMethod());
            existingOrder.setOrderItems(updatedOrderDetial.getOrderItems());
            return orderRepository.save(existingOrder);
        } else {
            return null;
        }
    }


    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
