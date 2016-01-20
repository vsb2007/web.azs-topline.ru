<%--
  Created by IntelliJ IDEA.
  User: VSB
  Date: 19.01.2016
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Employee info</h3>

<p>Name : ${user.name}</p>

<form method="get" action="login">

    <br/> <input type="text" name="name"/>
    <br/> <input type="submit"/>
</form>
</body>
</html>
