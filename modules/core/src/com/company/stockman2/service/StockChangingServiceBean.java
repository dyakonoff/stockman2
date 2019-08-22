package com.company.stockman2.service;

import com.company.stockman2.entity.Product;
import com.company.stockman2.entity.StockItem;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Service(StockChangingService.NAME)
public class StockChangingServiceBean implements StockChangingService {
    @Inject
    private DataManager dataManager;

    @Override
    public void changeStock(@NotNull UUID productId, int qtyChange) {
        Product product = dataManager.load(Product.class)
                .view("product-with-stock")
                .id(productId)
                .one();
        StockItem stockItem = product.getStockItem();
        int newQty = stockItem.getQuantity() + qtyChange;
        stockItem.setQuantity(newQty);
        dataManager.commit(stockItem);
    }
}