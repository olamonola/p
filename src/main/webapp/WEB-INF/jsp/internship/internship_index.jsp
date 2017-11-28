<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<t:layout>
    <jsp:body>

        <t:cMessageDanger msg="${errormsg}"/>
        <c:if test="${not empty msg}">           
            <div class="alert alert-success" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-ok"></span>
                    ${msg}
                </div>
            </div>
        </c:if>

        <div class="jumbotron">
            <p><b>Chcesz dodać nową praktykę? Kliknij w żółty przycisk, aby wybrać firmę,
                    w której odbędziesz praktykę. Po dokonaniu wyboru firmy nastąpi 
                    przekierowanie do formularza dodania nowej praktyki.
                    Jeżeli natomiast otrzymałeś pisemne potwierdzenie zwolnienia z praktyk, również 
                    musisz dodać tę informację. Kliknij ten sam żółty przycisk, wybierz firmę, w której 
                    pracowałeś, a następnie zostaniesz przekierowany do formularza, gdzie zaznaczysz, 
                    że jesteś zwolniony z praktyk.</b>
            </p>
            <div class="text-center">
                <a class="btn btn-lg btn-intern" href="<c:url
                       value="/Firma/lista/1"/>">
                    <span class="glyphicon glyphicon-plus" aria-label="Wybierz firmę, w której odbędziesz praktykę">
                    </span> Wybierz firmę do praktyk
                </a>
            </div>
        </div>


        <%--Tabela z listą elementów. Na dole jest paginacja--%>
        <c:choose>
            <c:when test="${empty elements}">
                <div role="alert">			
                    <strong>Nie znaleziono żadnych praktyk.</strong>
                </div>
            </c:when>
            <c:otherwise>

                <table class="table table-hover table-striped table-responsive">
                    <thead>
                        <tr>
                            <th>Firma</th>
                            <th>Czas trwania</th>
                            <th>Ocena</th>
                            <th>Potwierdzenie</th>  
                            <th></th>      
                        </tr>

                    </thead>
                    <tbody>
                        <c:forEach var="element" items="${elements}">
                            <tr>
                                <td>${element.companyName}<br>
                                    <c:choose>
                                        <c:when test="${empty element.companyStreet}">
                                            ${element.companyCity} ${element.companyStreetNo}<br/>
                                            ${element.companyZip}
                                        </c:when>
                                        <c:otherwise>${element.companyStreet} ${element.companyStreetNo}<br/>
                                            ${element.companyZip} ${element.companyCity} 
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test ="${element.exemption}">
                                            Zwolnienie
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test = "${empty element.startDate && empty element.endDate}">
                                                    -
                                                </c:when>
                                                <c:otherwise>

                                                    od 
                                                    <c:choose>
                                                        <c:when test= "${empty element.startDate}">
                                                            -<br>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fmt:formatDate value="${element.startDate}" pattern="dd-MM-yyyy" /><br>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    do 
                                                    <c:choose>
                                                        <c:when test= "${empty element.endDate}">
                                                            -
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fmt:formatDate value="${element.endDate}" pattern="dd-MM-yyyy" /><br>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise> 
                                            </c:choose>
                                        </c:otherwise> 
                                    </c:choose>
                                <td>
                                    <c:choose>
                                        <c:when test= "${empty element.grade}">
                                            brak
                                        </c:when>
                                        <c:otherwise>
                                            ${element.grade}<br>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test= "${element.confirmation}">
                                            Potwierdzona
                                        </c:when>
                                        <c:otherwise>
                                            Niepotwierdzona<br>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <%--PDF --%>
                                    <a class="btn btn-link" href="<c:url
                                           value="/Praktyka/umowaPraktyk/${element.internshipId}"/>">
                                        <span class="glyphicon glyphicon-file" aria-label="Umowa praktyk"></span>
                                        Umowa (PDF)</a>
                                    <br>
                                    <%--Szczegóły --%>
                                    <a class="btn btn-link" href="<c:url
                                           value="/Praktyka/widok/${element.internshipId}"/>">
                                        <span class="glyphicon glyphicon-zoom-in" aria-label="Szczegóły praktyki"></span>
                                        Szczegóły</a>
                                    <br>
                                    <%--Edytuj --%><c:choose>
                                        <c:when test="${!element.confirmation and empty element.grade}">  
                                            <a class="btn btn-link" href="<c:url
                                                   value="/Praktyka/edycja/${element.internshipId}"/>">
                                                <span class="glyphicon glyphicon-edit" aria-label="Edytuj"></span> Edytuj
                                            </a>
                                        </c:when>    
                                        <c:when test="${element.confirmation or not empty element.grade}">  
                                            <button class="btn btn-link" type="button" title="Edycja niemożliwa, ponieważ praktyka została oceniona lub potwierdzona."> 
                                                <span class="glyphicon glyphicon-edit" style="color:lightgrey" aria-label="Edycja niemożliwa"></span>
                                            </button>
                                            <br />
                                        </c:when> 
                                    </c:choose>

                                    <%--Usuń --%>
                                    <c:choose>
                                        <c:when test="${!element.confirmation and empty element.grade}">  
                                            <form action="<c:url value="/Praktyka/${element.internshipId}/usun"/>" method="post"
                                                  onsubmit="return confirm('Czy chcesz usunąć wybraną praktykę?') ? true : false;">                    

                                                <button class="btn btn-link" type="submit"> 
                                                    <span class="glyphicon glyphicon-trash" style="color:red" aria-label="Usuń"></span> Usuń
                                                </button>
                                            </form>
                                        </c:when>    
                                        <c:when test="${element.confirmation or not empty element.grade}">  
                                            <button class="btn btn-link" type="button" title="Usunięcie niemożliwe, ponieważ praktyka została oceniona lub potwierdzona."> 
                                                <span class="glyphicon glyphicon-trash" style="color:lightgrey" aria-label="Usunięcie niemożliwe"></span>
                                            </button>
                                            <br />
                                        </c:when> 
                                    </c:choose>
                                </td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>


                <%--Paginacja --%>
                <c:if test="${endIndex >1 }">                                            
                    <c:url var="firstUrl" value="/Praktyka/mojePraktyki/1" />
                    <c:url var="lastUrl" value="/Praktyka/mojePraktyki/${thePage.totalPages}" />
                    <c:url var="prevUrl" value="/Praktyka/mojePraktyki/${currentIndex - 1}" />
                    <c:url var="nextUrl" value="/Praktyka/mojePraktyki/${currentIndex + 1}" />

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
                                    <c:url var="pageUrl" value="/Praktyka/mojePraktyki/${i}" />
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


