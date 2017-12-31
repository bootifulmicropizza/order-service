package com.bootifulmicropizza.service.order.repository;

import com.bootifulmicropizza.service.order.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Spring Data repository for orders.
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

}
