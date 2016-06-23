<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<c:if test="${(siteUser.getCompanyUnit()!=null
                    && siteUser.getCompanyUnit().getIdCompanyUnit()==bid.getOilStorageIn().getIdOilStorage())
        || (
            siteUser.getPost()!=null && siteUser.getPost().getIdPost()==2 && bid.getDriver()!=null
            && bid.getDriver().getIdDriver() == siteUser.getId()
        )
        }">
    <c:set var="valT" value="" scope="application"/>
    <c:set var="valP" value="" scope="application"/>
    <c:choose>
        <c:when test="${bidDetails.getDateIn()!=null && !bidDetails.getDateIn().equals(\"\")}">
            <c:set var="valT" value="${bidDetails.getTempIn()}" scope="application"/>
            <c:set var="valP" value="${bidDetails.getPlIn()}" scope="application"/>
            <c:set var="valV" value="${bidDetails.getVolumeIn()}" scope="application"/>
            <c:set var="valM" value="${bidDetails.getMassIn()}" scope="application"/>
        </c:when>
        <c:otherwise>
            <c:set var="valT" value="" scope="application"/>
            <c:set var="valP" value="" scope="application"/>
            <c:set var="valV" value="${bidDetails.getSection().getVol()}" scope="application"/>
            <c:set var="valM" value="" scope="application"/>
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
            <input type="text" class="text-input border-green-500"
                   value="${bidDetails.getOilType().getOilTypeName()}" readonly>
            <div>
                <span class="secondary-text">Вид топлива</span>
            </div>
        </div>
        <div class="tile">
            <input type="text" class="text-input border-green-500"
                   value="${bidDetails.getDestination().getCompanyUnitName()}" readonly>
            <div>
                <span class="secondary-text">Назначение</span>
            </div>
        </div>
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
                   readonly>
            <div>
                <span class="secondary-text">Масса</span>
            </div>
        </div>
    </div>
</c:if>