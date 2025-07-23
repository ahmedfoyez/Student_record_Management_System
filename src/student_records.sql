
-- Create Database
CREATE DATABASE student_db;

-- Use the created database
USE student_db;

-- Create the students table
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    grade VARCHAR(10),
    email VARCHAR(100)
);

-- Insert sample data
INSERT INTO students (name, age, grade, email) VALUES ('John Doe', 20, 'A', 'john@example.com');
INSERT INTO students (name, age, grade, email) VALUES ('Jane Doe', 22, 'B', 'jane@example.com');
INSERT INTO students (name, age, grade, email) VALUES ('Alex Lee', 21, 'A', 'alex@example.com');
INSERT INTO students (name, age, grade, email) VALUES ('Emma Kim', 19, 'C', 'emma@example.com');
INSERT INTO students (name, age, grade, email) VALUES ('Chris Zhang', 23, 'B', 'chris@example.com');
