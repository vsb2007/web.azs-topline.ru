<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<sec:authorize access="hasRole('ROLE_BID_UPDATE')">
    Контроль остатков<br>
    <div id="control">

    </div>
    <ul class="list">
        <c:forEach items="${bid.getOilStorageIn().getCompanyUnit().getOilTypeStorageArrayList()}" var="storageList">
            <li ripple>
                <span class="item-text">
                        ${storageList.getOilType().getOilTypeName()}
                </span>
                <span class="item-text">
                    <input class="text-input border-green-500" placeholder="Объем литров"
                           value="${storageList.getVolumeV()}"
                           type="number" step="any" required readonly
                           name="oilStorageV_${storageList.getIdOilTypeStorage()}"
                           id="oilStorageV_${storageList.getIdOilTypeStorage()}">
                    <span class="secondary-text">
                        <label for="oilStorageV_${storageList.getIdOilTypeStorage()}">Доступно литров</label>
                    </span>
                </span>
                <span class="item-text">
                    <input class="text-input border-green-500" placeholder="Масса"
                           value="${storageList.getVolumeM()}"
                           type="number" step="any" required readonly
                           name="oilStorageM_${storageList.getIdOilTypeStorage()}"
                           id="oilStorageM_${storageList.getIdOilTypeStorage()}">
                    <span class="secondary-text">
                        <label for="oilStorageM_${storageList.getIdOilTypeStorage()}">Доступно тонн</label>
                    </span>
                </span>
                <%--
                <span class="item-text">
                    <input class="text-input border-green-500" placeholder="Литров отгружено"
                           value="0"
                           type="number" step="any" required readonly
                           name="sumV_${storageList.getOilType().getId_oilType()}"
                           id="sumV_${storageList.getOilType().getId_oilType()}">
                    <span class="secondary-text">
                        <label for="oilStorageM_${storageList.getIdOilTypeStorage()}">Литров отгружено</label>
                    </span>
                </span>
                <span class="item-text">
                    <input class="text-input border-green-500" placeholder="Тонн отгружено"
                           value="0"
                           type="number" step="any" required readonly
                           name="sumM_${storageList.getOilType().getId_oilType()}"
                           id="sumM_${storageList.getOilType().getId_oilType()}">
                    <span class="secondary-text">
                        <label for="oilStorageM_${storageList.getIdOilTypeStorage()}">Тонн отгружено</label>
                    </span>
                </span>
                --%>
            </li>
        </c:forEach>
    </ul>
</sec:authorize>