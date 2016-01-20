<%--
  Created by IntelliJ IDEA.
  User: VSB
  Date: 19.01.2016
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%@ include file="header.jsp"%>

<div class="container">
  <div class="row">
    <div class="">

      <form class="form-signin" role="form" action="login" method="POST">
        <h2 class="text-center">Авторизация</h2>
        <div style="display:none;" class="error"></div>
        <input type="text" class="form-control" id="username" name="username" placeholder="User name" required autofocus>
        <input type="password" class="form-control" placeholder="Password" id="password" name="password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Вход</button>
      </form>

    </div>
  </div>
</div>
<!-- /container -->


<%@ include file="footer.jsp"%>