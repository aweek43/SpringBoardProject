<%--
  Created by IntelliJ IDEA.
  User: kimhanju
  Date: 2020-03-28
  Time: 오전 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title> 게시글 목록 </title>
</head>
<body>
    <div>
        ${userId} 님 환영합니다.
    </div>
    <br>

    <table border="1">
        <th> 제목 </th>
        <th> 작성자 </th>
        <c:forEach items="${postList}" var="list">
            <tr onclick=readPost(${list.id}); style="cursor: pointer">
                <td> ${list.title} </td>
                <td class="author"> ${list.author.id} </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <c:forEach var="i" begin="0" end="${pageCount}">
        <buttom id="page${i}" onclick=postList(${i}); style="cursor: pointer"> [<c:out value="${i}" />] </buttom>
    </c:forEach>
    <br><br>
    <form action="/writePost" method="get">
        <input type="hidden" value="${userId}" name="userId" />
        <input type="submit" value="글쓰기" />
    </form>

    <form action="/readPost" method="get" id="postForm">
        <input type="hidden" name="postId" id="postId"/>
    </form>
    <form action="/postList" method="get" id="postListForm">
        <input type="hidden" name="page" id="page"/>
    </form>

<script type="application/javascript">
    function readPost(postId) {
        document.getElementById('postId').value = postId;
        document.getElementById('postForm').submit();
    }

    function postList(index) {
        document.getElementById('page').value = index;
        document.getElementById('postListForm').submit();
    }

    window.onload = function() {
        document.getElementById("page${page}").style.fontWeight = '700';
        var authors = document.getElementsByClassName("author");

        for(var i=0; i < authors.length; ++i) {
            var author = authors[i].innerText;
            authors[i].innerText = author.slice(0,4) + "***********".slice(0, author.length - 4);
        }
    }
</script>

</body>
</html>