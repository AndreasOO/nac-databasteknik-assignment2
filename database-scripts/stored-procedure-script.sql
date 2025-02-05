DROP PROCEDURE IF EXISTS `shop_db`.`addToCart`;
delimiter $
CREATE PROCEDURE `shop_db`.`addToCart`(IN `in_customer_id` INT, IN `in_order_id` INT, IN `in_shop_item_id` INT)
MODIFIES SQL DATA
BEGIN
	DECLARE `customerActiveOrderId` INT;
    DECLARE EXIT HANDLER FOR 1062
		BEGIN 
            ROLLBACK;
            resignal set message_text = 'An order with that id aldready exists, rolling back';
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
            select `customerActiveOrderId`;
            IF (`in_order_id` IS NULL) THEN 
            select '1:1 start';
            INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
            select '1:1 end';
			
            
			ELSEIF (`in_order_id` IS NOT NULL) THEN 
            select '1:2 start';
            INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
            select '1:2 end';
			END IF;
            
		
        ELSE	
			IF (`in_order_id` IS NULL) THEN 
				select '2:1 start';
				INSERT INTO `shop_db`.`orders` (`customer_id`, `order_active`) VALUES (`in_customer_id`, 1);
				SELECT LAST_INSERT_ID() INTO `customerActiveOrderId`;
                select `customerActiveOrderId`;
				INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
                select '2:1 end';
		
        
			ELSEIF (`in_order_id` IS NOT NULL) THEN 
				select '2:2 start';
				INSERT INTO `shop_db`.`orders` (`id`,`customer_id`, `order_active`) VALUES (`in_order_id`,`in_customer_id`, 1);
				INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `in_order_id`);
                select '2:2 end';
                
            END IF;
        
        END IF;
        -- DECREMENT QUANTITY BLOCK HERE
     COMMIT;   
			
END$
delimiter ;

SET AUTOCOMMIT = 1;

call addToCart(7,25,1);
SELECT * from order_items WHERE order_items.order_id = 28;
-- SELECT shop_db.`orders`.`id` FROM shop_db.`orders` WHERE shop_db.`orders`.`customer_id`= 1 AND shop_db.orders.`order_active` = 1;