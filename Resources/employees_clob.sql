CREATE TABLE `employees_clob` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `last_name` varchar(64) DEFAULT NULL,
    `first_name` varchar(64) DEFAULT NULL,
    `email` varchar(64) DEFAULT NULL,
    `department` varchar(64) DEFAULT NULL,
    `salary` DECIMAL(10,2) DEFAULT NULL,
    `resume` LONGTEXT,
    PRIMARY KEY (`id`)
);
INSERT INTO `employees_clob` (`id`,`last_name`,`first_name`,`email`, `department`, `salary`) VALUES (1,'Doe','John','john.doe@foo.com', 'HR', 55000.00);
INSERT INTO `employees_clob` (`id`,`last_name`,`first_name`,`email`, `department`, `salary`) VALUES (2,'Public','Mary','mary.public@foo.com', 'Engineering', 75000.00);
