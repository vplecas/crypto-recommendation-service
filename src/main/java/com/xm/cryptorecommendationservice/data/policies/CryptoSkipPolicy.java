package com.xm.cryptorecommendationservice.data.policies;

import com.xm.cryptorecommendationservice.common.exception.BatchSkipException;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

import javax.annotation.Nonnull;

public class CryptoSkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(@Nonnull Throwable t, int i) throws SkipLimitExceededException {
        return t instanceof BatchSkipException;
    }

}
