package com.xm.cryptorecommendationservice.config;

import com.xm.cryptorecommendationservice.exception.BatchSkipException;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import javax.annotation.Nonnull;

public class CryptoSkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(@Nonnull Throwable t, int i) throws SkipLimitExceededException {
        return t instanceof BatchSkipException;
    }

}
