drop table if exists `user`;
CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NULL,
  `password` VARCHAR(128) NULL,
  `salt` VARCHAR(32) NULL,
  `email` VARCHAR(256) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
