create database book_management;
use book_management;

CREATE TABLE book (
    book_id INT PRIMARY KEY,
    book_name VARCHAR(255),
    author VARCHAR(255),
    description LONGTEXT,
    amount INT
);

CREATE TABLE Student (
    student_id INT PRIMARY KEY,
    full_name VARCHAR(255),
    class CHAR(4)
);

CREATE TABLE borrow_card (
    borrow_book_id INT PRIMARY KEY,
    book_id INT,
    student_id INT,
    status BOOLEAN,
    date_of_borrow DATE,
    date_of_return DATE,
    FOREIGN KEY (book_id)
        REFERENCES book (book_id),
    FOREIGN KEY (student_id)
        REFERENCES Student (student_id)
);