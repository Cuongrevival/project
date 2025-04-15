package org.example.book_management.model;

import java.sql.Date;

public class BorrowCard {
    private String borrowBookId;
    private Book book;
    private Student student;
    private String dateOfBorrow;
    private String dateOfReturn;
    private boolean status;

    public String getBorrowBookId() {
        return borrowBookId;
    }

    public void setBorrowBookId(String borrowBookId) {
        this.borrowBookId = borrowBookId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getDateOfBorrow() {
        return dateOfBorrow;
    }

    public void setDateOfBorrow(String dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(String dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}