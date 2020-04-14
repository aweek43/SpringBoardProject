<%--
  Created by IntelliJ IDEA.
  User: kimhanju
  Date: 2020-03-31
  Time: 오전 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> 글쓰기 </title>
</head>
<body>
    <form action="/savePost" method="post" id="writePostForm">
        제목:
        <input type="text" name="title" id="title" />
        <br>
        내용: <br>
        <textarea name="content" id="content"></textarea>
        <br>
        <input type="button" value="글쓰기" onclick="writePost()" />
        <input type="hidden" value="${userId}" name="userId" />
    </form>

<script type="application/javascript">
    function writePost() {
        if(document.getElementById('title').value.length == 0 || document.getElementById('content').value.length == 0) {
            alert("제목이나 내용에 빈 칸이 있습니다.");
        }
        else {
            document.getElementById('writePostForm').submit();
        }
    }
</script>

</body>
</html>