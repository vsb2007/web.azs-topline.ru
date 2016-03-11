<%@ page import="io.bgroup.topline.model.SiteUser" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="io.bgroup.topline.model.RolesUrl" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>
<sec:authorize access="hasRole('ROLE_LINKS')">

<div class="section">
    <form action="/roles" method="post">
        <input type="text" class="text-input border-green-500" placeholder="Link" required name="linkUrl"
               id="linkUrl"> &nbsp;&nbsp;
        <input type="text" class="text-input border-green-500" placeholder="Description" required name="linkDescription"
               id="linkDescription">
        <br>
        <input type="hidden" name="addLinkUrlForm" id="addLinkUrlForm" value="1">
        <button class="button raised bg-blue-500 color-white" id="addLinkUrlButton"
                name="addLinkUrlButton">Добавить URL
        </button>
    </form>
    <%
        String errorAddUrl = (String) request.getAttribute("errorAddUrl");
        if (errorAddUrl != null && !errorAddUrl.equals("")) {
    %>
    <p class="text"><%=errorAddUrl%>
    </p>
    <%
        }
        if (errorAddUrl == null) {
            String addLinkUrlMessage = (String) request.getAttribute("addLinkUrlMessage");
            if (addLinkUrlMessage != null) {
    %>
                <span class="secondary-text">
				    <label for="addLinkUrlButton" class="label color-green-500"><%=addLinkUrlMessage%>
                    </label>
			</span>
    <%
            }
        }
    %>
</div>

<div class="section">
    <h2>Доступные линки</h2>
    <ul class="list">
        <%
            ArrayList<RolesUrl> rolesUrlsList = (ArrayList<RolesUrl>) request.getAttribute("linkUrlList");
            if (rolesUrlsList != null) {
                for (RolesUrl RolesUrl : rolesUrlsList) {
        %>
        <li ripple>
            <form action="/permissions" method="post">
                <input type="hidden" id="permissions-red-label" value="1" name="permissions-red-label">
                <input value="<%=RolesUrl.getId()%>" name="buttonPermissionRed" id="buttonPermissionRed" type="hidden">
                <%
                    if (RolesUrl.getIsBlock().equals("0")) {
                %>
                <button class="button raised color-white bg-blue-500" type="submit" style="width: 15em;">
                        <%
                    }
                    else {
                %>
                    <button class="button raised color-white bg-grey-500" type="submit" style="width: 15em;">
                        <%
                            }
                        %>
		<span class="item-text">
			<%=RolesUrl.getUrl()%>
			<span class="secondary-text">
				<%
                    if (RolesUrl.getDescription() != null) {
                %>
                    <%=RolesUrl.getDescription()%>
                <%
                    }
                %>
			</span>
		</span>
                    </button>
            </form>
        </li>
        <%
            }
        %>
    </ul>
    <%
        }
    %>
</div>

</sec:authorize>
<sec:authorize access="!hasRole('ROLE_LINKS')">
    Нужна авторизация
</sec:authorize>

<%@ include file="footer.jsp" %>
