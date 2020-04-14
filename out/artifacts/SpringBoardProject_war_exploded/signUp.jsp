<%--
  Created by IntelliJ IDEA.
  User: kimhanju
  Date: 2020-03-25
  Time: 오전 2:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
        <div>
            회원가입을 할 수 있습니다.
        </div>
    <br>
        <form action="/requestSignUp" method="post" id="signUpForm">
    아이디(6~15자):
    <input type="text" name="userId" id="userId" />
    <br>
    비밀번호:
    <input type="password" name="password" id="password1" />
    <br>
    비밀번호 확인:
    <input type="password" id="password2" />
    <br><br>
    <input type="button" value="회원가입" onclick="signUp();" />
        </form>
    ${duplicateKey}

<script type="application/javascript">
    function signUp() {
        var idLength = document.getElementById("userId").value.length;
        if(idLength < 6 || idLength > 15) {
            alert("아이디는 6자 이상, 15자 이하여야 합니다.");
        }
        else if(document.getElementById("password1").value != document.getElementById("password2").value) {
            alert("비밀번호가 서로 다릅니다.");
        }
        else {
            document.getElementById('signUpForm').submit();
        }
    }
</script>

</body>
</html>
