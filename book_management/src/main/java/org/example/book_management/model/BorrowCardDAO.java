package org.example.book_management.model;



import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BorrowCardDAO {
    private Connection connection = DBConnection.connect();

    public void insertCard(int studentId, int bookId, String borrowDate, String returnDate) throws SQLException {
        String query = "INSERT INTO BorrowCard (studentid, bookid, BorrowDate, ReturnDate, Status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, bookId);
            stmt.setString(3, convertJSPToDBDateFormat(borrowDate));
            stmt.setString(4, convertJSPToDBDateFormat(returnDate));
            stmt.setBoolean(5, true);
            stmt.executeUpdate();
        }
        new BookDAO().reduceCopies(bookId);
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

    public void returnBook(int cardId) throws SQLException {
        String query = "UPDATE BorrowCard SET ReturnDate = CURDATE(), Status = false WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cardId);
            stmt.executeUpdate();
        }
    }

    public List<BorrowCard> getBorrowedCardList() throws SQLException {
        List<BorrowCard> borrowCardList = new ArrayList<>();
        String query = "SELECT bc.id, b.title AS bookTitle, b.author AS bookAuthor, "
                + "s.name AS studentName, s.class AS studentClass, "
                + "bc.BorrowDate, bc.ReturnDate "
                + "FROM BorrowCard bc "
                + "JOIN book b ON bc.bookid = b.id "
                + "JOIN Student s ON bc.studentid = s.id "
                + "WHERE bc.Status = true";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BorrowCard borrowCard = new BorrowCard();
                borrowCard.setId(rs.getInt("id"));
                Book book = new Book();
                book.setTitle(rs.getString("bookTitle"));
                book.setAuthor(rs.getString("bookAuthor"));
                borrowCard.setBook(book);
                Student student = new Student();
                student.setName(rs.getString("studentName"));
                student.setClassName(rs.getString("studentClass"));
                borrowCard.setStudent(student);
                String borrowDate = convertDBToJSPDateFormat(rs.getString("BorrowDate"));
                String returnDate = convertDBToJSPDateFormat(rs.getString("ReturnDate"));
                borrowCard.setBorrowDate(borrowDate);
                borrowCard.setReturnDate(returnDate);
                borrowCardList.add(borrowCard);
            }
        }
        return borrowCardList;
    }
}
