DROP TABLE IF EXISTS `pharmacy`;

CREATE TABLE `pharmacy`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `created_date`     datetime(6) DEFAULT NULL,
    `modified_date`    datetime(6) DEFAULT NULL,
    `latitude`         double NOT NULL,
    `longitude`        double NOT NULL,
    `pharmacy_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `pharmacy_name`    varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
