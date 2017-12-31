package com.bootifulmicropizza.service.order.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Order entity.
 */
@Entity(name = "orders")
public class Order extends BaseEntity implements Serializable {

    private Long id;

    private String orderId;

    private String accountNumber;

    private Set<OrderLine> orderLines = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {

    }

    public Order(final String orderId, final String accountNumber, final Set<OrderLine> orderLines) {
        this.orderId = orderId;
        this.accountNumber = accountNumber;
        this.orderLines = orderLines;
        this.status = OrderStatus.RECEIVED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
