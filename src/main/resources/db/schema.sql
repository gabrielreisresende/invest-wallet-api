-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema invest_wallet
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema invest_wallet
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `invest_wallet` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `invest_wallet`;

-- -----------------------------------------------------
-- Table `invest_wallet`.`tb_active_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invest_wallet`.`tb_active_types`
(
    `active_type_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `active_type`    VARCHAR(50) NOT NULL,
    PRIMARY KEY (`active_type_id`),
    UNIQUE INDEX `UK_tbhtmbxaihhhc4tsmvudk4os` (`active_type` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `invest_wallet`.`tb_active_sectors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invest_wallet`.`tb_active_sectors`
(
    `active_sector_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `active_sector`    VARCHAR(50) NOT NULL,
    PRIMARY KEY (`active_sector_id`),
    UNIQUE INDEX `UK_69j5i67mfogysu1ulwcwkl23m` (`active_sector` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 6
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `invest_wallet`.`tb_active_codes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invest_wallet`.`tb_active_codes`
(
    `active_code`      VARCHAR(255) NOT NULL,
    `active_sector_id` BIGINT       NOT NULL,
    `active_type_id`   BIGINT       NOT NULL,
    PRIMARY KEY (`active_code`),
    INDEX `FKqt7mm6rdgnswwqlouof70vxxa` (`active_sector_id` ASC) VISIBLE,
    INDEX `FKevgtgspqvics6tom7ev8g991` (`active_type_id` ASC) VISIBLE,
    CONSTRAINT `FKevgtgspqvics6tom7ev8g991`
        FOREIGN KEY (`active_type_id`)
            REFERENCES `invest_wallet`.`tb_active_types` (`active_type_id`),
    CONSTRAINT `FKqt7mm6rdgnswwqlouof70vxxa`
        FOREIGN KEY (`active_sector_id`)
            REFERENCES `invest_wallet`.`tb_active_sectors` (`active_sector_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `invest_wallet`.`tb_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invest_wallet`.`tb_users`
(
    `user_id`  BIGINT                   NOT NULL AUTO_INCREMENT,
    `deleted`  BIT(1)                   NOT NULL,
    `email`    VARCHAR(255)             NOT NULL,
    `password` VARCHAR(255)             NOT NULL,
    `role`     ENUM ('CLIENT', 'ADMIN') NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `UK_grd22228p1miaivbn9yg178pm` (`email` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 13
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `invest_wallet`.`tb_clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invest_wallet`.`tb_clients`
(
    `client_id`  BIGINT      NOT NULL AUTO_INCREMENT,
    `birth_date` DATE        NOT NULL,
    `cpf`        VARCHAR(14) NOT NULL,
    `deleted`    BIT(1)      NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name`  VARCHAR(50) NOT NULL,
    `phone`      VARCHAR(20) NOT NULL,
    `user_id`    BIGINT      NOT NULL,
    PRIMARY KEY (`client_id`),
    UNIQUE INDEX `UK_m5apblnthcb4t7t3bo0xqaeus` (`cpf` ASC) VISIBLE,
    UNIQUE INDEX `UK_gnntjpir9ci23odvw3jbrrou1` (`user_id` ASC) VISIBLE,
    CONSTRAINT `FKon6me4fd42ira6ev2fm308yuf`
        FOREIGN KEY (`user_id`)
            REFERENCES `invest_wallet`.`tb_users` (`user_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 12
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `invest_wallet`.`tb_wallets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invest_wallet`.`tb_wallets`
(
    `wallet_id` BIGINT      NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(50) NOT NULL,
    `client_id` BIGINT      NOT NULL,
    PRIMARY KEY (`wallet_id`),
    INDEX `FK21mmahe4gkeqqx1oynbieetog` (`client_id` ASC) VISIBLE,
    CONSTRAINT `FK21mmahe4gkeqqx1oynbieetog`
        FOREIGN KEY (`client_id`)
            REFERENCES `invest_wallet`.`tb_clients` (`client_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `invest_wallet`.`tb_actives`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invest_wallet`.`tb_actives`
(
    `active_id`     BIGINT        NOT NULL AUTO_INCREMENT,
    `average_value` DECIMAL(6, 2) NOT NULL,
    `quantity`      INT           NOT NULL,
    `active_code`   VARCHAR(255)  NOT NULL,
    `wallet_id`     BIGINT        NOT NULL,
    PRIMARY KEY (`active_id`),
    INDEX `FKcr4wjc8fcyhk6wu5ikqjt5cre` (`active_code` ASC) VISIBLE,
    INDEX `FKboiqgnhyy9pl8ksa8l8ccsw2b` (`wallet_id` ASC) VISIBLE,
    CONSTRAINT `FKboiqgnhyy9pl8ksa8l8ccsw2b`
        FOREIGN KEY (`wallet_id`)
            REFERENCES `invest_wallet`.`tb_wallets` (`wallet_id`),
    CONSTRAINT `FKcr4wjc8fcyhk6wu5ikqjt5cre`
        FOREIGN KEY (`active_code`)
            REFERENCES `invest_wallet`.`tb_active_codes` (`active_code`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `invest_wallet`.`tb_passoword_reset_codes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invest_wallet`.`tb_passoword_reset_codes`
(
    `id`      BIGINT NOT NULL AUTO_INCREMENT,
    `code`    INT    NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UK_jxjetcc33y2ooxisdouawtdop` (`user_id` ASC) VISIBLE,
    CONSTRAINT `FKs1qo5ubmf736bs3gxxrp62bi6`
        FOREIGN KEY (`user_id`)
            REFERENCES `invest_wallet`.`tb_users` (`user_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
