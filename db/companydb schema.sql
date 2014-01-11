/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;

SET NAMES 'utf8';
USE companydb;

--
-- Описание для таблицы department
--
CREATE TABLE department(
  id INT (11) NOT NULL AUTO_INCREMENT,
  name VARCHAR (45) DEFAULT NULL,
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
  name VARCHAR (45) DEFAULT NULL,
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
  name VARCHAR (30) DEFAULT NULL,
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
  roles_order INT (11) NOT NULL,
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

-- 
-- Вывод данных для таблицы department
-- 
INSERT INTO department VALUES(4, 'executives');
INSERT INTO department VALUES(6, 'dep1');
INSERT INTO department VALUES(11, 'sales dep');
INSERT INTO department VALUES(14, 'managers dep');
INSERT INTO department VALUES(15, 'lunatic');


-- 
-- Вывод данных для таблицы role
-- 
INSERT INTO role VALUES(7, 'manager');
INSERT INTO role VALUES(15, 'sales');


-- 
-- Вывод данных для таблицы employee
-- 
INSERT INTO employee VALUES(12, 'volaneev', 15);
INSERT INTO employee VALUES(13, 'marchenko', 15);
INSERT INTO employee VALUES(25, 'artur', 11);
INSERT INTO employee VALUES(41, 'name1', 4);
INSERT INTO employee VALUES(50, 'name', 11);
INSERT INTO employee VALUES(51, 'name', 11);
INSERT INTO employee VALUES(52, 'name2', 4);
INSERT INTO employee VALUES(53, 'elka', 15);
INSERT INTO employee VALUES(54, 'Julia', 15);
INSERT INTO employee VALUES(55, 'April', 11);
INSERT INTO employee VALUES(56, 'Avrora', 11);
INSERT INTO employee VALUES(57, 'Luna', 4);
INSERT INTO employee VALUES(58, 'Solaris', 15);
INSERT INTO employee VALUES(59, 'May', 15);
INSERT INTO employee VALUES(60, 'Eddy', 4);
INSERT INTO employee VALUES(61, 'Sheldon', 15);
INSERT INTO employee VALUES(62, 'Seed', NULL);
INSERT INTO employee VALUES(63, 'Scrat', NULL);
INSERT INTO employee VALUES(64, 'Bolt', NULL);
INSERT INTO employee VALUES(65, 'One', NULL);
INSERT INTO employee VALUES(66, 'ihoo', NULL);


-- 
-- Вывод данных для таблицы employee_role
-- 
INSERT INTO employee_role VALUES(50, 7, 0);
INSERT INTO employee_role VALUES(50, 15, 1);
INSERT INTO employee_role VALUES(51, 7, 1);
INSERT INTO employee_role VALUES(51, 15, 0);
INSERT INTO employee_role VALUES(52, 7, 0);
INSERT INTO employee_role VALUES(53, 15, 0);
INSERT INTO employee_role VALUES(54, 7, 0);


/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
