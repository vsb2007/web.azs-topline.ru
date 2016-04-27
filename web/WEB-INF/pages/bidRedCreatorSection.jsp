<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<c:if test="${bid.getCreateUser().getName().equals(siteUser.getName())
                    || siteUser.getName().equals(\"admin\")
                    }">
    <c:choose>
        <c:when test="${bidDetails.getDateOut()!=null}">
            <c:set var="valT" value="${bidDetails.getTempOut()}" scope="application"/>
            <c:set var="valP" value="${bidDetails.getPlOut()}" scope="application"/>
            <c:set var="valV" value="${bidDetails.getVolumeOut()}" scope="application"/>
            <c:set var="valM" value="${bidDetails.getMassOut()}" scope="application"/>
        </c:when>
        <c:otherwise>
            <c:set var="valT" value="${bidDetails.getTempIn()}" scope="application"/>
            <c:set var="valP" value="${bidDetails.getPlIn()}" scope="application"/>
            <c:set var="valV" value="${bidDetails.getVolumeIn()}" scope="application"/>
            <c:set var="valM" value="${bidDetails.getMassIn()}" scope="application"/>
        </c:otherwise>
    </c:choose>
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
                <c:when test="${bidDetails.isDone() || !bid.getBid_is_freeze().equals(\"0\")}">
                    <input type="text" class="text-input border-green-500"
                           value="${bidDetails.getOilType().getOilTypeName()}"
                           readonly>
                </c:when>
                <c:when test="${!bidDetails.isDone() && bid.getBid_is_freeze().equals(\"0\")}">
                    <select class="dropdown-menu" id="${bidDetails.getSection().getId_section()}_oilTypeId"
                            name="${bidDetails.getSection().getId_section()}_oilTypeId">
                        <option value=\"-1\">Пустая секция</option>
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
                    <select class="dropdown-menu" id="oilStorage"
                        id="${bidDetails.getSection().getId_section()}_storageOutId"
                        name="${bidDetails.getSection().getId_section()}_storageOutId" >
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
            <%-- <div class="tile">
                <input class="text-input border-green-500" placeholder="0" required
                       name="${bidDetails.getSection().getId_section()}_volume"
                       id="${bidDetails.getSection().getId_section()}_volume"
                       value="${valV}" type="number" step="any"  ${readonlyTmp}>
                <div>
                    <span class="secondary-text">Литры</span>
                </div>
            </div>
            <div class="tile">
                <input class="text-input border-green-500" placeholder="0" required
                       name="${bidDetails.getSection().getId_section()}_p"
                       value="${valP}" type="number" step="any"  ${readonlyTmp}>
                <div>
                    <span class="secondary-text">Плотность</span>
                </div>
            </div>
            <div class="tile">
                <input class="text-input border-green-500" placeholder="0" required
                       name="${bidDetails.getSection().getId_section()}_t"
                       value="${valT}" type="number" step="any"  ${readonlyTmp}>
                <div>
                    <span class="secondary-text">Температура</span>
                </div>
            </div>
            <div class="tile">
                <input class="text-input border-green-500" placeholder="0" required
                       name="${bidDetails.getSection().getId_section()}_mass"
                       value="${valM}" type="number" step="any"  ${readonlyTmp}>
                <div>
                    <span class="secondary-text">Масса</span>
                </div>
            </div>
            <br>
            <c:choose>
                <c:when test="${!bidDetails.isDone() && bid.getBid_is_freeze()!=null
                                    && !bid.getBid_is_freeze().equals(\"0\")}">
                    <div class="tile">
                        <button class="button raised bg-blue-500 color-white">${submitButtonValue}</button>
                        <input type="hidden" name="bidId" value="${bid.getId_bid()}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
                    </div>
                </c:when>
                <c:when test="${bidDetails.isDone()}">
                    <div class="tile">
                        <input type="button" class="button raised bg-green-500 color-white"
                               value="Доставлено">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="tile">
                    </div>
                </c:otherwise>
            </c:choose>
            --%>
    </div>
</c:if>