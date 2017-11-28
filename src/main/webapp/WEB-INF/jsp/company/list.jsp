<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<t:layout>
    <jsp:body>
        <h1>Firmy</h1>
        <sec:authorize access="hasRole('ROLE_STUDENT')">
            <div class="jumbotron">
                <h2>Wybierz firmę, w której odbędziesz praktyki</h2>
                <p>Na liście nie ma firmy, w której odbędziesz praktyki? Dodaj nową firmę, lecz upewnij się, że na pewno nie ma jej już na liście.</p>
            </div>
        </sec:authorize>
        <%--Formularz wyszukiwania --%>
        <form:form method="post" modelAttribute="search" >
            <form:input path="name" id="name" type="text"></form:input>
                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search"></span> Szukaj
                </button>
        </form:form>
        <c:if test="${not empty errormsg}">           
            <div class="alert alert-danger" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-remove"></span>
                    ${errormsg}
                </div>
            </div>
        </c:if>
        <%--Button nowej encji --%>
        <c:url var="nowyUrl" value="/Firma/nowy"/>
        <form action="${nowyUrl}" method="get"> 
            <button type="submit" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus"></span> Dodaj nową firmę
            </button>                  
        </form>
        <c:choose>
            <c:when test="${empty elements}">
                <div role="alert">			
                    <strong>Nie znaleziono żadnych firm.</strong>
                </div>
            </c:when>
            <c:otherwise>

                <table class="table table-hover table-striped table-responsive">
                    <thead>
                        <tr>
                            <th>Firma</th>
                            <th>Telefon</th>
                            <th>Adres</th>
                            <th>Komentarz</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <th>Stan</th>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_STUDENT')">
                                <th>Wybierz</th>
                                </sec:authorize>
                            <th>Edytuj</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <th>Zmień stan</th>
                                </sec:authorize>
                            <th>Usuń</th>
                        </tr>

                    </thead>
                    <tbody>
                        <c:forEach var="element" items="${elements}">
                            <tr>
                                <td>
                                    <a class="btn btn-link" href="<c:url
                                           value="/Firma/widok/${element.companyId}"/>">
                                    ${element.name}
                                    </a></td>
                                <td>${element.phone}</td>
                                <td><c:choose>
                                        <c:when test="${empty element.street}">
                                            ${element.city} ${element.streetNo}<br/>
                                            ${element.zip}
                                        </c:when>
                                        <c:otherwise>${element.street} ${element.streetNo}<br/>
                                            ${element.zip} ${element.city} 
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${element.comments}</td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <td><c:choose>
                                            <c:when test="${element.enabled}">  
                                                Aktywna
                                            </c:when> 
                                            <c:otherwise>
                                                Zablokowana
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </sec:authorize><sec:authorize access="hasRole('ROLE_STUDENT')">
                                    <td>
                                        <c:url var="wybranaFirmaUrl" value="/Praktyka/wybrana_firma/${element.companyId}"/>
                                        <form action="${wybranaFirmaUrl}" method="get"> 
                                            <button class="btn btn-primary" type="submit"> 
                                                <span class="glyphicon glyphicon-ok" aria-label="Wybierz"></span> Wybierz
                                            </button>                 
                                        </form>
                                    </td>
                                </sec:authorize>
                                <td>
                                    <%--Edytuj --%>
                                    <c:url var="edycjaFirmyUrl" value="/Firma/edycja/${element.companyId}"/>
                                    <form action="${edycjaFirmyUrl}" method="get"> 
                                        <button class="btn btn-link" type="submit"> 
                                            <span class="glyphicon glyphicon-edit" aria-label="Edytuj"></span>
                                        </button>                 
                                    </form>
                                </td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <td>
                                        <%--Zablokuj --%>
                                        <c:if test="${element.enabled}">
                                            <c:url var="blokadaFirmyUrl" value="/Firma/${element.companyId}/zablokuj"/>
                                            <form action="${blokadaFirmyUrl}" method="post">                       
                                                <button class="btn btn-link" type="submit"> 
                                                    <span class="glyphicon glyphicon-ban-circle" aria-label="Zablokuj"></span>
                                                </button>                                          
                                            </form>
                                        </c:if> 
                                        <%--Odblokuj --%>
                                        <c:if test="${!element.enabled}">
                                            <c:url var="odblokowanieFirmyUrl" value="/Firma/${element.companyId}/odblokuj"/>
                                            <form action="${odblokowanieFirmyUrl}" method="post">                         
                                                <button class="btn btn-link" type="submit"> 
                                                    <span class="glyphicon glyphicon-ok-circle" aria-label="Odblokuj"></span>
                                                </button>                     
                                            </form> 
                                        </c:if>   
                                    </td>
                                </sec:authorize>
                                <td>
                                    <%--Usuń --%>
                                    <c:url var="usuwanieFirmyUrl" value="/Firma/${element.companyId}/usun"/>
                                    <form action="${usuwanieFirmyUrl}" method="post"
                                          onsubmit="return confirm('Czy chcesz usunąć wybraną firmę?') ? true : false;">                    
                                        <c:choose>
                                            <c:when test="${empty element.internshipCollection}">  
                                                <button class="btn btn-link" type="submit"> 
                                                    <span class="glyphicon glyphicon-trash" style="color:red" aria-label="Usuń"></span>
                                                </button>
                                            </c:when>    
                                            <c:otherwise>
                                                <button class="btn btn-link" type="submit" disabled="disabled"> 
                                                    <span class="glyphicon glyphicon-trash" style="color:lightgrey" aria-label="Usunięcie niemożliwe"></span>
                                                </button>
                                                <br />
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>


                <%--Paginacja --%>
                <c:if test="${endIndex >1 }">                                            
                    <c:url var="firstUrl" value="/Firma/lista/1" />
                    <c:url var="lastUrl" value="/Firma/lista/${thePage.totalPages}" />
                    <c:url var="prevUrl" value="/Firma/lista/${currentIndex - 1}" />
                    <c:url var="nextUrl" value="/Firma/lista/${currentIndex + 1}" />

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
                                    <c:url var="pageUrl" value="/Firma/lista/${i}" />
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
            </c:otherwise>
        </c:choose>
    </jsp:body>
</t:layout>