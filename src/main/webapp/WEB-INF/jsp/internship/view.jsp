<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<t:layout>
    <jsp:body>  
        <c:choose>
            <c:when test="${internship.internshipId == null || errormsg == 'Brak dostępu'}">  
                <div class="alert alert-danger" role="alert">	
                    <div class="text-center">
                        <span class="glyphicon glyphicon-remove"></span>
                        Brak dostępu do danych.
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <h1>Widok praktyki</h1>
                <c:if test="${not empty msg}">           
                    <div class="alert alert-success" role="alert">	
                        <div class="text-center">
                            <span class="glyphicon glyphicon-ok"></span>
                            ${msg}
                        </div>
                    </div>
                </c:if>
                <table class="table table-responsive borderless">
                    <tbody>
                        <tr class="blueRowHeader">
                            <td class="col-md-2"></td>
                            <td class="col-md-10 cellGradientBlue"><b>Informacje ogólne:</b></td>
                        </tr>
                        <c:if test="${internship.exemption}">
                            <tr>
                                <td class="col-md-2 cellGradientBlue">Zwolnienie z praktyk</td>
                                <td class="col-md-10">
                                    <b>Student zaznczył, że uzyskał pisemne zwolnienie z praktyk.</b>
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td class="col-md-2 cellGradientBlue">Imię i nazwisko studenta</td>
                            <td class="col-md-10">
                                <b>${internship.accountId.firstName}
                                    ${internship.accountId.lastName}
                                </b>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientBlue">Rok studiów</td>
                            <td class="col-md-10"><b>${internship.year}</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientBlue">System studiów</td>
                            <td class="col-md-10"><b>${internship.systemSystemId.name}</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientBlue">Kierunek</td>
                            <td class="col-md-10"><b>${internship.facultyId.name}</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientBlue">Specjalność</td>
                            <td class="col-md-10"><b>${internship.specializationId.name}</b></td>
                        </tr>
                        <c:if test="${!internship.exemption}">
                            <tr>
                                <td class="col-md-2 cellGradientBlue">Rozpoczęcie praktyk</td>
                                <td class="col-md-10"><b><fmt:formatDate value="${internship.startDate}" pattern="dd-MM-yyyy" /></b></td>
                            </tr>

                            <tr>
                                <td class="col-md-2 cellGradientBlue">Zakończenie praktyk</td>
                                <td class="col-md-10"><b><fmt:formatDate value="${internship.endDate}" pattern="dd-MM-yyyy" /></b></td>
                            </tr>
                        </c:if>
                        <tr>
                            <td class="col-md-2 cellGradientBlue">Typ praktyk</td>
                            <td class="col-md-10"><b>${internship.internshipTypeId.type}</b></td>
                        </tr>
                        <c:if test="${!internship.exemption}">
                            <tr class="greenRowHeader">
                                <td class="col-md-2"></td>
                                <td class="col-md-10 cellGradientGreen"><b>Dyrektor (Kierownik) firmy lub upoważniony przezeń pracownik:</b></td>
                            </tr>
                            <tr>
                                <td class="col-md-2 cellGradientGreen">Imię</td>
                                <td class="col-md-10"><b>${internship.companyOwnerFirstName}</b></td>
                            </tr>
                            <tr>
                                <td class="col-md-2 cellGradientGreen">Nazwisko</td>
                                <td class="col-md-10"><b>${internship.companyOwnerLastName}</b></td>
                            </tr>
                            <tr>
                                <td class="col-md-2 cellGradientGreen">Stanowisko</td>
                                <td class="col-md-10"><b>${internship.companyOwnerPosition}</b></td>
                            </tr>
                            <tr class="blueRowHeader">
                                <td class="col-md-2"></td>
                                <td class="col-md-10 cellGradientBlue"><b>Opiekun zakładowy praktyk:</b></td>
                            </tr>
                            <tr>
                                <td class="col-md-2 cellGradientBlue">Imię opiekuna zakładowego</td>
                                <td class="col-md-10"><b>${internship.companyInternshipAdministratorFirstName}</b></td>
                            </tr>
                            <tr>
                                <td class="col-md-2 cellGradientBlue">Nazwisko opiekuna zakładowego</td>
                                <td class="col-md-10"><b>${internship.companyInternshipAdministratorLastName}</b></td>
                            </tr>
                        </c:if>
                        <tr class="greenRowHeader">
                            <td class="col-md-2"></td>
                            <td class="col-md-10 cellGradientGreen"><b>Dane firmy:</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientGreen">Nazwa firmy</td>
                            <td class="col-md-10"><b>${internship.companyName}</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientGreen">Telefon firmy</td>
                            <td class="col-md-10"><b>${internship.companyPhone}</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientGreen">Ulica firmy</td>
                            <td class="col-md-10"><b>${internship.companyStreet}</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientGreen">Numer budynku firmy</td>
                            <td class="col-md-10"><b>${internship.companyStreetNo}</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientGreen">Miejscowość firmy</td>
                            <td class="col-md-10"><b>${internship.companyCity}</b></td>
                        </tr>
                        <tr>
                            <td class="col-md-2 cellGradientGreen">Kod pocztowy firmy</td>
                            <td class="col-md-10"><b>${internship.companyZip}</b></td>
                        </tr>
                        <tr class="blueRowHeader">
                            <td class="col-md-2">Komentarz</td>
                            <td class="col-md-10 cellGradientBlue"><b>${internship.comments}</b></td>
                        </tr>

                    </tbody>         
                </table>

                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_OPIEKUN_PRAKTYK')">
                    <a class="btn btn-default" href="<c:url
                           value="/Praktyka/listaPraktyk/1"/>">
                        Lista praktyk
                    </a>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_STUDENT')">
                    <a class="btn btn-default" href="<c:url
                           value="/Praktyka/mojePraktyki/1"/>">
                        Lista praktyk
                    </a>
                </sec:authorize>
            </c:otherwise>
        </c:choose>
    </jsp:body>
</t:layout>