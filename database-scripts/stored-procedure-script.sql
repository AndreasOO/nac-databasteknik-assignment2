DROP PROCEDURE IF EXISTS `shop_db`.`addToCart`;
delimiter $
CREATE PROCEDURE `shop_db`.`addToCart`(IN `in_customer_id` INT, IN `in_order_id` INT, IN `in_shop_item_id` INT)
MODIFIES SQL DATA
BEGIN
	DECLARE `customerActiveOrderId` INT;
    
    DECLARE EXIT HANDLER FOR 1062
		BEGIN 
            ROLLBACK;
            RESIGNAL SET MESSAGE_TEXT = 'A completed order with that id aldready exists';
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
		-- check if an active order exists för the customer
        IF EXISTS (SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= `in_customer_id` AND `shop_db`.`orders`.`order_active` = 1) THEN
            
            -- set variable to active order id
            SELECT `shop_db`.`orders`.`id` INTO `customerActiveOrderId` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= `in_customer_id` AND `shop_db`.`orders`.`order_active` = 1;
            
            IF (`in_order_id` IS NULL) THEN 
            -- Active order exists and input order is null -> add input shop item to active order
            INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
			
            
			ELSEIF (`in_order_id` IS NOT NULL) THEN 
            -- Active order exists and input order is specified -> add input shop item to active order with corresponding input order id -> if id mismatch throw error 1452
            INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `in_order_id`);
			END IF;
            
		
        ELSE
			
			IF (`in_order_id` IS NULL) THEN 
            -- Active order does not exist and input order is not specified -> create new active order and auto increment id -> get last_insert_id -> add shop item to active order
				INSERT INTO `shop_db`.`orders` (`customer_id`, `order_active`) VALUES (`in_customer_id`, 1);
				SELECT LAST_INSERT_ID() INTO `customerActiveOrderId`;
				INSERT INTO `shop_db`.`order_items` (`shop_item_id`, `order_id`) VALUES (`in_shop_item_id`, `customerActiveOrderId`);
		
        
			ELSEIF (`in_order_id` IS NOT NULL) THEN 
            -- Active order does not exist and input order is specified -> create new active order with input order id -> add shop item to active order -> throws error 1062 if completed order with same order id exists already
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

 UPDATE `shop_db`.`shop_items` SET `quantity`= 2 WHERE `shop_db`.`shop_items`.`id` = 1;

-- DEMO START -- 
-- Show that no active order exists for customer with id = 1
SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 1 AND `shop_db`.`orders`.`order_active` = 1; 

-- Show that shop item with id 1 has quantity = 2
SELECT `shop_items`.`quantity` FROM `shop_db`.`shop_items` WHERE `shop_db`.`shop_items`.`id` = 1;

-- Show that out of stock table is empty
SELECT * FROM `shop_db`.`out_of_stock`; 

-- Add shop item with id 1 to customer with id 1, order id is null and no active order -> scenario 1 (first if clause)
call addToCart(1,null,1);

-- Show that out of stock table is still empty
SELECT * FROM `shop_db`.`out_of_stock`; 

-- Show that active order now exists for customer with id = 1
SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 1 AND `shop_db`.`orders`.`order_active` = 1; 

-- Show that item with id 1 has been added to active order
SELECT * FROM `shop_db`.`order_items` WHERE `shop_db`.`order_items`.`order_id` = (SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 1 AND `shop_db`.`orders`.`order_active` = 1);

-- Add shop item with id 1 to customer with id 1, order id is null and active order exists -> scenario 3 (third if clause)
call addToCart(1,null,1);

-- Show that another item with id 1 has been added to active order
SELECT * FROM `shop_db`.`order_items` WHERE `shop_db`.`order_items`.`order_id` = (SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 1 AND `shop_db`.`orders`.`order_active` = 1);

-- Show that out of stock table now has shop item id = 1 added 
SELECT * FROM `shop_db`.`out_of_stock`;

-- Show that trying to add out of stock shop item will generate error with resignal message
call addToCart(1,null,1);

-- Add shop item with id 2 to customer with id 1, order id is the active order id and active order exists -> scenario 2 (second if clause)
call addToCart(1,(SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 1 AND `shop_db`.`orders`.`order_active` = 1),2);

-- Show that another item with id 2 has been added to active order
SELECT * FROM `shop_db`.`order_items` WHERE `shop_db`.`order_items`.`order_id` = (SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 1 AND `shop_db`.`orders`.`order_active` = 1);

-- Show that no active order exists for customer with id = 2
SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 2 AND `shop_db`.`orders`.`order_active` = 1; 

-- Add shop item with id 1 to customer with id 2, order id is 500 and no active order -> scenario 4 (fourth if clause)
call addToCart(2,500,2);

-- Show that active order now exists for customer with id = 2
SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 2 AND `shop_db`.`orders`.`order_active` = 1; 

-- Show that item with id 1 has been added to active order
SELECT * FROM `shop_db`.`order_items` WHERE `shop_db`.`order_items`.`order_id` = (SELECT `shop_db`.`orders`.`id` FROM `shop_db`.`orders` WHERE `shop_db`.`orders`.`customer_id`= 2 AND `shop_db`.`orders`.`order_active` = 1);