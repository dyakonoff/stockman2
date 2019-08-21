package com.company.stockman2.web.screens.product;

import com.company.stockman2.service.StockChangingService;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
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
    private Dialogs dialogs;
    @Inject
    private Action replenishStock;
    @Inject
    private Action deductStock;
    @Inject
    private StockChangingService stockChangingService;
    @Inject
    private CollectionContainer<Product> productsDc;
    @Inject
    private CollectionLoader<Product> productsDl;

    public void onReplenishStockClick() {
        dialogs.createInputDialog(this)
                .withCaption("Replenish Stock")
                .withParameters(InputParameter.intParameter("quantity")
                    .withRequired(true)
                    .withDefaultValue(0))
                .withActions(DialogActions.OK_CANCEL, result -> {
                    Product product = productsDc.getItemOrNull(); // get selected item
                    if (product != null && result.getCloseActionType().equals(InputDialog.InputDialogResult.ActionType.OK)) {
                        Integer quantity = result.getValue("quantity");
                        assert quantity != null;
                        stockChangingService.changeStock(product.getId(), quantity);
                        productsDl.load();
                    }
                })
                .show();
    }

    public void onDeductStockClick() {
        dialogs.createInputDialog(this)
                .withCaption("Deduct from Stock")
                .withParameters(InputParameter.intParameter("quantity")
                        .withRequired(true)
                        .withDefaultValue(0))
                .withActions(DialogActions.OK_CANCEL, result -> {
                    Product product = productsDc.getItemOrNull(); // get selected item
                    if (product != null && result.getCloseActionType().equals(InputDialog.InputDialogResult.ActionType.OK)) {
                        Integer quantity = result.getValue("quantity");
                        assert quantity != null;
                        stockChangingService.changeStock(product.getId(), -quantity);
                        productsDl.load();
                    }
                })
                .show();
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