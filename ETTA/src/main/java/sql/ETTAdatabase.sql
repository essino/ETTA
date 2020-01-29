CREATE DATABASE economy;

USE economy;

CREATE TABLE groups (
	group_id INT NOT NULL AUTO_INCREMENT,
	description VARCHAR(50) NOT NULL,
	PRIMARY KEY (group_id)
	);
	
CREATE TABLE all_transfers (
	transfer_id INT NOT NULL AUTO_INCREMENT,
	description VARCHAR(50) NOT NULL,
	group_id INT,   
	income Boolean,
	date DATE,   TÄHÄN DEFAULTVALUE CURRENT DATE
	amount FLOAT,
	PRIMARY KEY (transfer_id),
	FOREIGN KEY (group_id) REFERENCES groups(group_id)
	);
	
CREATE TABLE all_savings (
	saving_id INT NOT NULL AUTO_INCREMENT,
	description VARCHAR(50) NOT NULL,
	amount FLOAT,
	goal_amount FLOAT,
	goal_date DATE,
	reached_goal FLOAT,
	PRIMARY KEY (saving_id)
	);
	
CREATE TABLE saldo (
	id INT NOT NULL AUTO_INCREMENT,
	balance FLOAT NOT NULL DEFAULT '0',
	PRIMARY KEY (id)
	);
	
CREATE TABLE people (
	person_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	birthday DATE,
	email VARCHAR(50),
	PRIMARY KEY (person_id)
	);
	
CREATE TABLE borrowed_things (
	thing_id INT NOT NULL AUTO_INCREMENT,
	description VARCHAR(50) NOT NULL,
	dateBorrow DATE,  TÄHÄN DEFAULTVALUE CURRENT DATE
	returnDate DATE,
	person_id INT,
	PRIMARY KEY (thing_id),
	FOREIGN KEY (person_id) REFERENCES people(person_id)
	);

CREATE TABLE events (
	event_id INT NOT NULL AUTO_INCREMENT,
	description VARCHAR(50) NOT NULL,
	date DATE,
	startTime TIME,
	PRIMARY KEY (event_id)
	);	

CREATE TABLE shoppinglistPresents (
	item_id INT NOT NULL AUTO_INCREMENT,
	description VARCHAR(50) NOT NULL,
	person_id INT,
	price Double,
	bought Boolean,
	dateNeeded DATE,
	additionalInfo VARCHAR(100),
	PRIMARY KEY (item_id),
	FOREIGN KEY (person_id) REFERENCES people(person_id)
	);	
	
INSERT INTO groups (description) 
VALUES ("food"), ("clothes"), ("apartment"), ("furniture"), ("fun"), ("eating out");

INSERT INTO all_transfers (description, group_id, income, date, amount) VALUES 
	("k-market", "1", false, curdate(), "-15.65");
	
INSERT INTO all_transfers (description, group_id, income, date, amount) VALUES 
	("s-market", "1", false, curdate(), "-10.65");
	
INSERT INTO saldo (balance) VALUES ("100");

UPDATE saldo SET balance=balance + (SELECT amount FROM all_transfers WHERE transfer_id=2) where id=1;

INSERT INTO people (name, birthday, email) VALUES ("Lena", "1980-08-10", "elena.myllari@metropolia.fi");