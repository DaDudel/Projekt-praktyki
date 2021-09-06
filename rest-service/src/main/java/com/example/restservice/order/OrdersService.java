package com.example.restservice.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository){
        this.ordersRepository = ordersRepository;
    }

    public List<Orders> getOrders(){
        return ordersRepository.findAll();
    }

    public void addNewOrders(Orders orders){
        Optional<Orders> orderOptional = ordersRepository
                .findOrderByTransId(orders.getTransId());

        if(orderOptional.isPresent()){
            throw new IllegalStateException("transId taken");
        }
        ordersRepository.save(orders);
    }

    public void deleteOrders(Integer orderId){
        boolean exists = ordersRepository.existsById(orderId);

        if(!exists){
            throw new IllegalStateException("order with id "+ orderId+ " does not exist");
        }
        ordersRepository.deleteById(orderId);
    }

    @Transactional
    public void updateOrders(Integer orderId, Integer transId, String client, Double bruttoPrice, Double nettoPrice, Double discount, String items){
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(()->new IllegalStateException("order with id "+ orderId+ " does not exist"));

        if(transId!= null &&
        !Objects.equals(orders.getTransId(),transId)){
            orders.setTransId(transId);
        }

        if(client != null &&
        client.length() > 0 &&
        !Objects.equals(orders.getClient(),client)){
            orders.setClient(client);
        }

        if(bruttoPrice!= null &&
                !Objects.equals(orders.getBruttoPrice(),bruttoPrice)){
            orders.setBruttoPrice(bruttoPrice);
        }

        if(nettoPrice!= null &&
                !Objects.equals(orders.getNettoPrice(),nettoPrice)){
            orders.setNettoPrice(nettoPrice);
        }

        if(discount!= null &&
                !Objects.equals(orders.getDiscount(),discount)){
            orders.setDiscount(discount);
        }

        if(items != null &&
                items.length() > 0 &&
                !Objects.equals(orders.getItems(),items)){
            orders.setItems(items);
        }
    }
}
