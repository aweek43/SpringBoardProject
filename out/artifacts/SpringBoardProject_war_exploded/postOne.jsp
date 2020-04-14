<%--
  Created by IntelliJ IDEA.
  User: kimhanju
  Date: 2020-03-30
  Time: 오후 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> ${title}  </title>
</head>
<body>
    <div>
        제목: ${title}
    </div>
    <div>
        내용: ${content}
    </div>
    <div>
        글쓴이:
        <input type="text" id="author" value="${author}" disabled style="border: 0; background-color: white"/>
    </div>
    <div>
        작성 시각: ${generatedDate}
    </div>
    <br>
    <button onclick="updatePost();">수정하기</button>
    <br><br>
    <button onclick="location.href='/postList?page=0'"> 목록으로 </button>

    <form action="/updatePost" method="post" id="updateForm">
        <input type="hidden" name="postId" id="postId" />
        <input type="hidden" name="title" id="title" />
        <input type="hidden" name="content" id="content" />
    </form>

<script type="application/javascript">
    function updatePost() {
        if ("${userId}" == "${author}") {
            document.getElementById('postId').value = "${postId}";
            document.getElementById('title').value = "${title}";
            document.getElementById('content').value = "${content}";
            document.getElementById('updateForm').submit();
        }
        else {
            alert("글쓴이가 아닙니다.");
        }
    }

    window.onload = function() {
        var author =  document.getElementById("author").value;
        document.getElementById("author").value = author.slice(0,4) + "***********".slice(0, author.length - 4);
    }

</script>

</body>
</html>
