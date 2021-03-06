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
        <c:when test="${bidDetails.getDateIn()!=null}">
            <c:set var="valT" value="${bidDetails.getTempIn()}" scope="application"/>
            <c:set var="valP" value="${bidDetails.getPlIn()}" scope="application"/>
            <c:set var="valV" value="${bidDetails.getVolumeIn()}" scope="application"/>
            <c:set var="valM" value="${bidDetails.getMassIn()}" scope="application"/>
        </c:when>
        <c:otherwise>
            <c:set var="valT" value="${bidDetails.getTempIn()}" scope="application"/>
            <c:set var="valP" value="${bidDetails.getPlIn()}" scope="application"/>
            <c:set var="valV" value="${bidDetails.getSection().getVol()}" scope="application"/>
            <c:set var="valM" value="${bidDetails.getMassIn()}" scope="application"/>
        </c:otherwise>
    </c:choose>
    <c:if test="${bid.getBid_is_freeze()!=null && bid.getBid_is_freeze() && !bid.isDone()}">
        <form action="bidUpdate" method="post">
        <input type="hidden" required name="bidNumber" value="${bid.getName()}" readonly>
        <input type="hidden" required name="oilStorageIn" value="${bid.getOilStorageIn().getOilStorageName()}" readonly>
        <input type="hidden" required name="driver" value="${bid.getDriver().getDriverFio()}" readonly>
        <input type="hidden" required name="car" value="${bid.getCar().getCar_name()}" readonly>
        <input type="hidden" required name="trailer" value="${bid.getTrailer().getTrailer_number()}" readonly>
    </c:if>
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
            <input type="text" class="text-input border-green-500"
                   value="${bidDetails.getOilType().getOilTypeName()}" readonly>
            <div>
                <span class="secondary-text">Вид топлива</span>
            </div>
        </div>
        <c:if test="${bidDetails.getDestination()!=null}">
        <div class="tile">
            <input type="text" class="text-input border-green-500"
                   value="${bidDetails.getDestination().getCompanyUnitName()}" readonly>
            <input type="hidden" name="${bidDetails.getSection().getId_section()}_destination" value="${bidDetails.getDestination().getIdCompanyUnit()}">
            <input type="hidden" name="orgType" value="0">
            <div>
                <span class="secondary-text">Назначение</span>
            </div>
        </div>
        </c:if>
        <c:if test="${bidDetails.getDestination()==null}">
            <div class="tile">
                <input type="text" class="text-input border-green-500"
                       value="${bidDetails.getOrganizationDestination().getOrganizationName()}" readonly>
                <input type="hidden" name="${bidDetails.getSection().getId_section()}_destination" value="${bidDetails.getOrganizationDestination().getIdOrganization()}">
                <input type="hidden" name="orgType" value="1">
                <div>
                    <span class="secondary-text">Назначение</span>
                </div>
            </div>
        </c:if>
        <div class="tile">
            <input class="text-input border-green-500" placeholder="0" required
                   name="${bidDetails.getSection().getId_section()}_volume"
                   id="${bidDetails.getSection().getId_section()}_volume"
                   value="${valV}" type="number" step="any"
                   onchange="onVolumeChange('${bidDetails.getSection().getId_section()}_volume',
                           '${bidDetails.getSection().getId_section()}_mass',
                           '${bidDetails.getSection().getId_section()}_p')"
                ${readonlyTmp}>
            <div>
                <span class="secondary-text">Литры</span>
            </div>
        </div>
        <div class="tile">
            <input class="text-input border-green-500" placeholder="0" required
                   name="${bidDetails.getSection().getId_section()}_p"
                   id="${bidDetails.getSection().getId_section()}_p"
                   value="${valP}" type="number" step="any"
                   onchange="onPChange('${bidDetails.getSection().getId_section()}_volume',
                           '${bidDetails.getSection().getId_section()}_mass',
                           '${bidDetails.getSection().getId_section()}_p')"
                ${readonlyTmp}>
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
                   id="${bidDetails.getSection().getId_section()}_mass"
                   value="${valM}" type="number" step="any"
                   onchange="onMassChange('${bidDetails.getSection().getId_section()}_volume',
                           '${bidDetails.getSection().getId_section()}_mass',
                           '${bidDetails.getSection().getId_section()}_p')"
                ${readonlyTmp}>
            <div>
                <span class="secondary-text">Масса</span>
            </div>
        </div>
        <br>
        <c:choose>
            <c:when test="${!bidDetails.isDone() && bid.getBid_is_freeze()!=null
                                && bid.getBid_is_freeze()}">
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
    </div>
    <c:if test="${bid.getBid_is_freeze()!=null && bid.getBid_is_freeze() && !bid.isDone()}">
        </form>
    </c:if>
</c:if>