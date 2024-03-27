package com.griddynamics.product.service.impl;

import com.griddynamics.product.entity.Product;
import com.griddynamics.product.kafka.KeySet;
import com.griddynamics.product.mapper.JsonMapperWrapper;
import com.griddynamics.product.repository.ProductRepository;
import com.griddynamics.product.service.KafkaProducerService;
import com.griddynamics.product.service.ProductService;

import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final KafkaProducerService kafkaProducerService;
    private final JsonMapperWrapper jsonMapper;

    private final String topic;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              KafkaProducerService kafkaProducerService,
                              JsonMapperWrapper jsonMapper,
                              @Value("${spring.kafka.consumer.topic.product}") String topic) {
        this.productRepository = productRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.jsonMapper = jsonMapper;
        this.topic = topic;
    }

    @Override
    public Product save(Product product) {
        LOGGER.info("Save product: {}", product);
        Product saved = productRepository.save(product);
        kafkaProducerService.send(topic, KeySet.SAVE, jsonMapper.writeValue(saved));
        return saved;
    }

    @Override
    public Product update(UUID id, Product updated) {
        LOGGER.info("Update product, id: {}, {}", id, updated);
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found, id" + id));
        product.setDescription(updated.getDescription());
        product.setImageUrl(updated.getImageUrl());
        product.setPrice(updated.getPrice());
        Product saved = productRepository.save(product);
        kafkaProducerService.send(topic, KeySet.UPDATE, jsonMapper.writeValue(saved));
        return saved;
    }

    @Override
    public Product findOne(UUID id) {
        LOGGER.info("Find product, id: {}", id);
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found, id" + id));
    }

    @Override
    public List<Product> findAll() {
        LOGGER.info("Find all products");
        Iterable<Product> products = productRepository.findAll();
        return IteratorUtils.toList(products.iterator());
    }

    @Override
    public void delete(UUID id) {
        LOGGER.info("Delete product, id: {}", id);
        productRepository.deleteById(id);
        kafkaProducerService.send(topic, KeySet.DELETE, id.toString());
    }

}
