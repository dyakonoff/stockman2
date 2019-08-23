package com.company.stockman2.web.screens.product;

import com.company.stockman2.entity.Product;
import com.company.stockman2.service.StockChangingService;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

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

    private final static String QTY_PARAM_NAME = "quantity";

    public void onReplenishStockClick() {
        dialogs.createInputDialog(this)
                .withCaption("Replenish Stock")
                .withParameter(InputParameter.intParameter(QTY_PARAM_NAME)
                    .withRequired(true)
                    .withDefaultValue(0))
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    Product product = productsDc.getItemOrNull(); // get selected item
                    if (product != null && closeEvent.getCloseAction() == InputDialog.INPUT_DIALOG_OK_ACTION) {
                        Integer qtyChange = closeEvent.getValue(QTY_PARAM_NAME);
                        stockChangingService.changeStock(product.getId(), qtyChange);
                        productsDl.load();
                    }
                })
                .withValidator(context -> {
                    Integer qty = context.getValue(QTY_PARAM_NAME);
                    if (qty != null && qty <= 0) {
                        return ValidationErrors.of("Replenish quantity should be positive");
                    }
                    return ValidationErrors.none();
                })
                .show();
    }

    public void onDeductStockClick() {
        dialogs.createInputDialog(this)
                .withCaption("Deduct from Stock")
                .withParameter(InputParameter.intParameter(QTY_PARAM_NAME)
                        .withRequired(true)
                        .withDefaultValue(0))
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    Product product = productsDc.getItemOrNull(); // get selected item
                    if (product != null && closeEvent.getCloseAction() == InputDialog.INPUT_DIALOG_OK_ACTION) {
                        Integer qtyChange = closeEvent.getValue(QTY_PARAM_NAME);
                        assert qtyChange != null;
                        stockChangingService.changeStock(product.getId(), -qtyChange);
                        productsDl.load();
                    }
                })
                .withValidator(context -> {
                    Integer qty = context.getValue(QTY_PARAM_NAME);
                    if (qty != null && qty <= 0) {
                        return ValidationErrors.of("Deduct quantity should be positive");
                    }
                    return ValidationErrors.none();
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
        btnDeduct.setEnabled(entity.getStockItem().getQuantity() > 0);
        return btnDeduct;
    }
}