package com.bootifulmicropizza.service.order.rest;

import com.bootifulmicropizza.service.order.TestUtils;
import com.bootifulmicropizza.service.order.domain.Order;
import com.bootifulmicropizza.service.order.domain.OrderLine;
import com.bootifulmicropizza.service.order.domain.OrderStatus;
import com.bootifulmicropizza.service.order.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class to test the {@link OrderRestController} with security disabled.
 */
@WebMvcTest(controllers = OrderRestController.class, secure = false)
@Import(TestUtils.class)
@AutoConfigureDataJpa
@TestPropertySource("/test.properties")
@RunWith(SpringRunner.class)
public class OrderRestControllerTest {

    @Autowired
    private TestUtils testUtils;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    private Order order;

    @Before
    public void setUp() {
        order = new Order();
        order.setId(1L);
        order.setOrderId("74673673453");
        order.setAccountNumber("123456");
        order.setStatus(OrderStatus.RECEIVED);

        final Set<OrderLine> orderLines = new HashSet<>();
        orderLines.add(new OrderLine("763456345", 1, new BigDecimal(4.99)));
        orderLines.add(new OrderLine("632534676", 2, new BigDecimal(2.99)));
        order.setOrderLines(orderLines);

        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        when(orderRepository.findOne(1L)).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void getAllOrders() throws Exception {
        mockMvc.perform(get("/orders/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("@.[0].id").value(1L))
               .andExpect(jsonPath("@.[0].orderId").value("74673673453"))
               .andExpect(jsonPath("@.[0].accountNumber").value("123456"))
               .andExpect(jsonPath("@.[0].orderLines.[*].productId", containsInAnyOrder("763456345", "632534676")))
               .andExpect(jsonPath("@.[0].orderLines.[*].quantity", containsInAnyOrder(1, 2)))
               .andExpect(jsonPath("@.[0].orderLines.[*].unitPrice", containsInAnyOrder(4.99, 2.99)));

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void getSingleOrder() throws Exception {
        mockMvc.perform(get("/orders/1/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("id").value(1L))
               .andExpect(jsonPath("@.orderId").value("74673673453"))
               .andExpect(jsonPath("@.accountNumber").value("123456"))
               .andExpect(jsonPath("@.orderLines.[*].productId", containsInAnyOrder("763456345", "632534676")))
               .andExpect(jsonPath("@.orderLines.[*].quantity", containsInAnyOrder(1, 2)))
               .andExpect(jsonPath("@.orderLines.[*].unitPrice", containsInAnyOrder(4.99, 2.99)));

        verify(orderRepository, times(1)).findOne(1L);
    }

    @Test
    public void getSingleCustomerThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/orders/2/"))
               .andExpect(status().isNotFound());

        verify(orderRepository, times(1)).findOne(2L);
    }

    @Test
    public void saveSingleOrder() throws Exception {
        final Order order = new Order();

        mockMvc.perform(
            post("/orders/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(testUtils.asJsonString(order)))
               .andExpect(status().isCreated())
               .andExpect(MockMvcResultMatchers.header().string("LOCATION", "/orders/1/"))
               .andExpect(jsonPath("id").value(1L))
               .andExpect(jsonPath("orderId").value("74673673453"))
               .andExpect(jsonPath("accountNumber").value("123456"))
               .andExpect(jsonPath("orderLines.[*].productId", containsInAnyOrder("763456345", "632534676")))
               .andExpect(jsonPath("orderLines.[*].quantity", containsInAnyOrder(1, 2)))
               .andExpect(jsonPath("orderLines.[*].unitPrice", containsInAnyOrder(4.99, 2.99)));

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void updateSingleOrder() throws Exception {
        when(orderRepository.exists(1L)).thenReturn(true);

        mockMvc.perform(
            put("/orders/1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(testUtils.asJsonString(order)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("id").value(1L))
               .andExpect(jsonPath("orderId").value("74673673453"))
               .andExpect(jsonPath("accountNumber").value("123456"))
               .andExpect(jsonPath("orderLines.[*].productId", containsInAnyOrder("763456345", "632534676")))
               .andExpect(jsonPath("orderLines.[*].quantity", containsInAnyOrder(1, 2)))
               .andExpect(jsonPath("orderLines.[*].unitPrice", containsInAnyOrder(4.99, 2.99)));

        verify(orderRepository, times(1)).exists(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void updateSingleOrderThatDoesNotExist() throws Exception {
        when(orderRepository.exists(1L)).thenReturn(false);

        final Order order = new Order();

        mockMvc.perform(
            put("/orders/1/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(testUtils.asJsonString(order)))
               .andExpect(status().isNotFound());

        verify(orderRepository, times(1)).exists(1L);
    }

    @Test
    public void deleteSingleOrder() throws Exception {
        mockMvc.perform(delete("/orders/1/"))
               .andExpect(status().isNoContent());

        verify(orderRepository, times(1)).delete(1L);
    }
}
