-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db1` ;

-- -----------------------------------------------------
-- Table `db1`.`email_content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`email_content` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `content` VARCHAR(1024) NOT NULL)
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id` ON `db1`.`email_content` (`id` ASC) VISIBLE;

CREATE INDEX `email_content_i` ON `db1`.`email_content` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db1`.`email_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`email_address` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `address` VARCHAR(50) NOT NULL)
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id` ON `db1`.`email_address` (`id` ASC) VISIBLE;

CREATE INDEX `email_address_i` ON `db1`.`email_address` (`address` ASC) VISIBLE;

CREATE INDEX `email_address_i2` ON `db1`.`email_address` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db1`.`email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`email` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email_from_id` BIGINT NOT NULL,
  `content_id` BIGINT NOT NULL,
  `title` VARCHAR(128) NOT NULL,
  `importance` SMALLINT NULL DEFAULT NULL,
  CONSTRAINT `content_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `db1`.`email_content` (`id`),
  CONSTRAINT `email_from_fk`
    FOREIGN KEY (`email_from_id`)
    REFERENCES `db1`.`email_address` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id` ON `db1`.`email` (`id` ASC) VISIBLE;

CREATE INDEX `email_from_fk` ON `db1`.`email` (`email_from_id` ASC) VISIBLE;

CREATE INDEX `content_fk` ON `db1`.`email` (`content_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db1`.`email_att`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`email_att` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email_id` BIGINT NOT NULL,
  `src` VARCHAR(255) NOT NULL,
  CONSTRAINT `email_fk`
    FOREIGN KEY (`email_id`)
    REFERENCES `db1`.`email` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id` ON `db1`.`email_att` (`id` ASC) VISIBLE;

CREATE INDEX `email_fk` ON `db1`.`email_att` (`email_id` ASC) VISIBLE;

CREATE INDEX `email_att_i` ON `db1`.`email_att` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db1`.`email_email_cc`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`email_email_cc` (
  `email_id` BIGINT NOT NULL,
  `email_cc_id` BIGINT NOT NULL,
  CONSTRAINT `email_cc_address_fk`
    FOREIGN KEY (`email_id`)
    REFERENCES `db1`.`email` (`id`),
  CONSTRAINT `email_cc_cc_address_fk`
    FOREIGN KEY (`email_cc_id`)
    REFERENCES `db1`.`email_address` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `email_cc_cc_address_fk` ON `db1`.`email_email_cc` (`email_cc_id` ASC) VISIBLE;

CREATE INDEX `email_cc_address_fk` ON `db1`.`email_email_cc` (`email_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `db1`.`email_email_to`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`email_email_to` (
  `email_id` BIGINT NOT NULL,
  `email_to_id` BIGINT NOT NULL,
  CONSTRAINT `email_address_fk`
    FOREIGN KEY (`email_to_id`)
    REFERENCES `db1`.`email_address` (`id`),
  CONSTRAINT `email_to_address_fk`
    FOREIGN KEY (`email_id`)
    REFERENCES `db1`.`email` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `email_address_fk` ON `db1`.`email_email_to` (`email_to_id` ASC) VISIBLE;

CREATE INDEX `email_to_address_fk` ON `db1`.`email_email_to` (`email_id` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
