package com.xm.cryptorecommendationservice.config;

import com.xm.cryptorecommendationservice.domain.Crypto;
import com.xm.cryptorecommendationservice.repository.CryptoRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.List;

//@RequiredArgsConstructor
public class CryptoWriter<T> implements ItemWriter<Crypto> {

    @Autowired
    private CryptoRepository cryptoRepository;

    @Override
    public void write(@Nonnull List<? extends Crypto> data) throws Exception {
        System.out.println("WRITEEEEE");
        cryptoRepository.saveAll(data);
    }
}
