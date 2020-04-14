<%--
  Created by IntelliJ IDEA.
  User: kimhanju
  Date: 2020-03-25
  Time: 오전 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> 로그인 </title>
</head>
<body>
    <div>
        로그인을 할 수 있습니다.
    </div>
    <br>
    <form action="/requestLogin" method="post">
    아이디:
    <input type="text" name="userId" />
    <br>
    비밀번호:
    <input type="password" name="password" />
    <input type="submit" value="로그인" />
        </form>
    ${emptyResult}
</body>
</html>