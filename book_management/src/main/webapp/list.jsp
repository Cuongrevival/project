<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Danh sách sách đang được mượn</title>
  <style>
    table {
      border-collapse: collapse;
      width: 100%;
      border: 2px solid black;
    }

    th, td {
      border: 1px solid gray;
      padding: 8px;
      text-align: left;
    }

    th {
      background-color: #f2f2f2;
    }

    .return-btn {
      background-color: #ff4d4d;
      color: white;
      border: none;
      padding: 5px 10px;
      cursor: pointer;
    }
  </style>
</head>
<body>
<h1>Danh sách sách đang được mượn</h1>

<table>
  <tr>
    <th>Mã mượn sách</th>
    <th>Tên sách</th>
    <th>Tác giả</th>
    <th>Tên học sinh</th>
    <th>Lớp</th>
    <th>Ngày mượn</th>
    <th>Ngày trả</th>
    <th>Hành động</th>
  </tr>

  <c:forEach var="borrowCard" items="${borrowCardList}">
    <tr>
      <td>MS-${String.format("%04d", borrowCard.id)}</td>
      <td>${borrowCard.book.title}</td>
      <td>${borrowCard.book.author}</td>
      <td>${borrowCard.student.name}</td>
      <td>${borrowCard.student.className}</td>
      <td>${borrowCard.borrowDate}</td>
      <td>${borrowCard.returnDate}</td>
      <td>
        <a href="?action=return&borrowCardId=${borrowCard.id}">
          <button type="button" class="return-btn" onclick="confirmReturn()">Trả sách</button>
        </a>
      </td>
    </tr>
  </c:forEach>
</table>
<script>
  function confirmReturn() {
    return confirm("Chắc muốn trả sách?");
  }
</script>

</body>
</html>