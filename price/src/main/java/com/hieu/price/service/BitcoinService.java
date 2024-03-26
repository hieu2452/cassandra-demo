package com.hieu.price.service;

import com.hieu.price.model.BitcoinPrice;
import com.hieu.price.repository.BitcoinPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class BitcoinService {
    @Autowired
    private BitcoinPriceRepository bitcoinPriceRepository;
    @Scheduled(fixedRate = 3000)
    public void simulatePrice() {
        System.out.println("1");
        try {
            Random random = new Random();
            double price = 70000 + (10000 * random.nextDouble());
            BitcoinPrice bitcoinPrice = new BitcoinPrice(LocalDateTime.now(), price);
            bitcoinPriceRepository.save(bitcoinPrice);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
