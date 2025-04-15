<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Danh sách sách</title>
    <style>
        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #45a049;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            border: 2px solid black;
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h2>Danh sách sách trong thư viện</h2>
<table>
    <tr>
        <th>Mã sách</th>
        <th>Tên sách</th>
        <th>Tác giả</th>
        <th>Số lượng có sẵn</th>
        <th>Mô tả</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="book" items="${bookList}">
        <tr>
            <td>${book.bookId}</td>
            <td>${book.bookName}</td>
            <td>${book.author}</td>
            <td>${book.description}</td>
            <td>${book.amount}</td>
            <td>
                <a href="book?action=borrow&bookId=${book.bookId}">
                    <button type="button">📖 Mượn</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
