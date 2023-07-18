CREATE TABLE IF NOT EXISTS CUSTOMERS
(
    `id`        int AUTO_INCREMENT  NOT NULL,
    `name`      varchar(50)         NOT NULL,
    `bornAt`    int                 NOT NULL,
    PRIMARY KEY(`id`)
);
--
--INSERT INTO CUSTOMERS
--(`id`, `name`, `bornAt`)
--VALUES
--(1, 'John', 1993),
--(2, 'Peter', 1991),
--(3, 'Hack', 1995);