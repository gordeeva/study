SET NAMES 'utf8';
USE companydb;

--
-- Описание для таблицы department
--
CREATE TABLE department(
  id INT (11) NOT NULL AUTO_INCREMENT,
  name VARCHAR (45) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 16
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы role
--
CREATE TABLE role(
  id INT (11) NOT NULL AUTO_INCREMENT,
  name VARCHAR (45) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 16
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы employee
--
CREATE TABLE employee(
  id INT (8) NOT NULL AUTO_INCREMENT,
  name VARCHAR (30) NOT NULL,
  department_id INT (11) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX fk_employee_department USING BTREE (department_id),
  CONSTRAINT fk_employee_department FOREIGN KEY (department_id)
  REFERENCES department (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 67
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы employee_role
--
CREATE TABLE employee_role(
  emp_id INT (11) NOT NULL,
  role_id INT (11) NOT NULL,
  PRIMARY KEY (emp_id, role_id),
  INDEX role_id_fk_idx USING BTREE (role_id),
  CONSTRAINT emp_id_fk FOREIGN KEY (emp_id)
  REFERENCES employee (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT role_id_fk FOREIGN KEY (role_id)
  REFERENCES role (id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;