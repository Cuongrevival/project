<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Mượn sách</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f2f6fc;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .form-container {
      background-color: white;
      padding: 30px 40px;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
      width: 400px;
    }

    h1 {
      text-align: center;
      color: #333;
      margin-bottom: 20px;
    }

    form p {
      margin-bottom: 15px;
    }

    label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }

    input[type="text"],
    input[type="date"],
    select {
      width: 100%;
      padding: 8px 10px;
      border: 1px solid #ccc;
      border-radius: 6px;
    }

    button {
      padding: 10px 20px;
      margin-top: 10px;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-weight: bold;
    }

    button[type="submit"] {
      background-color: #4CAF50;
      color: white;
    }

    button[type="button"] {
      background-color: #f44336;
      color: white;
      margin-left: 10px;
    }

    .button-group {
      display: flex;
      justify-content: space-between;
    }
  </style>
</head>
<body>
<h1>Mượn sách</h1>

<form method="post" action="book?action=borrow">

  <p>Mã sách: <input type="text" name="bookId" value="${book.bookId}" readonly></p>

  <p>Tên sách: <input type="text" name="bookTitle" value="${book.bookName}" readonly></p>

  <p>Tên học sinh:
    <select name="studentId">
      <c:forEach var="student" items="${studentList}">
        <option value="${student.id}">${student.name}</option>
      </c:forEach>
    </select>
  </p>

  <p>Ngày mượn: <input type="text" id="borrowDate" name="borrowDate" value="${today}" readonly></p>

  <p>Ngày trả: <input type="date" id="returnDate" name="returnDate" onchange="validateReturn()"></p>

  <button type="submit">Mượn</button>
  <a href="book?action=list">
    <button type="button">Hủy</button>
  </a>
</form>

<script>

  function validateReturn() {
    let borrowDate = document.getElementById("borrowDate").value;
    let returnDate = document.getElementById("returnDate").value;
    if (new Date(returnDate) <= new Date(borrowDate)) {
      alert("Ngày trả phải sau ngày mượn!");
      document.getElementById("returnDate").value = "";
    }
  }
</script>

</body>
</html>
