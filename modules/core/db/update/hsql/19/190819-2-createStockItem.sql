alter table STOCKMAN2_STOCK_ITEM add constraint FK_STOCKMAN2_STOCK_ITEM_ON_PRODUCT foreign key (PRODUCT_ID) references STOCKMAN2_PRODUCT(ID);
create index IDX_STOCKMAN2_STOCK_ITEM_ON_PRODUCT on STOCKMAN2_STOCK_ITEM (PRODUCT_ID);
