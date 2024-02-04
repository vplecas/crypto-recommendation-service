package com.xm.cryptorecommendationservice.common.repository;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {

    @Query(value = "SELECT * FROM crypto c WHERE c.symbol = :symbol ORDER BY c.timestamp ASC LIMIT 1",
            nativeQuery = true)
    Optional<Crypto> findOldest(String symbol);

    @Query(value = "SELECT * FROM crypto c WHERE c.symbol = :symbol ORDER BY c.timestamp DESC LIMIT 1",
            nativeQuery = true)
    Optional<Crypto> findNewest(String symbol);

    @Query(value = "SELECT * FROM crypto c WHERE c.symbol = :symbol ORDER BY c.price ASC LIMIT 1",
            nativeQuery = true)
    Optional<Crypto> findMin(String symbol);

    @Query(value = "SELECT * FROM crypto c WHERE c.symbol = :symbol ORDER BY c.price DESC LIMIT 1",
            nativeQuery = true)
    Optional<Crypto> findMax(String symbol);

}
