DROP PROCEDURE IF EXISTS `shop_db`.`addToCart`;
delimiter $
CREATE PROCEDURE `shop_db`.`addToCart`(IN `in_customer_id` INT, IN `in_order_id` INT, IN `in_shop_item_id` INT)
MODIFIES SQL DATA
BEGIN
	DECLARE `customerActiveOrderId` INT;
    DECLARE EXIT HANDLER FOR 1062
		BEGIN 
            ROLLBACK;
            resignal set message_text = 'A completed order with that id aldready exists, rolling back';
        END;
        
    DECLARE EXIT HANDLER FOR 1264
		BEGIN 
            ROLLBACK;
            resignal set message_text = 'The shop item is out of stock';
        END;
        
    DECLARE EXIT HANDLER FOR 1690
		BEGIN 
            ROLLBACK;
            resignal set message_text = 'The shop item is out of stock';
        END;
        
    DECLARE EXIT HANDLER FOR 1452
		BEGIN 
            ROLLBACK;
            resignal set message_text = 'There is no active order what that id';
        END;
        
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN 
            ROLLBACK;
            resignal set message_text = 'Something unexpected happened, rolling back';
        END;
	
    SET AUTOCOMMIT = 0;
    START TRANSACTION;
        IF EXISTS (SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= `in_customer_id` AND `shop_db`.`orders`.`order_active` = 1) THEN
			
            
            SELECT `shop_db`.`orders`.`id` INTO `customerActiveOrderId` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= `in_customer_id` AND `shop_db`.`orders`.`order_active` = 1;
            IF (`in_order_id` IS NULL) THEN 
            -- Active order exists and input order is null -> add input shop item to active order
            INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
			
            
			ELSEIF (`in_order_id` IS NOT NULL) THEN 
            -- Active order exists and input order is specified -> add input shop item to active order with corresponding input order id -> if mismatch throw error 1452
            INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `in_order_id`);
			END IF;
            
		
        ELSE	
			IF (`in_order_id` IS NULL) THEN 
				INSERT INTO `shop_db`.`orders` (`customer_id`, `order_active`) VALUES (`in_customer_id`, 1);
				SELECT LAST_INSERT_ID() INTO `customerActiveOrderId`;
				INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
		
        
			ELSEIF (`in_order_id` IS NOT NULL) THEN 
            -- Active order does not exists and input order is specified -> create new active order with input order id -> add shop item to active order -> throws error 1062 if completed order with same order id exists already
            -- WARNING: WILL SET NEW AUTO_INCREMENT START VALUE STARTING FROM in_order_id VALUE
				INSERT INTO `shop_db`.`orders` (`id`,`customer_id`, `order_active`) VALUES (`in_order_id`,`in_customer_id`, 1);
				INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `in_order_id`);
                
            END IF;
        
        END IF;
        UPDATE `shop_db`.`shop_items` SET `quantity`= `quantity` -1 WHERE `shop_db`.`shop_items`.`id` = `in_shop_item_id`;
     COMMIT;   
			
END$
delimiter ;

SET AUTOCOMMIT = 1;

 UPDATE `shop_db`.`shop_items` SET `quantity`= 1 WHERE `shop_db`.`shop_items`.`id` = 1;

-- call addToCart(4,null,1);
-- SELECT * from order_items WHERE order_items.order_id = 28;
-- SELECT shop_db.`orders`.`id` FROM shop_db.`orders` WHERE shop_db.`orders`.`customer_id`= 1 AND shop_db.orders.`order_active` = 1;