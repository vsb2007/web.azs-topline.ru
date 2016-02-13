<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="sidemenu sidebar responsive" id="navigation-sidemenu" hidden>
    <div class="sidemenu-hero">
        <img class="sidemenu-hero-image" src="demo-files/sidemenu-hero.jpg">
        <h1 class="title serif">Меню</h1>
    </div>
    <ul class="menu">

        <li class="divider"></li>
        <%

            if (user != null && user.getName().equals("admin")) {
        %>
        <li ripple><a href="/users"><i class="icon-input"></i>Пользователи</a></li>

        <%
            }
        %>
        <%

            if (user == null) {
        %>
        <li ripple><a href="/login"><i class="icon-menu"></i>Вход</a></li>
        <%
        } else {
        %>
        <li ripple><a href="/logout"><i class="icon-menu"></i>Выход</a></li>
        <%
            }
        %>
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