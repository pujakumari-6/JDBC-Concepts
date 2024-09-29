DELIMITER $$

-- Procedure to get employees for a specific department
CREATE DEFINER=`student`@`localhost` PROCEDURE get_employees_for_department(IN the_department VARCHAR(255))
BEGIN
    SELECT * FROM employees WHERE department = the_department;
END //

-- Procedure to greet a specific department
CREATE PROCEDURE greet_the_department(
    INOUT dept_name VARCHAR(50)
)
BEGIN
    SET dept_name = CONCAT('Hello, ', dept_name, ' department!');
END$$


-- Procedure to increase salaries for a specific department
CREATE DEFINER=`student`@`localhost` PROCEDURE increase_salaries_for_department(
    IN the_department VARCHAR(255),
    IN increase_amount DECIMAL(10, 2)
)
BEGIN
    UPDATE employees
    SET salary = salary + increase_amount
    WHERE department = the_department;
END //

-- Count the number of employees in the specified department
CREATE PROCEDURE get_count_for_department(
    IN the_department VARCHAR(64), 
    OUT the_count INT
)
BEGIN
    SELECT COUNT(*) INTO the_count
    FROM employees
    WHERE department = the_department;
END$$

-- Select all employees from the specified department
CREATE PROCEDURE get_employees_for_department(
    IN the_department VARCHAR(64)
)
BEGIN
    SELECT * 
    FROM employees 
    WHERE department = the_department;
END$$

DELIMITER ;

