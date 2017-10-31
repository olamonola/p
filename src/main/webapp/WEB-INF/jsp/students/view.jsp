<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:layout>
    <jsp:body>
        <h1>Profil studenta. Liczba praktyk: ${totalElements}</h1>
        <c:if test="${not empty msg}">           
            <div class="alert alert-success" role="alert">	<div class="text-center">
                    <span class="glyphicon glyphicon-ok"></span>
                    ${msg}
                </div>
            </div>
        </c:if>
        <div class="panel panel-green">
            <div class="panel-heading">
                <h3 class="panel-title">Dane studenta</h3>
            </div>
            <div class="panel-body">


                <div class="row">
                    <div class="col-md-2">Imię</div>
                    <div class="col-md-10"><b>${account.userProfile.firstName}</b></div>
                </div>
                <div class="row">
                    <div class="col-md-2">Nazwisko</div>
                    <div class="col-md-10"><b>${account.userProfile.lastName}</b></div>
                </div>
                <div class="row">
                    <div class="col-md-2">Numer indeksu</div>
                    <div class="col-md-10"><b>${account.userProfile.indexNo}</b></div>
                </div>
                <div class="row">
                    <div class="col-md-2">Email</div>
                    <div class="col-md-10"><b>${account.email}</b></div>
                </div>
                <div class="row">
                    <div class="col-md-2">Login</div>
                    <div class="col-md-10"><b>${account.login}</b></div>
                </div>
                <div class="row">
                    <div class="col-md-2">Data rejestracji</div>
                    <div class="col-md-10"><b>${account.registrationTime}</b></div>
                </div>
                <div class="row">
                    <div class="col-md-2">Dane potwierdzono</div>
                    <div class="col-md-10"><b><c:choose>
                                <c:when test="${account.accountConfirmed}">
                                    Tak, potwierdzono
                                </c:when>
                                <c:otherwise>
                                    Nie potwierdzono
                                </c:otherwise>
                            </c:choose></b></div>
                </div>
                <div class="row">
                    <div class="col-md-2">Weryfikacja emailowa</div>
                    <div class="col-md-10"><b><c:choose>
                                <c:when test="${account.verified}">
                                    Tak, zweryfikowano emailowo
                                </c:when>
                                <c:otherwise>
                                    Nie zweryfikowano emailowo
                                </c:otherwise>
                            </c:choose></b></div>
                </div>
                <div class="row">
                    <div class="col-md-2">Uprawnienia studenta</div>
                    <div class="col-md-10">
                        <c:choose>
                            <c:when test="${account.accountRoleCollection[0].roleId.roleId == 3}">
                                <b> Posiada uprawnienia studenta</b><br>
                                ważne od <b>${account.accountRoleCollection[0].roleStartTime}</b>
                                do <b>${account.accountRoleCollection[0].roleEndTime}</b>
                            </c:when>
                            <c:otherwise>
                                <b> Nie posiada uprawnień studenta</b>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">Konto aktywne</div>
                    <div class="col-md-10"><b>
                            <c:choose>
                                <c:when test="${account.active}">
                                    Tak
                                </c:when>
                                <c:otherwise>
                                    Nie
                                </c:otherwise>
                            </c:choose>
                        </b></div>
                </div>
            </div>
        </div>


        <c:forEach var="element" items="${elements}">
            <div class="panel panel-blue">
                <div class="panel-heading">
                    <h3 class="panel-title">Praktyka</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-2">Ocena</div>
                        <div class="col-md-10"><b>
                                <c:choose>
                                    <c:when test="${not empty element.grade}">
                                        ${element.grade}
                                    </c:when>
                                    <c:otherwise>
                                        Brak
                                    </c:otherwise>
                                </c:choose>
                            </b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Rok studiów</div>
                        <div class="col-md-10"><b>${element.year}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Data rozpoczęcia</div>
                        <div class="col-md-10"><b><fmt:formatDate value="${element.startDate}" pattern="dd-MM-yyyy" /></b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Data zakończenia</div>
                        <div class="col-md-10"><b><fmt:formatDate value="${element.endDate}" pattern="dd-MM-yyyy" /></b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Potwierdzenie praktyki</div>
                        <div class="col-md-10"><b>
                                <c:choose>
                                    <c:when test="${element.confirmation}">
                                        Tak
                                    </c:when>
                                    <c:otherwise>
                                        Nie
                                    </c:otherwise>
                                </c:choose></b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Zwolnienie z praktyk</div>
                        <div class="col-md-10"><b>
                                <c:choose>
                                    <c:when test="${element.exemption}">
                                        Tak
                                    </c:when>
                                    <c:otherwise>
                                        Nie
                                    </c:otherwise>
                                </c:choose></b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Dodanie praktyki</div>
                        <div class="col-md-10"><b><fmt:formatDate value="${element.created}" pattern="dd-MM-yyyy" /></b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Ostatnia edycja</div>
                        <div class="col-md-10"><b><fmt:formatDate value="${element.lastEdited}" pattern="dd-MM-yyyy" /></b></div>
                    </div>
                    <hr class="divider">
                    <div class="row">
                        <div class="col-md-2">Nazwa firmy</div>
                        <div class="col-md-10"><b>${element.companyName}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Ulica</div>
                        <div class="col-md-10"><b>${element.companyStreet}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Numer</div>
                        <div class="col-md-10"><b>${element.companyStreetNo}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Miasto</div>
                        <div class="col-md-10"><b>${element.companyCity}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Kod pocztowy</div>
                        <div class="col-md-10"><b>${element.companyZip}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Telefon</div>
                        <div class="col-md-10"><b>${element.companyPhone}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Właściciel/Dyrektor</div>
                        <div class="col-md-10"><b>${element.companyOwnerFirstName} ${element.companyOwnerLastName}<c:if test="${not empty element.companyOwnerPosition}">, ${element.companyOwnerPosition}</c:if></b></div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">Opiekun zakładowy</div>
                            <div class="col-md-10"><b>${element.companyInternshipAdministratorFirstName} ${element.companyInternshipAdministratorLastName}</b></div>
                    </div>
                    <hr class="divider">
                    <c:if test = "${not empty element.comments}">
                        <div class="row">
                            <div class="col-md-2">Komentarz</div>
                            <div class="col-md-10"><b>${element.comments}</b></div>
                        </div>
                    </c:if>
                    <div class="row">
                        <div class="col-md-2">Archiwizowana</div>
                        <div class="col-md-10"><b>
                                <c:choose>
                                    <c:when test="${element.archived}">
                                        Tak
                                    </c:when>
                                    <c:otherwise>
                                        Nie
                                    </c:otherwise>
                                </c:choose>
                            </b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Kierunek</div>
                        <div class="col-md-10"><b>${element.facultyId.name}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Typ praktyk</div>
                        <div class="col-md-10"><b>${element.internshipTypeId.type}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">Specjalność</div>
                        <div class="col-md-10"><b>${element.specializationId.name}</b></div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">System studiów</div>
                        <div class="col-md-10"><b>${element.systemSystemId.name}</b></div>
                    </div>
                </div>

            </div>


        </c:forEach>


        <%--Paginacja --%>
        <c:if test="${endIndex >1 }">                                            
            <c:url var="firstUrl" value="/Studenci/profil/${account.accountId}/1" />
            <c:url var="lastUrl" value="/Studenci/profil/${account.accountId}/${thePage.totalPages}" />
            <c:url var="prevUrl" value="/Studenci/profil/${account.accountId}/${currentIndex - 1}" />
            <c:url var="nextUrl" value="/Studenci/profil/${account.accountId}/${currentIndex + 1}" />

            <div class="text-center">
                <ul class="pagination">
                    <c:choose>
                        <c:when test="${currentIndex == 1}">
                            <li class="disabled"><a href="#">&lt;&lt;</a></li>
                            <li class="disabled"><a href="#">&lt;</a></li>
                            </c:when>
                            <c:otherwise>
                            <li><a href="${firstUrl}">&lt;&lt;</a></li>
                            <li><a href="${prevUrl}">&lt;</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
                            <c:url var="pageUrl" value="/Studenci/profil/${account.accountId}/${i}" />
                            <c:choose>
                                <c:when test="${i == currentIndex}">
                                <li class="active"><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${currentIndex == thePage.totalPages}">
                            <li class="disabled"><a href="#">&gt;</a></li>
                            <li class="disabled"><a href="#">&gt;&gt;</a></li>
                            </c:when>
                            <c:otherwise>
                            <li><a href="${nextUrl}">&gt;</a></li>
                            <li><a href="${lastUrl}">&gt;&gt;</a></li>
                            </c:otherwise>
                        </c:choose>
                </ul>
            </div>
        </c:if>
        <%--Paginacja KONIEC--%>
        <a class="btn btn-default" href="<c:url
               value="/Studenci/lista/1"/>">
            Powrót do listy studentów
        </a>
    </jsp:body>
</t:layout>