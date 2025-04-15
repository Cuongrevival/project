package org.example.examination.controller;

import org.example.book_management.model.*;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final BorrowCardDAO borrowCardDAO = new BorrowCardDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "borrow":
                int bookId = Integer.parseInt(request.getParameter("bookId"));
                Book book = null;
                List<Student> studentList = null;
                String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                try {
                    book = bookDAO.getBookById(bookId);
                    studentList = studentDAO.getStudentList();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                request.setAttribute("today", today);
                request.setAttribute("studentList", studentList);
                request.setAttribute("book", book);
                request.getRequestDispatcher("borrow.jsp").forward(request, response);
                break;
            case "list":
                List<BorrowCard> borrowCardList = null;
                try {
                    borrowCardList = borrowCardDAO.getBorrowedCardList();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                request.setAttribute("borrowCardList", borrowCardList);
                request.getRequestDispatcher("list.jsp").forward(request, response);
                break;
            case "return":
                int borrowCardId = Integer.parseInt(request.getParameter("borrowCardId"));
                try {
                    borrowCardDAO.returnBook(borrowCardId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                try {
                    request.setAttribute("bookList", bookDAO.getAllBooks());
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "borrow":
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                int bookId = Integer.parseInt(request.getParameter("bookCode").replace("S-", ""));
                String borrowDate = request.getParameter("borrowDate");
                String returnDate = request.getParameter("returnDate");
                try {
                    borrowCardDAO.insertCard(studentId, bookId, borrowDate, returnDate);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
        }
    }
}