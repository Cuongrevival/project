package org.example.book_management.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final Connection connection;
    public BookDAO() {
        connection = DBConnection.connect();
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM book";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setAmount(rs.getInt("amount"));

                bookList.add(book);
            }
        }
        return bookList;
    }

    public Book getBookById(String bookId) throws SQLException {
        String query = "SELECT * FROM book WHERE book_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setAmount(rs.getInt("amount"));
                return book;
            }
        }
        return null;
    }

    public void reduceCopies(String bookId) throws SQLException {
        String query = "UPDATE book SET amount = amount - 1 WHERE book_id = ? AND amount > 0";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, bookId);
            stmt.executeUpdate();
        }
    }

    public void increaseCopies(String bookId) throws SQLException {
        String query = "UPDATE book SET amount = amount + 1 WHERE book_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, bookId);
            stmt.executeUpdate();
        }
    }
}
