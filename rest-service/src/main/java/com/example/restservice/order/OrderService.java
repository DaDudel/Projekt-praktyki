package com.example.restservice.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService (OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrder(){
        return orderRepository.findAll();
    }

    public void addNewOrder(Order order){
        Optional<Order> orderOptional = orderRepository
                .findOrderByTransId(order.getTransId());

        if(orderOptional.isPresent()){
            throw new IllegalStateException("transId taken");
        }
        orderRepository.save(order);
    }

    public void deleteOrder(Integer orderId){
        boolean exists = orderRepository.existsById(orderId);

        if(!exists){
            throw new IllegalStateException("order with id "+ orderId+ " does not exist");
        }
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public void updateOrder(Integer orderId, Integer transId, String client, Double bruttoPrice, Double nettoPrice, Double discount, String items){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new IllegalStateException("order with id "+ orderId+ " does not exist"));

        if(transId!= null &&
        !Objects.equals(order.getTransId(),transId)){
            order.setTransId(transId);
        }

        if(client != null &&
        client.length() > 0 &&
        !Objects.equals(order.getClient(),client)){
            order.setClient(client);
        }

        if(bruttoPrice!= null &&
                !Objects.equals(order.getBruttoPrice(),bruttoPrice)){
            order.setBruttoPrice(bruttoPrice);
        }

        if(nettoPrice!= null &&
                !Objects.equals(order.getNettoPrice(),nettoPrice)){
            order.setNettoPrice(nettoPrice);
        }

        if(discount!= null &&
                !Objects.equals(order.getDiscount(),discount)){
            order.setDiscount(discount);
        }

        if(items != null &&
                items.length() > 0 &&
                !Objects.equals(order.getItems(),items)){
            order.setItems(items);
        }
    }
}
