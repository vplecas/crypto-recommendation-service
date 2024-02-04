package com.xm.cryptorecommendationservice.repository;

import com.xm.cryptorecommendationservice.domain.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {
}
