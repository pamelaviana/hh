CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  birthday DATE NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(256) NOT NULL,
  user_role VARCHAR(255)
);
--
--INSERT INTO CUSTOMERS
--(`id`, `name`, `bornAt`)
--VALUES
--(1, 'John', 1993),
--(2, 'Peter', 1991),
--(3, 'Hack', 1995);