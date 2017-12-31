CREATE TABLE `orders` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` bigint(20) DEFAULT NULL,
    `last_modified` bigint(20) DEFAULT NULL,
    `account_number` varchar(255) DEFAULT NULL,
    `order_id` varchar(255) DEFAULT NULL,
    `status` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_line` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_at` bigint(20) DEFAULT NULL,
    `last_modified` bigint(20) DEFAULT NULL,
    `product_id` varchar(255) DEFAULT NULL,
    `quantity` int(11) NOT NULL,
    `unit_price` decimal(19,2) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `orders_order_lines` (
    `orders_id` bigint(20) NOT NULL,
    `order_lines_id` bigint(20) NOT NULL,
    PRIMARY KEY (`orders_id`,`order_lines_id`),
    UNIQUE KEY `UK_rk4wg0al69u98bj5qb38ve559` (`order_lines_id`),
    CONSTRAINT `FK4jurtsjio2c3i6bj99ga3kjuc` FOREIGN KEY (`order_lines_id`) REFERENCES `order_line` (`id`),
    CONSTRAINT `FKlaecm1pdvqbmsdo18a0kusqg4` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
