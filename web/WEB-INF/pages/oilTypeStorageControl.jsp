<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<sec:authorize access="hasRole('ROLE_BID_UPDATE')">
    Контроль остатков<br>
    <ul class="list">
        <c:forEach items="${companyUnit.getOilTypeStorageArrayList()}" var="storageList">
            <li ripple>
                <span class="item-text">
                        ${storageList.getOilType().getOilTypeName()}
                </span>
                <span class="item-text">
                    <input class="text-input border-green-500" placeholder="Объем литров"
                           value="${storageList.getVolumeV()}"
                           type="number" step="any" required
                           name="oilStorageV_${storageList.getIdOilTypeStorage()}"
                           id="oilStorageV_${storageList.getIdOilTypeStorage()}">
                    <span class="secondary-text">
                        <label for="oilStorageV_${storageList.getIdOilTypeStorage()}">литры</label>
                    </span>
                    <input value="${storageList.getVolumeV()}"
                           type="hidden" step="any" required
                           name="oilStorageVOld_${storageList.getIdOilTypeStorage()}"
                           id="oilStorageVOld_${storageList.getIdOilTypeStorage()}">
                </span>
                <span class="item-text">
                    <input class="text-input border-green-500" placeholder="Масса"
                           value="${storageList.getVolumeM()}"
                           type="number" step="any" required
                           name="oilStorageM_${storageList.getIdOilTypeStorage()}"
                           id="oilStorageM_${storageList.getIdOilTypeStorage()}">
                    <span class="secondary-text">
                        <label for="oilStorageM_${storageList.getIdOilTypeStorage()}">масса</label>
                    </span>
                    <input value="${storageList.getVolumeM()}"
                           type="hidden" step="any" required
                           name="oilStorageMOld_${storageList.getIdOilTypeStorage()}"
                           id="oilStorageMOld_${storageList.getIdOilTypeStorage()}">
                </span>
            </li>
        </c:forEach>
    </ul>
</sec:authorize>