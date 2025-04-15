package org.example.book_management.controller;

import org.example.book_management.model.*;

import java.io.IOException;
import java.sql.SQLException;
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
        if (action == null) action = "";

        switch (action) {
            case "borrow": {
                String bookId = request.getParameter("bookId");
                Book book = null;
                List<Student> studentList = null;
                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                try {
                    book = bookDAO.getBookById(bookId);
                    studentList = studentDAO.getStudentList();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                request.setAttribute("today", today);
                request.setAttribute("studentList", studentList);
                request.setAttribute("book", book);
                request.getRequestDispatcher("borrow.jsp").forward(request, response);
                break;
            }
            case "list": {
                try {
                    List<BorrowCard> borrowCardList = borrowCardDAO.getBorrowedCardList();
                    request.setAttribute("borrowCardList", borrowCardList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                request.getRequestDispatcher("list.jsp").forward(request, response);
                break;
            }
            case "return": {
                String borrowBookId = request.getParameter("borrowBookId");
                try {
                    borrowCardDAO.returnBook(borrowBookId); // đã tăng ở đây
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
                response.sendRedirect("book?action=list");
                break;
            }

            default: {
                try {
                    List<Book> bookList = bookDAO.getAllBooks();
                    request.setAttribute("bookList", bookList);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "borrow": {
                String studentIdStr = request.getParameter("studentId");
                if (studentIdStr != null && !studentIdStr.isEmpty()) {
                    try {
                        int studentId = Integer.parseInt(studentIdStr);
                        String bookId = request.getParameter("bookId");
                        String borrowDate = request.getParameter("borrowDate");
                        String returnDate = request.getParameter("returnDate");

                        borrowCardDAO.insertCard(studentId, bookId, borrowDate, returnDate);
                        bookDAO.reduceCopies(bookId);
                        response.sendRedirect("book?action=list");
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Student ID phải là số hợp lệ!");
                        request.getRequestDispatcher("borrow.jsp").forward(request, response);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    request.setAttribute("error", "Student ID không thể để trống!");
                    request.getRequestDispatcher("borrow.jsp").forward(request, response);
                }
                break;
            }
            default:
                response.sendRedirect("book");
        }
    }

}
