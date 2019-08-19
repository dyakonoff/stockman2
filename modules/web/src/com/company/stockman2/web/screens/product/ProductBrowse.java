package com.company.stockman2.web.screens.product;

import com.haulmont.cuba.gui.screen.*;
import com.company.stockman2.entity.Product;

@UiController("stockman2_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
@LoadDataBeforeShow
public class ProductBrowse extends StandardLookup<Product> {
}