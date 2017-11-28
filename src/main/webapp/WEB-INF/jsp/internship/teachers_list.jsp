<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<t:layout>
    <jsp:body>

        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <c:forEach var="element" items="${allSchoolYears}">  
                            <li>
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#${element}">
                                                <p class="text-center">
                                                    <span class="glyphicon glyphicon-folder-close">
                                                    </span>
                                                    ${element}/${element+1} <span class="caret">
                                                    </span>
                                                </p></a>
                                        </h4>
                                    </div>
                                    <div id="${element}" class="panel-collapse collapse in">
                                        <div class=".panel-body-custom">

                                            <div class="panel panel-cust panel-one">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a  data-parent="#accordion" href="<c:url
                                                                value="/Praktyka/listaPraktyk/1?year=1&schoolyear=${element}"/>">
                                                            <p class="text-center">Rok 1</p></a>
                                                    </h4>
                                                </div>
                                                <div class=".panel-body-custom">


                                                    <table class="table table-cust">
                                                        <c:forEach var="specialization" items="${specializations}">
                                                            <tr>
                                                                <td class="td-cust">
                                                                    <a href="<c:url
                                                                           value="/Praktyka/listaPraktyk/1?year=1&schoolyear=${element}&specId=${specialization.specializationId}"/>">
                                                                        <p class="text-center">${specialization.name}</p>
                                                                    </a>   
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>



                                                </div>
                                            </div>









                                            <div class="panel panel-cust panel-two">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-parent="#accordion" href="<c:url
                                                               value="/Praktyka/listaPraktyk/1?year=2&schoolyear=${element}"/>">
                                                            <p class="text-center">Rok 2</p></a>
                                                    </h4>
                                                </div>
                                                <div class=".panel-body-custom">


                                                    <table class="table table-cust">
                                                        <c:forEach var="specialization" items="${specializations}">
                                                            <tr>
                                                                <td class="td-cust">
                                                                    <a href="<c:url
                                                                           value="/Praktyka/listaPraktyk/1?year=2&schoolyear=${element}&specId=${specialization.specializationId}"/>">
                                                                        <p class="text-center">${specialization.name}</p>
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>


                                                </div>
                                            </div>












                                            <div class="panel panel-cust panel-three">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-parent="#accordion" href="<c:url
                                                               value="/Praktyka/listaPraktyk/1?year=3&schoolyear=${element}"/>">
                                                            <p class="text-center">Rok 3</p>
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div class=".panel-body-custom">


                                                    <table class="table table-cust">
                                                        <c:forEach var="specialization" items="${specializations}">
                                                            <tr>
                                                                <td class="td-cust">
                                                                    <a href="<c:url
                                                                           value="/Praktyka/listaPraktyk/1?year=3&schoolyear=${element}&specId=${specialization.specializationId}"/>">
                                                                        <p class="text-center">${specialization.name}</p>
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>



                                                </div>
                                            </div>














                                            <div class="panel panel-cust panel-four">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-parent="#accordion" href="<c:url
                                                               value="/Praktyka/listaPraktyk/1?year=4&schoolyear=${element}"/>">
                                                            <p class="text-center">Rok 4</p></a>
                                                    </h4>
                                                </div>
                                                <div class=".panel-body-custom">


                                                    <table class="table table-cust">
                                                        <c:forEach var="specialization" items="${specializations}">
                                                            <tr>
                                                                <td class="td-cust">
                                                                    <a href="<c:url
                                                                           value="/Praktyka/listaPraktyk/1?year=4&schoolyear=${element}&specId=${specialization.specializationId}"/>">
                                                                        <p class="text-center">${specialization.name}</p>
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </table>



                                                </div>
                                            </div>


                                        </div>
                                    </div>
                                </div>


                            </li>
                        </c:forEach>

                    </ul>
                </div>
                <%--KONIEC MENU BOCZNEGO--%>

                <div class="col-sm-9 col-md-12 main">
                    <%--DASHBOARD--%>

                    <c:choose>
                        <c:when test="${empty param.year || empty param.schoolyear}">
                            <div class="jumbotron">
                                <h1>Praktyki</h1>
                                Wybierz grupę studentów z menu bocznego.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h1>Praktyki</h1>
                        </c:otherwise>
                    </c:choose>

                    <form:form method="post" modelAttribute="searchInternship" class="form-horizontal">

                        <t:cinput path="firstName" label="Imię studenta" placeholder="Imię"/>
                        <t:cinput path="lastName" label="Nazwisko studenta" placeholder="Nazwisko"/>
                        <form:input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                        <form:input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                        <form:input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />

                        <div class="text-center">
                            <form:button type="submit" class="btn btn-default" name="erase" value="erase">
                            <span class="glyphicon glyphicon-erase"></span> Wyczyść parametry wyszukiwania
                        </form:button>
                            <form:button type="submit" class="btn btn-info" name="search" value="search">
                                <span class="glyphicon glyphicon-search"></span> Szukaj
                            </form:button> 

                        </div>
                    </form:form> 
                    <br>


                    <c:if test="${empty elements and not empty param.year and not empty param.schoolyear}">
                        Brak praktyk dla podanych parametrów: <br>
                        rocznik studentów ${param.year}<br>
                        rok szkolny ${param.schoolyear}<br>
                        <c:if test="${not empty param.specId}">
                            identyfikator specjalizacji ${param.specId}<br>
                        </c:if>
                        Studenci nie wprowadzili danych dla tych paramertów.
                    </c:if>

                    <t:cMessageDanger msg="${errormsg}"/>   
                    <c:if test="${not empty msg}">           
                        <div class="alert alert-success" role="alert">	<div class="text-center">
                                <span class="glyphicon glyphicon-ok"></span>
                                ${msg}
                            </div>
                        </div>
                    </c:if>
                    <%--TABELA--%>
                    <c:if test="${not empty elements}">

                        <table class="table table-hover table-striped table-responsive">
                            <thead>
                                <tr>
                                    <th>Student</th>
                                    <th>Ocena</th>
                                    <th>Potwierdzenie</th>
                                    <th>Firma</th>
                                    <th>Utworzono</th>
                                    <th>Ostatnia edycja</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="internship" items="${elements}" varStatus="vs">
                                    <c:choose>
                                        <c:when test="${internship.archived}">
                                            <tr class="archived">
                                            </c:when>
                                            <c:otherwise>
                                            <tr>
                                            </c:otherwise>
                                        </c:choose>

                                        <td>${internship.accountId.firstName} ${internship.accountId.lastName}</td>
                                        <td>
                                            <%--OCENA--%>
                                            <c:url var="ocenUrl" value="/Praktyka/ocen"/>
                                            <%--ocniona--%>
                                            <c:if test="${not empty internship.grade}">
                                                <c:choose>
                                                    <%--ocniona na 2--%>
                                                    <c:when test="${internship.grade == '2.0'}">
                                                        
                                                        <form:form method="post" action="${ocenUrl}" modelAttribute="grade" class="form-horizontal">
                                                            <form:input type="hidden" id="internshipId" name="internshipId" path="internshipId" value="${internship.internshipId}" />
                                                            <form:input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                                                            <form:input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                                                            <form:input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />
                                                            <form:input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                                            <div class="col-sm-6">
                                                                <form:select path="grade" class="form-control grade-select not-evaluated failed" id="internship.grade">
                                                                    <form:option value="${internship.grade}">${internship.grade}</form:option>
                                                                    <c:forEach items="${grades}" var="grade">
                                                                        <form:option value="${grade}">${grade}</form:option>
                                                                    </c:forEach>
                                                                </form:select>
                                                            </div>
                                                            <input type="submit" value="Oceń" class="btn btn-primary btn-xs" />
                                                        </form:form>  
                                                    </c:when>
                                                    <%--ocniona na wyżej niż 2--%>
                                                    <c:otherwise>
                                                        <form:form method="post" action="${ocenUrl}" modelAttribute="grade" class="form-horizontal"
                                                                   onsubmit="return confirm('Czy chcesz zmienić ocenę studenta ${internship.accountId.firstName} ${internship.accountId.lastName}? Dotychcasowa ocena to ${internship.grade}') ? true : false;">
                                                            <form:input type="hidden" id="internshipId" name="internshipId" path="internshipId" value="${internship.internshipId}" />
                                                            <form:input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                                                            <form:input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                                                            <form:input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />
                                                            <form:input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                                            <div class="col-sm-6">
                                                                <form:select path="grade" class="form-control grade-select evaluated" id="internship.grade">
                                                                    <form:option value="${internship.grade}">${internship.grade}</form:option>
                                                                    <c:forEach items="${grades}" var="grade">
                                                                        <form:option value="${grade}">${grade}</form:option>
                                                                    </c:forEach>
                                                                </form:select>
                                                            </div>
                                                            <input type="submit" value="Zmień" class="btn btn-primary btn-xs" />
                                                        </form:form> 
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <%--nieoceniona--%>
                                            <c:if test="${empty internship.grade}">
                                                <form:form method="post" action="${ocenUrl}" modelAttribute="grade" class="form-horizontal">
                                                    <form:input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                                                    <form:input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                                                    <form:input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />
                                                    <form:input type="hidden" id="internshipId" name="internshipId" path="internshipId" value="${internship.internshipId}" />
                                                    <form:input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                                    <div class="col-sm-6">
                                                        <form:select path="grade" class="form-control grade-select not-evaluated" id="internship.grade">
                                                            <form:option value="">brak</form:option>
                                                            <c:forEach items="${grades}" var="grade">
                                                                <form:option value="${grade}">${grade}</form:option>
                                                            </c:forEach>
                                                        </form:select>
                                                    </div>
                                                    <input type="submit" value="Oceń" class="btn btn-primary btn-xs" />
                                                </form:form>  
                                            </c:if>
                                        </td>
                                        <%--potwierdzenie praktyk--%>
                                        <td>
                                            <c:choose>
                                                <c:when test="${internship.confirmation}">
                                                    <div class="green-color">Potwierdzona</div>
                                                    <c:url var="potwierdzUrl" value="/Praktyka/potwierdz"/>
                                                    <form action="${potwierdzUrl}" modelAttribute="internshipConfirmation" method="post"> 
                                                        <input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                                                        <input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                                                        <input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />
                                                        <input type="hidden" id="internshipId" name="internshipId" path="internshipId" value="${internship.internshipId}" />
                                                        <input type="hidden" id="confirmation" name="confirmation" path="confirmation" value="${internship.confirmation}" />
                                                        <input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                                        <button type="submit" class="btn btn-default btn-xs">
                                                            <span class="glyphicon glyphicon-unchecked red-color"></span> Anuluj
                                                        </button>                                 
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="red-color">Niepotwierdzona</div>
                                                    <c:url var="potwierdzUrl" value="/Praktyka/potwierdz"/>
                                                    <form action="${potwierdzUrl}" modelAttribute="internshipConfirmation" method="post"> 
                                                        <input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                                                        <input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                                                        <input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />
                                                        <input type="hidden" id="internshipId" name="internshipId" path="internshipId" value="${internship.internshipId}" />
                                                        <input type="hidden" id="confirmation" name="confirmation" path="confirmation" value="${internship.confirmation}" />
                                                        <input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                                        <button type="submit" class="btn btn-default btn-xs">
                                                            <span class="glyphicon glyphicon-check green-color"></span> Potwierdź
                                                        </button> 
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${internship.companyName}<br>
                                            <c:choose>
                                                <c:when test="${empty internship.companyStreet}">
                                                    ${internship.companyCity} ${internship.companyStreetNo}<br/>
                                                    ${internship.companyZip}
                                                </c:when>
                                                <c:otherwise>${internship.companyStreet} ${internship.companyStreetNo}<br/>
                                                    ${internship.companyZip} ${internship.companyCity} 
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${internship.created}" pattern="dd-MM-yyyy" /><br>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${internship.lastEdited}" pattern="dd-MM-yyyy" /><br>
                                        </td>
                                        <td>
                                            <%-- Button trigger modal szczegóły --%>
                                            <button type="button" class="btn btn-default btn-xs" data-toggle="modal" data-target="#myModal${vs.index}" id="viewDetailButton${vs.index}">
                                                <span class="glyphicon glyphicon-zoom-in"></span>Szczegóły
                                            </button>
                                            <br>

                                            <%-- Archiwizowanie i przywracanie --%>
                                            <c:choose>
                                                <c:when test="${internship.archived}">
                                                    <c:set var="archivedconfirmation" scope="application" value="Czy chcesz przywrócić praktykę studenta ${internship.accountId.firstName} ${internship.accountId.lastName} z archiwum?"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="archivedconfirmation" scope="application" value="Czy chcesz przenieść praktykę studenta ${internship.accountId.firstName} ${internship.accountId.lastName} do archiwum?"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:url var="archiwumUrl" value="/Praktyka/archiwum"/>
                                            <form action="${archiwumUrl}" modelAttribute="internshipArchived" method="post"
                                                       onsubmit="return confirm('${archivedconfirmation}') ? true : false;">

                                                <input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                                                <input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                                                <input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />
                                                <input type="hidden" id="internshipId" name="internshipId" path="internshipId" value="${internship.internshipId}" />
                                                <input type="hidden" id="archived" name="archived" path="archived" value="${internship.archived}" />
                                                <input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                                <c:choose>
                                                    <c:when test="${internship.archived}">
                                                        <button type="submit" class="btn btn-default btn-xs">
                                                            Przywróć z archiwum
                                                        </button>   
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit" class="btn btn-default btn-xs">
                                                            Archiwizuj
                                                        </button>  
                                                    </c:otherwise>
                                                </c:choose>                              
                                            </form>
                                            <%-- Archiwizowanie i przywracanie KONIEC--%>
                                            
                                            
                                            
                                            <%-- Odwoływanie i przywracanie --%>
                                            <c:choose>
                                                <c:when test="${internship.dismissed}">
                                                    <c:set var="dismissconfirmation" scope="application" value="Czy chcesz anulować odwołanie studenta ${internship.accountId.firstName} ${internship.accountId.lastName} z praktyki? Student nie będzie już odwołany z praktyki."/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="dismissconfirmation" scope="application" value="Czy chcesz odwołać studenta ${internship.accountId.firstName} ${internship.accountId.lastName} z praktyki?"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:url var="odwolanieUrl" value="/Praktyka/odwolanie"/>
                                            <form action="${odwolanieUrl}" modelAttribute="studentDismissed" method="post"
                                                       onsubmit="return confirm('${dismissconfirmation}') ? true : false;">

                                                <input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                                                <input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                                                <input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />
                                                <input type="hidden" id="internshipId" name="internshipId" path="internshipId" value="${internship.internshipId}" />
                                                <input type="hidden" id="dismissed" name="dismissed" path="dismissed" value="${internship.dismissed}" />
                                                <input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                                <c:choose>
                                                    <c:when test="${internship.dismissed}">
                                                        <button type="submit" class="btn btn-default btn-xs">
                                                            Anuluj odwołanie
                                                        </button>   
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit" class="btn btn-default btn-xs">
                                                            Odwołaj
                                                        </button>  
                                                    </c:otherwise>
                                                </c:choose>                              
                                            </form>
                                            <%-- Odwołyanie i przywracanie KONIEC--%>
                                            <%-- Edycja--%>
                                            <a class="btn btn-default btn-xs" href="<c:url
                                                   value="/Praktyka/edycja/${internship.internshipId}"/>">
                                                <span class="glyphicon glyphicon-edit" aria-label="Edytuj"></span>
                                                Edytuj
                                            </a>
                                            <%-- Usuwanie--%>
                                            <c:choose>
                                                <c:when test="${internship.archived}">
                                                    <c:url var="usunPraktykeUrl" value="/Praktyka/${internship.internshipId}/usunPraktyke"/>
                                                    <form action="${usunPraktykeUrl}" method="post" modelAttribute="currentPage"
                                                               onsubmit="return confirm('Czy chcesz usunąć praktykę studenta ${internship.accountId.firstName} ${internship.accountId.lastName}?') ? true : false;">                    
                                                        <input type="hidden" id="schoolyear" name="schoolyear" path="schoolyear" value="${param.schoolyear}" />
                                                        <input type="hidden" id="year" name="year" path="year" value="${param.year}" />
                                                        <input type="hidden" id="specId" name="specId" path="specId" value="${param.specId}" />

                                                        <button class="btn btn-default btn-xs" type="submit"> 
                                                            <span class="glyphicon glyphicon-trash" style="color:red" aria-label="Usuń"></span>Usuń
                                                        </button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>

                                    <%-- Modal szczegółów --%>
                                <div class="modal fade" id="myModal${vs.index}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <div class="text-center">
                                                    <h3 class="modal-title" id="myModalLabel"><b>${fn:toUpperCase(internship.accountId.firstName)} ${fn:toUpperCase(internship.accountId.lastName)}</b></h3>
                                                </div>
                                            </div>
                                            <div class="modal-body">
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-md-4">Student:</div>
                                                        <div class="col-md-8"><b>${internship.accountId.firstName} ${internship.accountId.lastName}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Rok studiów:</div>
                                                        <div class="col-md-8"><b>${internship.year}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Rozpoczęcie praktyk:</div>
                                                        <div class="col-md-8"><b><fmt:formatDate value="${internship.startDate}" pattern="dd-MM-yyyy" /></b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Zakończenie praktyk:</div>
                                                        <div class="col-md-8"><b><fmt:formatDate value="${internship.endDate}" pattern="dd-MM-yyyy" /></b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Potwierdzenie:</div>
                                                        <div class="col-md-8"><b>
                                                                <c:choose>
                                                                    <c:when test="${internship.confirmation}">
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
                                                                    <c:when test="${internship.exemption}">
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
                                                                    <c:when test="${not empty internship.grade}">
                                                                        ${internship.grade}
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        Brak oceny
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Dodanie praktyki:</div>
                                                        <div class="col-md-8"><b><fmt:formatDate value="${internship.created}" pattern="dd-MM-yyyy" /></b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Ostatnia edycja:</div>
                                                        <div class="col-md-8"><b><fmt:formatDate value="${internship.lastEdited}" pattern="dd-MM-yyyy" /></b></div>
                                                    </div>
                                                    <hr class="divider">
                                                    <div class="row">
                                                        <div class="col-md-4">Nazwa firmy:</div>
                                                        <div class="col-md-8"><b>${internship.companyName}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Ulica:</div>
                                                        <div class="col-md-8"><b>${internship.companyStreet}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Numer:</div>
                                                        <div class="col-md-8"><b>${internship.companyStreetNo}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Miasto:</div>
                                                        <div class="col-md-8"><b>${internship.companyCity}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Kod pocztowy:</div>
                                                        <div class="col-md-8"><b>${internship.companyZip}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Telefon:</div>
                                                        <div class="col-md-8"><b>${internship.companyPhone}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Właściciel/Dyrektor:</div>
                                                        <div class="col-md-8"><b>${internship.companyOwnerFirstName} ${internship.companyOwnerLastName}<c:if test="${not empty internship.companyOwnerPosition}">, ${internship.companyOwnerPosition}</c:if></b></div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-md-4">Opiekun zakładowy:</div>
                                                            <div class="col-md-8"><b>${internship.companyInternshipAdministratorFirstName} ${internship.companyInternshipAdministratorLastName}</b></div>
                                                    </div>
                                                    <hr class="divider">
                                                    <div class="row">
                                                        <div class="col-md-4">Komentarz:</div>
                                                        <div class="col-md-8"><b>${internship.comments}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Archiwizowana:</div>
                                                        <div class="col-md-8"><b>
                                                                <c:choose>
                                                                    <c:when test="${internship.archived}">
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
                                                        <div class="col-md-8"><b>${internship.facultyId.name}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Typ praktyk:</div>
                                                        <div class="col-md-8"><b>${internship.internshipTypeId.type}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">Specjalność:</div>
                                                        <div class="col-md-8"><b>${internship.specializationId.name}</b></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-4">System studiów:</div>
                                                        <div class="col-md-8"><b>${internship.systemSystemId.name}</b></div>
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
                    </c:if>


                    <%--Paginacja --%>
                    <c:if test="${endIndex >1 }">  
                        <c:choose>
                            <%--Bez uwzględnienia specjalizacji --%>
                            <c:when test="${empty param.specId}">
                                <c:url var="firstUrl" value="/Praktyka/listaPraktyk/1?year=${param.year}&schoolyear=${param.schoolyear}" />
                                <c:url var="lastUrl" value="/Praktyka/listaPraktyk/${thePage.totalPages}?year=${param.year}&schoolyear=${param.schoolyear}" />
                                <c:url var="prevUrl" value="/Praktyka/listaPraktyk/${currentIndex - 1}?year=${param.year}&schoolyear=${param.schoolyear}" />
                                <c:url var="nextUrl" value="/Praktyka/listaPraktyk/${currentIndex + 1}?year=${param.year}&schoolyear=${param.schoolyear}" />
                            </c:when>
                            <%--Z uwzglęnieniem specjalizacji --%>
                            <c:otherwise>
                                <c:url var="firstUrl" value="/Praktyka/listaPraktyk/1?year=${param.year}&schoolyear=${param.schoolyear}&specId=${param.specId}" />
                                <c:url var="lastUrl" value="/Praktyka/listaPraktyk/${thePage.totalPages}?year=${param.year}&schoolyear=${param.schoolyear}&specId=${param.specId}" />
                                <c:url var="prevUrl" value="/Praktyka/listaPraktyk/${currentIndex - 1}?year=${param.year}&schoolyear=${param.schoolyear}&specId=${param.specId}" />
                                <c:url var="nextUrl" value="/Praktyka/listaPraktyk/${currentIndex + 1}?year=${param.year}&schoolyear=${param.schoolyear}&specId=${param.specId}" />
                            </c:otherwise>
                        </c:choose>

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
                                        <c:choose>
                                            <%--Bez uwzględnienia specjalizacji --%>
                                            <c:when test="${empty param.specId}">
                                                <c:url var="pageUrl" value="/Praktyka/listaPraktyk/${i}?year=${param.year}&schoolyear=${param.schoolyear}" />
                                            </c:when>
                                            <%--Z uwzglęnieniem specjalizacji --%>
                                            <c:otherwise>
                                                <c:url var="pageUrl" value="/Praktyka/listaPraktyk/${i}?year=${param.year}&schoolyear=${param.schoolyear}&specId=${param.specId}" />
                                            </c:otherwise>
                                        </c:choose>
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
                    <!--Paginacja KONIEC-->
                </div>
                <%--KONIEC PANELU PO PRAWEJ STRONIE--%> 
            </div>
        </div>


    </jsp:body>
</t:layout>


