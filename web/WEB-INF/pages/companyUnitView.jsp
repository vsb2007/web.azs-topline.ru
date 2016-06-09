<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_COMPANY_RED')">
    <div class="section">
        <form action="companyUnitRed" method="post">
            <ul class="list">
                <li ripple>
                <span class="item-text">
                <input type="text" class="text-input border-green-500" placeholder="Имя организации"
                       value="${companyUnit.getCompanyUnitName()}"
                       id="companyUnitName" name="companyUnitName">
                    <input type="hidden" name="companyUnitId" id="companyUnitId"
                           value="${companyUnit.getIdCompanyUnit()}">
                <span class="secondary-text">
                <label for="companyUnitName">Имя подразделения</label>
                </span>
                </span>
                </li>
            </ul>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="button raised bg-blue-500 color-white">Редактировать</button>
        </form>
        <p class="text">${errorCompanyUnitRed}</p>
    </div>
    <div class="section">
        <form action="companyUnitAddOilStorage" method="post">
            <select class="dropdown-menu" id="" name="oilType" onchange="">
                <option value="-1">Выберите тип топлива</option>
                <c:forEach items="${oilTypeList}" var="oilType">
                    <option value="${oilType.getId_oilType()}">${oilType.getOilTypeName()}</option>
                </c:forEach>
            </select><br>
            <input type="hidden" name="companyUnitId" value="${companyUnit.getIdCompanyUnit()}"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="button raised bg-blue-500 color-white">Поставить на контроль</button>
        </form>
        <p class="text">${addOilStorageMessage}</p>
    </div>
    <c:if test="${companyUnit.getOilTypeStorageArrayList()!=null && companyUnit.getOilTypeStorageArrayList().size()!=0}">
        <div class="section">
            <form action="" method="post">
                <ul class="list">
                    <c:forEach items="${companyUnit.getOilTypeStorageArrayList()}" var="storageList">
                        <li ripple>
                            <span class="item-text">
                                    ${storageList.getOilType().getOilTypeName()}
                            </span>
                        <span class="item-text">
                            <input type="text" class="text-input border-green-500" placeholder="Объем литров"
                                   value="${storageList.getVolumeV()}"
                                   name="oilStorageV">
                            <span class="secondary-text">
                                <label for="companyUnitName">литры</label>
                            </span>
                        </span>
                        <span class="item-text">
                                <input type="text" class="text-input border-green-500" placeholder="Масса"
                                       value="${storageList.getVolumeM()}"
                                       name="oilStorageM">
                            <span class="secondary-text">
                                <label for="companyUnitName">масса</label>
                            </span>
                        </span>
                        </li>
                    </c:forEach>
                </ul>
                <input type="hidden" name="oilStorageId" value="${storageList.getIdOilTypeStorage()}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="button raised bg-blue-500 color-white">Редактировать</button>
            </form>
            <p class="text">${errorCompanyUnitRed}</p>
        </div>
    </c:if>
</sec:authorize>
<%@ include file="footer.jsp" %>
