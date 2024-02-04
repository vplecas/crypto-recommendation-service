package com.xm.cryptorecommendationservice.common.repository;

import com.xm.cryptorecommendationservice.common.domain.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {
}
