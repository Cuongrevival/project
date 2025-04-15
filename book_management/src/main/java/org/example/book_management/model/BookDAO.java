package org.example.book_management.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final Connection connection = DBConnection.connect();


    public List<Book> getAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM book");
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setCode(rs.getString("code"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setAvailableCopies(rs.getInt("AvailableCopies"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookList;
    }

    public Book getBookById(int id) throws SQLException {
        String query = "SELECT * FROM book WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setCode(rs.getString("code"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setAvailableCopies(rs.getInt("AvailableCopies"));
                return book;
            }
        }
        return null;
    }
    public void reduceCopies(int id) throws SQLException {
        String query = "UPDATE book SET AvailableCopies = AvailableCopies - 1 WHERE id = ? AND AvailableCopies > 0";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}