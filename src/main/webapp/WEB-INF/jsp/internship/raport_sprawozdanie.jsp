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
        <div class="text-center">
            <h1>SPRAWOZDANIE</h1>
            <h2>Opiekuna Praktyk Studenckich z przebiegu realizacji praktyki</h2>    
        </div>
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
                <form:form method="post" modelAttribute="sprawozdanieSearch" class="form-horizontal">

                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/kierunek.png"/>" style="width: 100px;"/></span>
                            <form:select path="facultyId" class="form-control" id="facultyId">
                                <c:forEach items="${faculties}" var="faculty">
                                    <form:option value="${faculty.facultyId}" >${faculty.name}</form:option>
                                </c:forEach>
                            </form:select>
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/specjalnosc.png"/>" style="width: 100px;"/></span>
                            <form:select path="specializationId" class="form-control" id="specializationId">
                                <c:forEach items="${specializations}" var="specialization">
                                    <form:option value="${specialization.specializationId}" >${specialization.name}</form:option>
                                </c:forEach>
                            </form:select>
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/system.png"/>" style="width: 100px;"/></span>
                            <form:select path="systemId" class="form-control" id="systemId">
                                <c:forEach items="${systems}" var="system">
                                    <form:option value="${system.systemId}" >${system.name}</form:option>
                                </c:forEach>
                            </form:select>
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/rok-studiow.png"/>" style="width: 100px;"/></span>
                            <form:select path="year" class="form-control" id="year">
                                <c:forEach items="${years}" var="year">
                                    <form:option value="${year}" >${year}</form:option>
                                </c:forEach>
                            </form:select>
                    </div>


                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/typ_praktyk.png"/>" style="width: 100px;"/></span>
                            <form:select path="internshipTypeId" class="form-control" id="internshipTypeId">
                                <c:forEach items="${internshipTypes}" var="type">
                                    <form:option value="${type.internshipTypeId}" >${type.type}</form:option>
                                </c:forEach>
                            </form:select>
                    </div>

                    <div class="input-group">
                        <span class="input-group-addon"><img src="<c:url value="/resources/static/images/rok_akademicki.png"/>" style="width: 100px;"/></span>
                            <form:select path="schoolYear" class="form-control" id="schoolYear">
                                <c:forEach items="${schoolYears}" var="year">
                                    <form:option value="${year}" >${year}/${year+1}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="text-center">
                        <form:button type="submit" class="btn btn-info" name="search" value="search">
                            <span class="glyphicon glyphicon-search"></span> Szukaj
                        </form:button> 
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><span class="glyphicon glyphicon-file"></span>Generuj PDF lub pobierz plik CSV</h3>
                </div>
                <div class="panel-body">
                    <t:cinput path="teacher" label="Opiekun praktyk*" placeholder="Imię Nazwisko"/><br>
                    <div class="form-group">
                        <label for="comments" class="control-label col-sm-2">Uwagi i wnioski</label>
                        <div class="col-sm-10"> 
                            <form:textarea path="comments" id="comments" class="form-control" rows="2" cols="50"></form:textarea>              
                            </div>
                            <BR>
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
        </div>

        <c:choose>
            <c:when test="${empty elements}">
                <div role="alert">			
                    <strong>Nie znaleziono żadnych instytucji.</strong>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-hover table-striped table-responsive">
                    <thead>
                        <tr>
                            <th>Lp.</th>
                            <th>Nazwa i adres instytucji</th>
                            <th>Liczba studentów realizujących praktyki</th>
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
                                    ${element[0]}
                                </td>
                                <td>
                                    ${element[1]}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <%--Paginacja --%>
                <c:if test="${endIndex >1 }">                                            
                    <c:url var="firstUrl" value="/Praktyka/sprawozdanie/1" />
                    <c:url var="lastUrl" value="/Praktyka/sprawozdanie/${totalPages}" />
                    <c:url var="prevUrl" value="/Praktyka/sprawozdanie/${currentIndex - 1}" />
                    <c:url var="nextUrl" value="/Praktyka/sprawozdanie/${currentIndex + 1}" />

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
                                    <c:url var="pageUrl" value="/Praktyka/sprawozdanie/${i}" />
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