The files here are for the backend java side. Remember to download mysql-connector.jar file and put it inside the classpath when building Java Build path. My connector is "mysql-connector-j-8.3.0.jar", I used Eclipse IDE for this.

For MySQL, first create employee_data schema, then create employee_details table:
<pre>
CREATE TABLE `employee_details`.`employees` (
  `employee_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(20),
  PRIMARY KEY (`employee_id`)
);
</pre>

Then insert data into employee_details table:
<pre>
  INSERT INTO `employee_data`.`employees` (`name`, `phone_number`)
VALUES
    ('John Doe', '123-456-7890'),
    ('Jane Smith', '987-654-3210'),
    ('Alice Johnson', '555-123-4567');

</pre>

Now create a salary_details table:
<pre>
  CREATE TABLE `employee_data`.`salary_details` (
  `salary_id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NOT NULL,
  `base_salary` DECIMAL(10, 2) NOT NULL,
  `bonus` DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (`salary_id`),
  FOREIGN KEY (`employee_id`) REFERENCES `employee_data`.`employees` (`employee_id`)
);
</pre>

and insert data into salary_details table:
<pre>
  INSERT INTO `employee_data`.`salary_details` (`employee_id`, `base_salary`, `bonus`)
VALUES
    (1, 50000.00, 5000.00), 
    (2, 55000.00, 6000.00),  
    (3, 60000.00, 7000.00);  
</pre>

Now the databases are done. Every time we run the code, we should see that John Doe's salary will be increased by 10000.
<pre>
          String sqlUpdate = "UPDATE salary_details " +
                "SET base_salary = base_salary + 10000 " +
                "WHERE employee_id = ( " +
                "    SELECT employee_id " +
                "    FROM employee_details " +
                "    WHERE name = ? " +
                ");";
</pre>
