
DELIMITER $
CREATE TRIGGER `after_shop_items_insert` AFTER UPDATE ON `shop_db`.`shop_items` FOR EACH ROW
	BEGIN
		IF (OLD.`quantity` = 1 AND NEW.`quantity` = 0) THEN
			INSERT INTO `shop_db`.`out_of_stock` (`shop_item_id`, `report_date`) VALUES (OLD.`id`, CURRENT_DATE());
        END IF;
    END$
	
DELIMITER ;
