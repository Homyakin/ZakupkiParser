SET SQL_SAFE_UPDATES = 0;
DELETE FROM zakupki.long_term_volume_detail;
DELETE FROM zakupki.long_term_volumes;
DELETE FROM zakupki.purchase_plan_item_row;
DELETE FROM zakupki.innovation_plan_item_row;
DELETE FROM zakupki.purchase_plan_item;
DELETE FROM zakupki.innovation_plan_item;
DELETE FROM zakupki.plan_item;
DELETE FROM zakupki.purchase_plan;
DELETE FROM zakupki.customer;
SET SQL_SAFE_UPDATES = 1;