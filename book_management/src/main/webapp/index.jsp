<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Danh sách sách</title>
    <style>
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
<table>
    <tr>
        <th>Mã sách</th>
        <th>Tên sách</th>
        <th>Tác giả</th>
        <th>Số lượng</th>
        <th>Mô tả</th>
        <th></th>
    </tr>
    <c:forEach var="book" items="${bookList}">
        <tr>
            <td>${book.code}</td>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.availableCopies}</td>
            <td>${book.description}</td>
            <td>
                <a href="?action=borrow&bookId=${book.id}">
                    <button>Mượn</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>