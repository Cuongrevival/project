package org.example.book_management.model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BorrowCardDAO {
    private Connection connection = DBConnection.connect();

    public void insertCard(int studentId, String bookId, String borrowDate, String returnDate) throws SQLException {
        String query = "INSERT INTO borrow_card (borrow_book_id, student_id, book_id, date_of_borrow, date_of_return, status) VALUES (?, ?, ?, ?, ?, ?)";

        String borrowBookId = generateBorrowBookId();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, borrowBookId);
            stmt.setInt(2, studentId);
            stmt.setString(3, bookId);
            stmt.setString(4, convertJSPToDBDateFormat(borrowDate));
            stmt.setString(5, convertJSPToDBDateFormat(returnDate));
            stmt.setBoolean(6, true);
            stmt.executeUpdate();
        }

        new BookDAO().reduceCopies(bookId);
    }

    private String generateBorrowBookId() throws SQLException {
        // Lấy số lượng thẻ mượn hiện tại để tạo mã mới
        String query = "SELECT COUNT(*) FROM borrow_card";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return String.format("MS-%04d", count + 1);    }
        }
        return null;
    }

    private String convertDBToJSPDateFormat(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            return outputFormat.format(inputFormat.parse(inputDate));
        } catch (ParseException e) {
            return null;
        }
    }

    private String convertJSPToDBDateFormat(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(inputFormat.parse(inputDate));
        } catch (ParseException e) {
            return null;
        }
    }
    public void returnBook(String borrowBookId) throws SQLException {

        String getBookIdQuery = "SELECT book_id FROM borrow_card WHERE borrow_book_id = ?";
        String bookId = null;

        try (PreparedStatement stmt = connection.prepareStatement(getBookIdQuery)) {
            stmt.setString(1, borrowBookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bookId = rs.getString("book_id");
            }
        }

        String updateQuery = "UPDATE borrow_card SET date_of_return = CURDATE(), status = false WHERE borrow_book_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, borrowBookId);
            stmt.executeUpdate();
        }

        // Tăng số lượng sách
        if (bookId != null) {
            new BookDAO().increaseCopies(bookId);
        }
    }


    public List<BorrowCard> getBorrowedCardList() throws SQLException {
        List<BorrowCard> borrowCardList = new ArrayList<>();
        String query = "SELECT bc.borrow_book_id, b.book_id, b.book_name AS bookTitle, b.author AS bookAuthor, "
                + "s.student_id, s.full_name AS studentName, s.`class` AS studentClass, "
                + "bc.date_of_borrow, bc.date_of_return "
                + "FROM borrow_card bc "
                + "JOIN book b ON bc.book_id = b.book_id "
                + "JOIN student s ON bc.student_id = s.student_id "
                + "WHERE bc.status = true";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BorrowCard borrowCard = new BorrowCard();
                borrowCard.setBorrowBookId(rs.getString("borrow_book_id"));

                Book book = new Book();
                book.setBookId(rs.getString("book_id"));
                book.setBookName(rs.getString("bookTitle"));
                book.setAuthor(rs.getString("bookAuthor"));
                borrowCard.setBook(book);

                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setName(rs.getString("studentName"));
                student.setClassName(rs.getString("studentClass"));
                borrowCard.setStudent(student);

                Date borrowDate = rs.getDate("date_of_borrow");
                borrowCard.setDateOfBorrow(borrowDate);

                Date returnDate = rs.getDate("date_of_return");
                if (returnDate != null) {
                    borrowCard.setDateOfReturn(returnDate);
                }

                borrowCardList.add(borrowCard);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return borrowCardList;
    }

}
