package com.bootifulmicropizza.service.order.rest;

import com.bootifulmicropizza.service.order.domain.Order;
import com.bootifulmicropizza.service.order.repository.OrderRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for the {code /orders/} endpoint.
 */
@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    private OrderRepository orderRepository;

    public OrderRestController(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getOrders() {
        final List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(order -> orders.add(order));

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Order> getOrder(@PathVariable("id") final Long id) {
        final Order order = orderRepository.findOne(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody final Order order) {
        final Order createdOrder = orderRepository.save(order);

        return ResponseEntity
            .created(URI.create("/orders/" + createdOrder.getId() + "/"))
            .body(createdOrder);
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Order> updateOrder(@RequestBody final Order order, @PathVariable("id") final Long id) {
        if (!orderRepository.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        final Order createdOrder = orderRepository.save(order);

        return ResponseEntity.ok(createdOrder);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Order> deleteOrder(@PathVariable final Long id) {
        orderRepository.delete(id);

        return ResponseEntity.noContent().build();
    }
}
