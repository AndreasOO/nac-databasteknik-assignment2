delimiter $
CREATE PROCEDURE `addToCart`(IN `in_customer_id` INT, IN `in_order_id` INT, IN `in_shop_item_id` INT)
BEGIN
	DECLARE `customerActiveOrderId` INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN 
			ROLLBACK;
            -- RESIGNAL SET MESSAGE_TEXT = 'Could not add shop item - out of stock';
        END;
	
    SET AUTOCOMMIT = 0;
    START TRANSACTION;
		SELECT `orders.id` INTO `customerActiveOrderId` FROM `orders` WHERE `orders.customer_id`= `in_customer_id` AND `order_active` = 1;
        
		IF (`in_order_id` IS NULL AND `customerActiveOrderId` IS NULL) THEN 
			INSERT INTO `orders` (`customer_id`, `order_active`) VALUES (`in_customer_id`, 1);
            SELECT LAST_INSERT_ID() INTO `customerActiveOrderId`;
            INSERT INTO `order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
		
        ELSEIF (`in_order_id` IS NULL AND `customerActiveOrderId` IS NOT NULL) THEN 
            INSERT INTO `order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
        
        ELSEIF (`in_order_id` IS NOT NULL AND `customerActiveOrderId` IS NULL) THEN 
			INSERT INTO `orders` (`id`,`customer_id`, `order_active`) VALUES (`in_order_id`,`in_customer_id`, 1);
            INSERT INTO `order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `in_order_id`);
            
		
        ELSEIF (`in_order_id` IS NOT NULL AND `customerActiveOrderId` IS NOT NULL) THEN 
            INSERT INTO `order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
        
        
        END IF;
     COMMIT;   
			-- DONT FORGET TO DECREMENT QUANTITY
END$
delimiter ;

call addToCart(1,null,1);