package com.xm.cryptorecommendationservice.data.writer;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import com.xm.cryptorecommendationservice.common.repository.CryptoRepository;
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
