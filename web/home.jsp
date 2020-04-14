<%--
  Created by IntelliJ IDEA.
  User: kimhanju
  Date: 2020-03-23
  Time: 오후 5:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <div>
        스프링 게시판 프로젝트의 메인화면입니다.
    </div>
    <button onclick="location.href='signUp.jsp'"> 회원가입 </button>
    <button onclick="location.href='login.jsp'"> 로그인 </button>
    ${success}
</body>
</html>
