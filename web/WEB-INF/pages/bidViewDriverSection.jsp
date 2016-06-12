<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<input type="text" class="text-input border-green-500"
       value="Секция ${bidDetails.getSection().getOilSectionName()} (${bidDetails.getSection().getVol()}л.)"
       readonly>&nbsp;
<input type="text" class="text-input border-green-500"
       value="${bidDetails.getOilType().getOilTypeName()}" readonly>&nbsp;
<input type="text" class="text-input border-green-500"
       value="${bidDetails.getDestination().getCompanyUnitName()}" readonly>&nbsp;<br>