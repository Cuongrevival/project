<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Mượn sách</title>
</head>
<body>
<h1>Mượn sách</h1>

<form method="post">
  <p>Mã sách: <input type="text" name="bookCode" value="${book.code}" readonly></p>
  <p>Tên sách: <input type="text" name="bookTitle" value="${book.title}" readonly></p>

  <p>Tên học sinh:
    <select name="studentId">
      <c:forEach var="student" items="${studentList}">
        <option value="${student.id}">${student.name}</option>
      </c:forEach>
    </select>
  </p>

  <p>Ngày mượn: <input type="text" id="borrowDate" name="borrowDate" value="${today}" readonly></p>
  <p>Ngày trả: <input type="text" id="returnDate" name="returnDate" onchange="validateReturn()"></p>

  <button type="submit">Mượn</button>
  <a href="book?action=">
    <button type="button">Hủy</button>
  </a>
</form>
<script>
  function validateReturn() {
    let borrowDate = document.getElementById("borrowDate").value;
    let returnDate = document.getElementById("returnDate").value;
    if (parseInt(returnDate) <= parseInt(borrowDate)) {
      alert("Ngày trả phải sau ngày mượn!");
      document.getElementById("returnDate").value = "";
    }
  }
</script>
</body>
</html>