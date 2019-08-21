package com.company.stockman2.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface StockChangingService {
    String NAME = "stockman2_StockChangingService";

    @Validated
    void changeStock(@NotNull UUID productId, int qtyChange);
}