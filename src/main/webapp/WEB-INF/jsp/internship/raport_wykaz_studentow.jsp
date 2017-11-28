<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<t:layout>
    <jsp:body>
        <h1>Wykaz studentów</h1>
        <c:if test="${not empty errormsg}">           
            <div class="alert alert-danger" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-remove"></span>
                    ${errormsg}
                </div>
            </div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="alert alert-success" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-ok"></span>
                    ${msg}
                </div>
            </div>
        </c:if>

        <div class="panel panel-search">
            <div class="panel-heading">
                <h3 class="panel-title"><span class="glyphicon glyphicon-search"></span>Opcje wyszukiwania danych</h3>
            </div>
            <div class="panel-body">
                <form:form method="post" modelAttribute="wykazSearch" class="form-horizontal">

                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/typ_praktyk.png"/>" style="width: 100px;"/></span>
                            <form:select path="internshipType" class="form-control" id="internshipType">
                                <c:forEach items="${internshipTypesForReport}" var="type">
                                    <form:option value="${type.internshipTypeId}" >${type.type}</form:option>
                                </c:forEach>
                            </form:select>
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/rok_akademicki.png"/>" style="width: 100px;"/></span>
                            <form:select path="schoolYear" class="form-control" id="schoolYear">
                                <c:forEach items="${allSchoolYearsForReport}" var="year">
                                    <form:option value="${year}" >${year}/${year+1}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/systemy.png"/>" style="width: 100px;"/></span>
                            <form:select path="system" class="form-control" id="system">
                                <c:forEach items="${systems}" var="system">
                                    <form:option value="${system.systemId}" >${system.name}</form:option>
                                </c:forEach>
                            </form:select>
                    </div>

                    <div class="text-center">
                        <form:button type="submit" class="btn btn-info" name="search" value="search">
                            <span class="glyphicon glyphicon-search"></span> Szukaj
                        </form:button> 
                    </div>
                </form:form> 
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><span class="glyphicon glyphicon-file"></span>Generuj PDF lub pobierz plik CSV</h3>
            </div>
            <div class="panel-body">
                <form:form method="post" modelAttribute="wykazSearch" class="form-horizontal">
                    Wybierz specjalności, które zostaną wpisane do dokumentu:<br/>
                    <c:set var="i" value="${0}" scope="request" />
                    <c:forEach var="specjalizacja" items="${allSpecializations}" varStatus="varStat">
                        <input type="checkbox" name="specializations[${varStat.index}]" id="selectedSpecializations${i}" value="${specjalizacja.name}" /> ${specjalizacja.name}<br>
                        <c:set var="i" value="${i = i+1}" scope="request" />
                    </c:forEach>
                    <t:cinput path="teacher" label="Opiekun praktyk*" placeholder="Imię Nazwisko"/>
                    <div class="text-center">
                        <form:button type="submit" class="btn btn-primary" name="generatePDF" value="generatePDF">
                            <span class="glyphicon glyphicon-file"></span> Generuj PDF
                        </form:button> 
                        <form:button type="submit" class="btn btn-primary" name="export" value="export">
                            <span class="glyphicon glyphicon-save-file"></span> Pobierz CSV
                        </form:button> 
                    </div>
                </form:form>
            </div>
        </div>



        <c:choose>
            <c:when test="${empty elements}">
                <div role="alert">			
                    <strong>Nie znaleziono żadnych użytkowników.</strong>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-hover table-striped table-responsive">
                    <thead>
                        <tr>
                            <th>Lp.</th>
                            <th>Imię i nazwisko studenta</th>
                            <th>Nazwa instytucji przyjmującej na praktyki, telefon kontaktowy</th>
                            <th>Imię i nazwisko opiekuna praktyk wyznaczonego przez instytucję</th>
                            <th>Termin praktyki od - do
                            </th>
                        </tr>

                    </thead>
                    <tbody>
                        <c:set var="lp" value="${currentIndex * 10 -10}" scope="request" />
                        <c:forEach var="element" items="${elements}" varStatus="vs">
                            <tr>
                                <td>
                                    ${lp = lp + 1}
                                </td>
                                <td>
                                    <a class="btn btn-link" href="<c:url
                                           value="/Studenci/profil/${element.accountId.accountId}/1"/>">
                                       ${element.accountId.firstName} ${element.accountId.lastName}
                                    </a>
                                </td>
                                <td>
                                    <a class="btn btn-link" href="<c:url
                                           value="/Firma/widok/${element.companyId.companyId}"/>">
                                       ${element.companyName}
                                       <c:if test="${not empty element.companyId.phone}">
                                           , ${element.companyId.phone}
                                       </c:if>
                                    </a>
                                </td>
                                <td>
                                    ${element.companyInternshipAdministratorFirstName}
                                    ${element.companyInternshipAdministratorLastName}
                                </td>
                                <td>
                                    <%-- Button trigger modal szczegóły --%>
                                    <a class="btn btn-link" data-toggle="modal" data-target="#myModal${vs.index}" id="viewDetailButton${vs.index}">
                                        <fmt:formatDate value="${element.startDate}" pattern="dd-MM-yyyy" /> - 
                                        <fmt:formatDate value="${element.endDate}" pattern="dd-MM-yyyy" />
                                    </a>

                                </td>
                            </tr>
                            <%-- Modal szczegółów --%>
                        <div class="modal fade" id="myModal${vs.index}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <div class="text-center">
                                            <h3 class="modal-title" id="myModalLabel"><b>${fn:toUpperCase(element.accountId.firstName)} ${fn:toUpperCase(element.accountId.lastName)}</b></h3>
                                        </div>
                                    </div>
                                    <div class="modal-body">
                                        <div class="container-fluid">
                                            <div class="row">
                                                <div class="col-md-4">Student:</div>
                                                <div class="col-md-8"><b>${element.accountId.firstName} ${element.accountId.lastName}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Rok studiów:</div>
                                                <div class="col-md-8"><b>${element.year}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Rozpoczęcie praktyk:</div>
                                                <div class="col-md-8"><b><fmt:formatDate value="${element.startDate}" pattern="dd-MM-yyyy" /></b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Zakończenie praktyk:</div>
                                                <div class="col-md-8"><b><fmt:formatDate value="${element.endDate}" pattern="dd-MM-yyyy" /></b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Potwierdzenie:</div>
                                                <div class="col-md-8"><b>
                                                        <c:choose>
                                                            <c:when test="${element.confirmation}">
                                                                Tak
                                                            </c:when>
                                                            <c:otherwise>
                                                                Nie
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Zwolnienie:</div>
                                                <div class="col-md-8"><b>
                                                        <c:choose>
                                                            <c:when test="${element.exemption}">
                                                                Tak
                                                            </c:when>
                                                            <c:otherwise>
                                                                Nie
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Ocena:</div>
                                                <div class="col-md-8"><b>
                                                        <c:choose>
                                                            <c:when test="${not empty element.grade}">
                                                                ${element.grade}
                                                            </c:when>
                                                            <c:otherwise>
                                                                Brak oceny
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Dodanie praktyki:</div>
                                                <div class="col-md-8"><b><fmt:formatDate value="${element.created}" pattern="dd-MM-yyyy" /></b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Ostatnia edycja:</div>
                                                <div class="col-md-8"><b><fmt:formatDate value="${element.lastEdited}" pattern="dd-MM-yyyy" /></b></div>
                                            </div>
                                            <hr class="divider">
                                            <div class="row">
                                                <div class="col-md-4">Nazwa firmy:</div>
                                                <div class="col-md-8"><b>${element.companyName}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Ulica:</div>
                                                <div class="col-md-8"><b>${element.companyStreet}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Numer:</div>
                                                <div class="col-md-8"><b>${element.companyStreetNo}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Miasto:</div>
                                                <div class="col-md-8"><b>${element.companyCity}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Kod pocztowy:</div>
                                                <div class="col-md-8"><b>${element.companyZip}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Telefon:</div>
                                                <div class="col-md-8"><b>${element.companyPhone}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Właściciel/Dyrektor:</div>
                                                <div class="col-md-8"><b>${element.companyOwnerFirstName} ${element.companyOwnerLastName}<c:if test="${not empty element.companyOwnerPosition}">, ${element.companyOwnerPosition}</c:if></b></div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">Opiekun zakładowy:</div>
                                                    <div class="col-md-8"><b>${element.companyInternshipAdministratorFirstName} ${element.companyInternshipAdministratorLastName}</b></div>
                                            </div>
                                            <hr class="divider">
                                            <div class="row">
                                                <div class="col-md-4">Komentarz:</div>
                                                <div class="col-md-8"><b>${element.comments}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Archiwizowana:</div>
                                                <div class="col-md-8"><b>
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
                                                <div class="col-md-4">Kierunek:</div>
                                                <div class="col-md-8"><b>${element.facultyId.name}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Typ praktyk:</div>
                                                <div class="col-md-8"><b>${element.internshipTypeId.type}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">Specjalność:</div>
                                                <div class="col-md-8"><b>${element.specializationId.name}</b></div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">System studiów:</div>
                                                <div class="col-md-8"><b>${element.systemSystemId.name}</b></div>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Zamknij</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--koniec modala szczegółów--%>
                    </c:forEach>
                </tbody>
            </table>

            <%--Paginacja --%>
            <c:if test="${endIndex >1 }">                                            
                <c:url var="firstUrl" value="/Praktyka/wykaz/1" />
                <c:url var="lastUrl" value="/Praktyka/wykaz/${totalPages}" />
                <c:url var="prevUrl" value="/Praktyka/wykaz/${currentIndex - 1}" />
                <c:url var="nextUrl" value="/Praktyka/wykaz/${currentIndex + 1}" />

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
                                <c:url var="pageUrl" value="/Praktyka/wykaz/${i}" />
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
                                <c:when test="${currentIndex == totalPages}">
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
        </c:otherwise>
    </c:choose>



</jsp:body>
</t:layout>