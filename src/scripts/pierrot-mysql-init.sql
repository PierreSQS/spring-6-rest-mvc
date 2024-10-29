DROP DATABASE IF EXISTS jt_spring6_sect46_assn44_db;
DROP USER IF EXISTS `restadmin`@`%`;
CREATE DATABASE IF NOT EXISTS jt_spring6_sect46_assn44_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE jt_spring6_sect46_assn44_db;
CREATE USER IF NOT EXISTS `restadmin`@`%` IDENTIFIED WITH mysql_native_password BY 'restadmin';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `jt_spring6_sect46_assn44_db`.* TO `restadmin`@`%`;