package com.company.stockman2.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "STOCKMAN2_STOCK_ITEM")
@Entity(name = "stockman2_StockItem")
public class StockItem extends StandardEntity {
    private static final long serialVersionUID = -8611058504881410586L;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    protected Integer quantity;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @PostConstruct
    public void postConstruct() {
        quantity = 0;
    }
}