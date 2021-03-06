<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<c:if test="${bid.getCreateUser().getName().equals(siteUser.getName())
                    || siteUser.getName().equals(\"admin\")
                    }">

    <div class="grid-list">
        <div class="tile">
            <input type="text" class="text-input border-green-500"
                   value="Секция ${bidDetails.getSection().getOilSectionName()} (${bidDetails.getSection().getVol()}л.)"
                   readonly>
            <div>
                <span class="secondary-text">Секция</span>
            </div>
        </div>
        <div class="tile">
            <c:choose>
                <c:when test="${bidDetails.isDone() || bid.getBid_is_freeze()}">
                    <input type="text" class="text-input border-green-500"
                           value="${bidDetails.getOilType().getOilTypeName()}"
                           readonly>
                    <input type="hidden" class="text-input border-green-500"
                           value="${bidDetails.getOilType().getId_oilType()}"
                           name="${bidDetails.getSection().getId_section()}_oilTypeId"
                           readonly>
                </c:when>
                <c:when test="${!bidDetails.isDone() && !bid.getBid_is_freeze()}">
                    <select class="dropdown-menu" id="${bidDetails.getSection().getId_section()}_oilTypeId"
                            name="${bidDetails.getSection().getId_section()}_oilTypeId">
                        <option value="-1">Пустая секция</option>
                        <c:forEach items="${oilTypeList}" var="oilType">
                            <c:choose>
                                <c:when test="${bidDetails.getOilType().getId_oilType().equals(oilType.getId_oilType())}">
                                    <option value="${oilType.getId_oilType()}"
                                            selected>${oilType.getOilTypeName()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${oilType.getId_oilType()}">${oilType.getOilTypeName()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </c:when>
            </c:choose>
            <div>
                <span class="secondary-text">Вид топлива</span>
            </div>
        </div>
        <div class="tile">
            <c:choose>
                <c:when test="${bidDetails.isDone()}">
                    <input type="text" class="text-input border-green-500"
                           value="${bidDetails.getDestination().getCompanyUnitName()}" readonly>
                </c:when>
                <c:otherwise>
                    <select class="dropdown-menu"
                            id="${bidDetails.getSection().getId_section()}_storageOutId"
                            name="${bidDetails.getSection().getId_section()}_storageOutId">
                        <option value="-1">Пункт отгрузки</option>
                        <c:forEach items="${oilStorageList}" var="oilStorage">
                            <c:if test="${oilStorage.getIdOilStorage().equals(bidDetails.getDestination().getIdCompanyUnit())}">
                                <option value="${oilStorage.getIdOilStorage()}"
                                        selected>${oilStorage.getOilStorageName()}</option>
                            </c:if>
                            <c:if test="${!oilStorage.getIdOilStorage().equals(bidDetails.getDestination().getIdCompanyUnit())}">
                                <option value="${oilStorage.getIdOilStorage()}">${oilStorage.getOilStorageName()}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </c:otherwise>
            </c:choose>
            <div>
                <span class="secondary-text">Назначение</span>
            </div>
        </div>
        <div class="tile">
            <c:choose>
                <c:when test="${bidDetails.isDone()}">
                    <input type="text" class="text-input border-green-500"
                           value="${bidDetails.getDestination().getCompanyUnitName()}" readonly>
                </c:when>
                <c:otherwise>
                    <select class="dropdown-menu" style="width: 100px"
                            id="${bidDetails.getSection().getId_section()}_OrgId"
                            name="${bidDetails.getSection().getId_section()}_OrgId"
                            onchange="getSaleBidId('${bidDetails.getSection().getId_section()}')"
                    >
                        <option value="-1">Покупатель</option>
                        <c:forEach items="${organizationList}" var="organization">
                            <c:if test="${organization.getIdOrganization() == bidDetails.getOrganizationDestination().getIdOrganization()}">
                                <option value="${organization.getIdOrganization()}"
                                        selected>${organization.getOrganizationName()}</option>
                            </c:if>
                            <c:if test="${organization.getIdOrganization() != bidDetails.getOrganizationDestination().getIdOrganization()}">
                                <option value="${organization.getIdOrganization()}">
                                        ${organization.getOrganizationName()}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </c:otherwise>
            </c:choose>
            <div>
                <span class="secondary-text">Назначение</span>
            </div>
        </div>
        <div class="tile">
            <c:if test="${bidDetails.getSaleOilId()>0}">
                <span id="span_${bidDetails.getSection().getId_section()}_OrgId">
                    <input type="text" class="text-input border-green-500" value="${bidDetails.getSaleOilId()}" readonly></span>
            </c:if>
            <c:if test="${bidDetails.getSaleOilId()<=0}">
                <span id="span_${bidDetails.getSection().getId_section()}_OrgId"></span>
            </c:if>
            <div>
                <span class="secondary-text">Номер продажи</span>
            </div>
        </div>
    </div>
</c:if>