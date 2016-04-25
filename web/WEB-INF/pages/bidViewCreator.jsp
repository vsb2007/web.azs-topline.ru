<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<sec:authorize access="hasRole('ROLE_BID_VIEW')">
    <div class="section">
            ${message}
        Creator
    </div>
    <div class="section">
            <%-- <sec:authorize access="!hasRole('ROLE_BID_RED')"> --%>
        Загрузка:<br>
        <c:set var="readonlyTmp" value="" scope="application"/>
        <c:if test="${!bid.getBid_is_freeze().equals(\"0\") && siteUser.getCompanyUnit()!=null
                    && siteUser.getCompanyUnit().getIdCompanyUnit().equals(bid.getOilStorageIn().getIdOilStorage())
                    && !siteUser.getName().equals(\"admin\")
                    }">
            <c:set var="readonlyTmp" value="readonly" scope="application"/>
        </c:if>
        <c:if test="${bid.getBid_is_freeze() !=null}">
            <sec:authorize access="hasRole('ROLE_BID_UPDATE')">
                <c:if test="${!readonlyTmp.equals(\"readonly\") && bid.getBid_is_freeze()!=null
                                && bid.getBid_is_freeze().equals(\"0\")}">
                    <form action="bidUpdate" method="post">
                </c:if>
            </sec:authorize>
            <c:if test="${bid.getBid_is_freeze().equals(\"0\")}">
                <c:set var="freeze" value="in" scope="application"/>
                <c:set var="submitButtonValue" value="Отпустить топливо" scope="application"/>
            </c:if>
            <c:if test="${!bid.getBid_is_freeze().equals(\"0\")}">
                <c:set var="freeze" value="out" scope="application"/>
                <c:set var="submitButtonValue" value="Принять топливо" scope="application"/>
            </c:if>
            <input type="text" class="text-input border-green-500" placeholder="Заявка (Номер)" required
                   name="bidNumber" value="${bid.getName()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Точка загрузки" required
                   name="oilStorageIn" value="${bid.getOilStorageIn().getOilStorageName()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Водитель" required
                   name="driver" value="${bid.getDriver().getDriverFio()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Машина" required
                   name="car" value="${bid.getCar().getCar_name()}" readonly> <br>
            <input type="text" class="text-input border-green-500" placeholder="Прицеп" required
                   name="trailer" value="${bid.getTrailer().getTrailer_number()}" readonly> <br>
            <br>
            Доставка:<br>
            Секции на машине:<br>
            <c:forEach items="${bidDetailsCar}" var="bidDetails">
                <c:if test="${bid.getCreateUser().getName().equals(siteUser.getName())
                    || siteUser.getName().equals(\"admin\")
                    }">
                    <c:choose>
                        <c:when test="${bidDetails.getDateOut()!=null}">
                            <c:set var="valT" value="${bidDetails.getTempOut()}" scope="application"/>
                            <c:set var="valT" value="${bidDetails.getTempOut()}" scope="application"/>
                            <c:set var="valP" value="${bidDetails.getPlOut()}" scope="application"/>
                            <c:set var="valV" value="${bidDetails.getVolumeOut()}" scope="application"/>
                            <c:set var="valM" value="${bidDetails.getMassOut()}" scope="application"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="valT" value="${bidDetails.getTempIn()}" scope="application"/>
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
                                    <input type="button" class="button raised bg-blue-500 color-white"
                                           value="${submitButtonValue}">
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
                </c:if>

            </c:forEach>
            Секции на прицепе:<br>
            <c:forEach items="${bidDetailsTrailer}" var="bidDetails">
                <c:if test="${bid.getCreateUser().getName().equals(siteUser.getName())
                    || siteUser.getName().equals(\"admin\")
                    }">
                    <c:choose>
                        <c:when test="${bidDetails.getDateOut()!=null}">
                            <c:set var="valT" value="${bidDetails.getTempOut()}" scope="application"/>
                            <c:set var="valT" value="${bidDetails.getTempOut()}" scope="application"/>
                            <c:set var="valP" value="${bidDetails.getPlOut()}" scope="application"/>
                            <c:set var="valV" value="${bidDetails.getVolumeOut()}" scope="application"/>
                            <c:set var="valM" value="${bidDetails.getMassOut()}" scope="application"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="valT" value="${bidDetails.getTempIn()}" scope="application"/>
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
                                    <input type="button" class="button raised bg-blue-500 color-white"
                                           value="${submitButtonValue}">
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
                </c:if>
            </c:forEach>

            <sec:authorize access="hasRole('ROLE_BID_UPDATE')">
                <c:if test="${!readonlyTmp.equals(\"readonly\") && bid.getBid_is_freeze()!=null
                                && bid.getBid_is_freeze().equals(\"0\")}">
                    <button class="button raised bg-blue-500 color-white">${submitButtonValue}</button>
                    <input type="hidden" name="bidId" value="${bid.getId_bid()}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="token"/>
                    </form>
                </c:if>
            </sec:authorize>
        </c:if>
            <%-- </sec:authorize> --%>
    </div>
    <script src="js/bidCreate.js"></script>
</sec:authorize>
<%@ include file="footer.jsp" %>
