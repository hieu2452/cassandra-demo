package com.griddynamics.order.service.impl;

import static com.griddynamics.order.kafka.KeySet.DELETE;
import static com.griddynamics.order.kafka.KeySet.SAVE;
import static com.griddynamics.order.kafka.KeySet.UPDATE;

import com.griddynamics.order.entity.OrderInfo;
import com.griddynamics.order.entity.OrderItem;
import com.griddynamics.order.entity.Product;
import com.griddynamics.order.mapper.JsonMapperWrapper;
import com.griddynamics.order.repository.OrderRepository;
import com.griddynamics.order.service.KafkaOrderService;
import com.griddynamics.order.service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final KafkaOrderService kafkaOrderService;
    private final JsonMapperWrapper jsonMapper;

    private final String topic;

    private final RestTemplate restTemplate;

    private final String url;

    @Autowired
    public OrderServiceImpl(RestTemplate restTemplate,
                            @Value("${service.protocol.product}") String protocol,
                            @Value("${service.domain.product}") String domain,
                            @Value("${service.port.product}") String port,
                            @Value("${service.path.product}") String path,
                            OrderRepository orderRepository, KafkaOrderService kafkaOrderService,
                            JsonMapperWrapper jsonMapper,
                            @Value("${spring.kafka.consumer.topic.order}") String topic) {
        this.orderRepository = orderRepository;
        this.kafkaOrderService = kafkaOrderService;
        this.jsonMapper = jsonMapper;
        this.topic = topic;
        this.restTemplate = restTemplate;
        this.url = protocol + domain + ":" + port + path;
    }

    @Override
    public OrderInfo save(OrderInfo orderInfo) {
        LOGGER.info("Save order: {}", orderInfo);
        for(OrderItem orderItem: orderInfo.getItems()) {
            ResponseEntity<Product> response = restTemplate.getForEntity(url + orderItem.getProductId(), Product.class);
            Product product = response.getBody();
            orderItem.setPrice(product.getPrice());
        }
        BigDecimal total = BigDecimal.valueOf(0);
        for(OrderItem orderItem: orderInfo.getItems()) {
            BigDecimal price = BigDecimal.valueOf(orderItem.getAmount()).multiply(orderItem.getPrice());
            total = total.add(price);
        }
        orderInfo.setTotalPrice(total);
        OrderInfo saved = orderRepository.save(orderInfo);
        kafkaOrderService.send(topic, SAVE, jsonMapper.writeValue(saved));
        return saved;
    }

    @Override
    public OrderInfo update(UUID id, OrderInfo updated) {
        LOGGER.info("Update order, id: {}, {}", id, updated);
        OrderInfo orderInfo = orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order not found, id" + id));
        orderInfo.setStatus(updated.getStatus());
        orderInfo.setTotalPrice(updated.getTotalPrice());
        orderInfo.setAddress(updated.getAddress());
        orderInfo.setItems(updated.getItems());
        OrderInfo saved = orderRepository.save(orderInfo);
        kafkaOrderService.send(topic, UPDATE, jsonMapper.writeValue(saved));
        return saved;
    }

    @Override
    public OrderInfo findOne(UUID id) {
        LOGGER.info("Find order, id: {}", id);
        return orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order not found, id" + id));
    }

    @Override
    public List<OrderInfo> findAll() {
        LOGGER.info("Find all orders");
        Iterable<OrderInfo> orders = orderRepository.findAll();
        return IteratorUtils.toList(orders.iterator());
    }

    @Override
    public void delete(UUID id) {
        LOGGER.info("Delete order, id: {}", id);
        orderRepository.deleteById(id);
        kafkaOrderService.send(topic, DELETE, id.toString());
    }

}
