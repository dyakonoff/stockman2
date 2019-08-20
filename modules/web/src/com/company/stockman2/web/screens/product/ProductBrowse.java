package com.company.stockman2.web.screens.product;

import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import com.company.stockman2.entity.Product;

import javax.inject.Inject;

@UiController("stockman2_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
@LoadDataBeforeShow
public class ProductBrowse extends StandardLookup<Product> {

    @Inject
    private UiComponents uiComponents;
    @Inject
    private Action replenishStock;
    @Inject
    private Action deductStock;



    public void onReplenishStockClick() {
    }

    public void onDeductStockClick() {
    }

    public Component generateAvailableCell(Product entity) {
        return new Table.PlainTextCell(entity.getStockItem().getQuantity().toString());
    }

    public Component generateReplenishCell(Product entity) {
        Button btnAdd = uiComponents.create(Button.class);
        btnAdd.setAction(replenishStock);
        btnAdd.setStyleName("icon-only");
        return btnAdd;
    }

    public Component generateDeductCell(Product entity) {
        Button btnDeduct = uiComponents.create(Button.class);
        btnDeduct.setAction(deductStock);
        btnDeduct.setStyleName("icon-only");
        return btnDeduct;
    }
}