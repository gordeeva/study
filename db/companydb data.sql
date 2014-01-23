SET NAMES 'utf8';
USE companydb;

-- 
-- Вывод данных для таблицы department
-- 
INSERT INTO department VALUES(4, 'executives');
INSERT INTO department VALUES(6, 'dep1');
INSERT INTO department VALUES(11, 'sales dep');
INSERT INTO department VALUES(14, 'managers dep');
INSERT INTO department VALUES(15, 'lunatic');


-- 
-- Вывод данных для таблицы employee
-- 
INSERT INTO employee VALUES(12, 'volaneev', 15);
INSERT INTO employee VALUES(13, 'marchenko', 15);
INSERT INTO employee VALUES(25, 'artur', 11);
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
-- Вывод данных для таблицы role
--
INSERT INTO role VALUES(7, 'manager');
INSERT INTO role VALUES(15, 'sales');

-- 
-- Вывод данных для таблицы employee_role
--
INSERT INTO employee_role VALUES(53, 15);
INSERT INTO employee_role VALUES(54, 7);


