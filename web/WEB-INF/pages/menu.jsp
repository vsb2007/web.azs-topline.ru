<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="sidemenu sidebar responsive" id="navigation-sidemenu" hidden>
    <div class="sidemenu-hero">
        <img class="sidemenu-hero-image" src="demo-files/sidemenu-hero.jpg">
        <h1 class="title serif">Меню</h1>
    </div>
    <ul class="menu">

        <li class="divider"></li>
        <sec:authorize access="hasRole('ROLE_USERS')">
            <li ripple><a href="users"><i class="icon-input"></i>Пользователи</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_COMPANY_LIST')">
            <li ripple><a href="company"><i class="icon-input"></i>Организации</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_BID_CREATE')">
            <li ripple><a href="bidcreate"><i class="icon-input"></i>Создать заявку</a></li>
            <!-- <li ripple><a href="bidEntrance"><i class="icon-input"></i>Поступление Ж.Д.</a></li> -->
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_BID_LIST')">
            <li ripple><a href="bidlistopen"><i class="icon-input"></i>Открытые заявки</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_SALE_CREATE')">
            <li ripple><a href="saleCreate"><i class="icon-input"></i>Создать продажу</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_SALE_LIST')">
            <li ripple><a href="saleList"><i class="icon-input"></i>Открытые продажи</a></li>
        </sec:authorize>
        <%--<sec:authorize access="hasRole('ROLE_BID_LIST_CLOSED')">
            <li ripple><a href="bidcreate"><i class="icon-input"></i>Закрытые заявки</a></li>
        </sec:authorize> --%>
        <sec:authorize access="hasRole('ROLE_LOGIN')">
            <li ripple><a href="logout"><i class="icon-menu"></i>Выход</a></li>
        </sec:authorize>
        <sec:authorize access="!hasRole('ROLE_LOGIN')">
            <li ripple><a href="index"><i class="icon-menu"></i>Вход</a></li>
        </sec:authorize>
    </ul>
</div>
<div class="main-content">
    <div class="toolbar header bg-blue-500 color-white">
        <button class="icon-button" onclick="SideMenu.toggle(document.querySelector('#navigation-sidemenu'))"><i
                class="icon-menu"></i></button>
        <label class="toolbar-label">Меню</label>
	<span class="float-right" id="switch-container">
		<div class="switch">
            <input type="checkbox" id="dark-theme-switch" onchange="Theme.setTheme(this.checked ? 'dark' : 'light')"/>
            <label for="dark-theme-switch"></label>
            <label class="form-control-label">Dark</label>
        </div>
	</span>
    </div>
