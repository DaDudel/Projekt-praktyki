package com.example.restservice.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "API/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public List<Orders> getOrders(){
        return ordersService.getOrders();
    }

    @PostMapping
    public void registerNewOrders(@RequestBody Orders orders){
        ordersService.addNewOrders(orders);
    }

    @DeleteMapping(path = "{ordersId}")
    public void deleteOrders(@PathVariable("ordersId")Integer ordersId){
        ordersService.deleteOrders(ordersId);
    }

    @PutMapping(path = "{ordersId}")
    public void updateOrders(
            @PathVariable("ordersId") Integer ordersId,
            @RequestParam(required = false) Integer transId,
            @RequestParam(required = false) String client,
            @RequestParam(required = false) Double bruttoPrice,
            @RequestParam(required = false) Double nettoPrice,
            @RequestParam(required = false) Double discount,
            @RequestParam(required = false) String items){
        ordersService.updateOrders(ordersId,transId,client,bruttoPrice,nettoPrice,discount,items);
    }
}
