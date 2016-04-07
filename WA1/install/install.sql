DROP TABLE IF EXISTS `settings` CASCADE;
DROP TABLE IF EXISTS `fotos` CASCADE;
DROP TABLE IF EXISTS `galleries` CASCADE;
DROP TABLE IF EXISTS `users` CASCADE;
DROP TABLE IF EXISTS `roles` CASCADE;

-- ------------------------------------------------------------
-- description:
-- 
-- ------------------------------------------------------------

CREATE TABLE `fotos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gallery_id` int(11) NOT NULL,
  `name` varchar(32) COLLATE utf8_czech_ci DEFAULT NULL,
  `filename` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `thumbname` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `uploaded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` text COLLATE utf8_czech_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `foto_unique_in_gallery` (`gallery_id`, `filename`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- ------------------------------------------------------------
-- description:
-- 
-- ------------------------------------------------------------

CREATE TABLE `galleries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(32) COLLATE utf8_czech_ci NOT NULL,
  `uploaded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `public` tinyint(1) NOT NULL DEFAULT '1',
  `password` varchar(128) COLLATE utf8_czech_ci DEFAULT NULL,
  `description` text COLLATE utf8_czech_ci,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- ------------------------------------------------------------
-- description:
-- 
-- ------------------------------------------------------------

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `allgalleries` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `roles` (`id`, `name`, `admin`, `allgalleries`) VALUES
(1, 'default', 0, 0);
INSERT INTO `roles` (`id`, `name`, `admin`, `allgalleries`) VALUES
(2, 'manager', 0, 1);
INSERT INTO `roles` (`id`, `name`, `admin`, `allgalleries`) VALUES
(3, 'admin', 1, 1);

-- ------------------------------------------------------------
-- description:
-- 
-- ------------------------------------------------------------

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '1',
  `username` varchar(32) COLLATE utf8_czech_ci NOT NULL,
  `password` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `email` varchar(32) COLLATE utf8_czech_ci NOT NULL,
  `name` varchar(64) COLLATE utf8_czech_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- admin:administrator
INSERT INTO `users` (`role_id`, `username`, `password`, `email`, `name`) VALUES
(3, 'admin', '$6$mewo.ZcY4ANt$.HUtSXxcx44b14u/lPziW0GBwnL./AdQUFngiUj75Rv/Ij8XOqOlOmnpVRP2Bl8e.6aK1qAv9TBBpXBAQ1Nqs.', 'admin@example.com', '');

-- ------------------------------------------------------------
-- description:
-- 
-- ------------------------------------------------------------

CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8_czech_ci NOT NULL,
  `value` text COLLATE utf8_czech_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

INSERT INTO `settings` (`name`, `value`) VALUES
('title', 'WA1 galerie');

INSERT INTO `settings` (`name`, `value`) VALUES
('copyright', '&copy; 2014 Michal Stanke');

INSERT INTO `settings` (`name`, `value`) VALUES
('used_theme', 'default');

-- ------------------------------------------------------------
-- description:
-- 
-- ------------------------------------------------------------

ALTER TABLE `fotos` ADD CONSTRAINT `ref_fotos_to_galleries` FOREIGN KEY (`gallery_id`)
	REFERENCES `galleries`(`id`)
	ON DELETE cascade
	ON UPDATE cascade;

ALTER TABLE `galleries` ADD CONSTRAINT `ref_galleries_to_users` FOREIGN KEY (`user_id`)
	REFERENCES `users`(`id`)
	ON DELETE cascade
	ON UPDATE cascade;

ALTER TABLE `users` ADD CONSTRAINT `ref_users_to_roles` FOREIGN KEY (`role_id`)
	REFERENCES `roles`(`id`)
	ON DELETE restrict
	ON UPDATE cascade;

