package org.example.book_management.model;

import java.sql.Date;

public class BorrowCard {
    private String borrowBookId;
    private Book book;
    private Student student;
    private Date dateOfBorrow;
    private Date dateOfReturn;
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

    public Date getDateOfBorrow() {
        return dateOfBorrow;
    }

    public void setDateOfBorrow(Date dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}