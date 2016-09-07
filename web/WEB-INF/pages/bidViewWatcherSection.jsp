<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<input type="text" class="text-input border-green-500"
       value="Секция ${bidDetails.getSection().getOilSectionName()} (${bidDetails.getSection().getVol()}л.)"
       readonly>&nbsp;
<input type="text" class="text-input border-green-500"
       value="${bidDetails.getOilType().getOilTypeName()}" readonly>&nbsp;
<c:if test="${bidDetails.getDestination()!=null}">
    <input type="text" class="text-input border-green-500"
           value="${bidDetails.getDestination().getCompanyUnitName()}" readonly>
</c:if>
<c:if test="${bidDetails.getDestination()==null}">
    <input type="text" class="text-input border-green-500"
           value="${bidDetails.getOrganizationDestination().getOrganizationName()}" readonly>
</c:if>&nbsp;
<input type="text" class="text-input border-green-500"
       value="${bidDetails.getDateIn()}" placeholder="Время загрузки" readonly>&nbsp;
<input type="text" class="text-input border-green-500"
       value="${bidDetails.getDateOut()}" placeholder="Время слива" readonly>&nbsp;<br>
