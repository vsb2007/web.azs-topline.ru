<%@ page import="com.web.azstopline.models.SiteUser" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: VSB
  Date: 08.02.2016
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SiteUser user = (SiteUser) session.getAttribute("dbUserName");
    String userName = "";
    if (user != null && user.getName().equals("admin")) {
        userName = user.getName();
    } else {
%>
<jsp:forward page="/login"/>

<%

    }
%>

<%@ include file="../header.jsp" %>
<%@ include file="../menu.jsp" %>

<%

    if (userName == null || !userName.equals("admin")) {

%>
<div class="section">
    <h2>Авторизация должна быть</h2>
</div>
<%

} else {
    SiteUser redUser = (SiteUser) request.getAttribute("reduser");
    String error = (String) request.getAttribute("error");

%>

<%
    if (redUser == null || (error != null && !error.equals(""))) {
%>
<div class="section">
    <h2>Ошибка: <%=error%>
        <%
            if (redUser == null) {
        %>
        Пользователь не найден
        <%
            }
        %>
    </h2></div>
<%
} else {
    if (redUser != null) {
%>
<div class="section">
    <h2>Пользователь <%=redUser.getName()%>
    </h2>
    <form action="/userred" method="post">
        <ul class="list">
            <li ripple>
            <span class="item-text">

                <input type="text" class="text-input border-green-500" placeholder="Имя пользователя"
                       value="<%=redUser.getName()%>"
                       id="user-name-label" name="user-name-label"
                >
               <span class="secondary-text">
                    <label for="user-name-label">Имя пользователя</label>
               </span>
		</span>
            </li>
            <li ripple>
            <span class="item-text">
			<input type="password" class="text-input border-green-500" placeholder="******"
                   value=""
                   id="user-password-label" name="user-password-label"
            >
			<span class="secondary-text">
				<label for="user-password-label" class="label">Пароль</label>
			</span>
		</span>
            </li>
            <li ripple>
            <span class="item-text">
			<input type="text" class="text-input border-green-500" placeholder="Ф.И.О."
                   value="<%=redUser.getFio()%>"
                   id="user-fio-label" name="user-fio-label"
            >
			<span class="secondary-text">
				<label for="user-fio-label" class="label">ФИО</label>
			</span>
		</span>
            </li>
            <li ripple>
            <span class="item-text">
			<input type="tel" class="text-input border-green-500" placeholder="Телефон"
                   value="<%=redUser.getPhone()%>"
                   id="user-phone-label" name="user-phone-label"
            >
			<span class="secondary-text">
				<label for="user-phone-label" class="label">Телефон</label>
			</span>
		</span>
            </li>
            <li ripple>
            <span class="item-text">
			<input type="email" class="text-input border-green-500" placeholder="E-mail"
                   value="<%=redUser.getEmail()%>"
                   id="user-email-label" name="user-email-label"
            >
			<span class="secondary-text">
				<label for="user-email-label" class="label">E-Mail</label>
			</span>
		</span>
            </li>

            <li ripple>
                <span class="item-text">
                <div class="switch">
                    <input type="checkbox" id="user-active-flag" name="user-active-flag" value="0"/>
                    <label for="user-active-flag"></label>
                </div>
                <span class="secondary-text">
				    <label for="user-active-flag" class="label">Активен</label>
			</span>
		</span>


            </li>

        </ul>
        <input type="hidden" id="red_form" value="1" name="red_form">
        <input type="hidden" id="user-red-id-label" name="user-red-id-label" value="<%=redUser.getId()%>">
        <button class="button raised color-white bg-blue-500" type="submit">Сохранить</button>
    </form>
</div>
<div class="section">
    <form action="/userred" method="post">
        <input type="hidden" id="delete_form" value="1" name="delete_form">
        <input type="hidden" id="user-delete-id-label" name="user-delete-id-label" value="<%=redUser.getId()%>">
        <button class="button raised color-white bg-red-500" type="submit">Удалить</button>
    </form>
</div>

<%
        }

    }
%>

<%
    }

%>

<%@ include file="../footer.jsp" %>
