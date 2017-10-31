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
                <h3 class="panel-title"><span class="glyphicon glyphicon-search"></span>Opcje wyszukiwania studentów</h3>
            </div>
            <div class="panel-body">


                <form:form method="post" modelAttribute="searchStudents" class="form-horizontal">

                    <t:input-sm path="firstName" label="Imię studenta" placeholder="Imię studenta" glyphicon="glyphicon-education"/>
                    <t:input-sm path="lastName" label="Nazwisko studenta" placeholder="Nazwisko studenta" glyphicon="glyphicon-education"/>
                    <t:input-sm path="indexNo" label="Numer indeksu" placeholder="Numer indeksu" glyphicon="glyphicon-star-empty"/>
                    <t:input-sm path="login" label="Login" placeholder="Login" glyphicon="glyphicon-user"/>
                    <t:input-sm path="email" label="Email" placeholder="Email" glyphicon="glyphicon-envelope"/>

                    <form:checkbox path="accountConfirmed" id="accountConfirmed" /> Tylko potwierdzone dane<br>
                    <form:checkbox path="accountNotConfirmed" id="accountNotConfirmed" /> Tylko niepotwierdzone dane<br>
                    <form:checkbox path="studentRoleExpired" id="studentRoleExpired" onClick="javascript:uncheckSecondary2(this);"/> Również studenci, którzy zakończyli edukację oraz ci, których uprawnienia są jeszcze przez terminem upoważnienia<br>
                    <form:checkbox path="studentsWithoutRole" id="studentsWithoutRole" onClick="javascript:uncheckSecondary(this);"/> Tylko studenci bez uprawnień<br>
                    <script language="javascript" type="text/javascript">
                        function uncheckSecondary2(obj)
                        {
                            if (obj.checked === true)
                            {
                                document.getElementById("studentsWithoutRole").checked = false;
                            }
                        }
                        function uncheckSecondary(obj)
                        {
                            if (obj.checked === true)
                            {
                                document.getElementById("studentRoleExpired").checked = false;
                            }
                        }
                    </script>

                    <div class="text-center">
                        <form:button type="submit" class="btn btn-info">
                            <span class="glyphicon glyphicon-search"></span> Szukaj
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
                            <th>Imię i nazwisko</th>
                            <th>Numer indeksu</th>
                            <th>Login</th>
                            <th>Email</th>
                            <th>Stan</th>
                            <th>Akcja</th>
                        </tr>

                    </thead>
                    <tbody>
                        <c:forEach var="element" items="${elements}">
                            <tr>
                                <td>
                                    <a class="btn btn-link" href="<c:url
                                           value="/Studenci/profil/${element.accountId}/1"/>">
                                       ${element.firstName} 
                                       ${element.lastName}
                                    </a></td>
                                <td>${element.indexNo}</td>
                                <td>${element.login}</td>
                                <td>${element.email}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${element.accountConfirmed}">
                                            Potwierdzono dane
                                        </c:when>
                                        <c:otherwise>
                                            Nie potwierdzono danych
                                        </c:otherwise>
                                    </c:choose>
                                    <br>
                                    <c:choose>
                                        <c:when test="${element.verified}">
                                            Zweryfikowano mailowo
                                        </c:when>
                                        <c:otherwise>
                                            Nie zweryfikowano mailowo
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a class="btn btn-default btn-xs" href="<c:url
                                           value="/Studenci/edycjaDanych/${element.accountId}"/>">
                                        <span class="glyphicon glyphicon-edit" aria-label="Edytuj"></span>
                                        Edytuj dane
                                    </a>
                                    <br/>

                                    <c:choose>
                                        <c:when test="${empty element.roleId}">
                                            <c:if test = "${element.accountConfirmed}">
                                            <a class="btn btn-primary btn-xs" href="<c:url
                                                   value="/Studenci/edycjaRoli/${element.accountId}"/>">
                                                <span class="glyphicon glyphicon-eye-open" aria-label="Edytuj"></span>
                                                Upoważnij
                                            </a>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="btn btn-default btn-xs" href="<c:url
                                                   value="/Studenci/edycjaRoli/${element.accountId}"/>">
                                                <span class="glyphicon glyphicon-eye-open" aria-label="Edytuj"></span>
                                                Edutuj upoważnienia
                                            </a>
                                        </c:otherwise>
                                    </c:choose>


                                    <c:choose>
                                        <c:when test = "${!element.accountConfirmed}">
                                            <c:url var="potwierdzUrl" value="/Studenci/potwierdz/${element.accountId}"/>
                                            <form:form action="${potwierdzUrl}" modelAttribute="confirmStudent" method="post">
                                                <form:input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                            <form:button class="btn btn-primary btn-xs" type="submit"> 
                                                    <span class="glyphicon glyphicon-ok" aria-label="Potwierdź"></span>Potwierdź dane
                                                </form:button>     
                                            </form:form>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url var="anulujPotwierdzenieUrl" value="/Studenci/anulujPotwierdzenie/${element.accountId}"/>
                                            <form:form action="${anulujPotwierdzenieUrl}" modelAttribute="confirmStudent" method="post">
                                                <form:input type="hidden" id="pageNumber" name="pageNumber" path="pageNumber" value="${pageNumber}" />
                                                <form:button class="btn btn-default btn-xs" type="submit"> 
                                                    <span class="glyphicon glyphicon-remove" aria-label="Anuluj potwierdzenie"></span>Anuluj potwierdzenie
                                                </form:button> 
                                            </form:form>
                                        </c:otherwise>
                                    </c:choose>






                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <%--Paginacja --%>
                <c:if test="${endIndex >1 }">                                            
                    <c:url var="firstUrl" value="/Studenci/lista/1" />
                    <c:url var="lastUrl" value="/Studenci/lista/${totalPages}" />
                    <c:url var="prevUrl" value="/Studenci/lista/${currentIndex - 1}" />
                    <c:url var="nextUrl" value="/Studenci/lista/${currentIndex + 1}" />

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
                                    <c:url var="pageUrl" value="/Studenci/lista/${i}" />
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