package com.company.stockman2.web.screens.product;

import com.haulmont.cuba.gui.screen.*;
import com.company.stockman2.entity.Product;


@UiController("stockman2_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
@LoadDataBeforeShow
public class ProductEdit extends StandardEditor<Product> {
}