package com.bootifulmicropizza.service.order;

import com.bootifulmicropizza.service.order.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test to verify JPA configuration.
 */
@DataJpaTest
@TestPropertySource("/test.properties")
@RunWith(SpringRunner.class)
public class OrderJpaTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void verifyJpaConfigurationForOrder() {
        final Order order = new Order("1", "123", Collections.EMPTY_SET);

        final Order savedOrder = testEntityManager.persistFlushFind(order);

        assertThat(savedOrder.getId(), equalTo(1L));
    }
}
