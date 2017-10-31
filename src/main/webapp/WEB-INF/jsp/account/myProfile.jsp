<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

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
        <h1>Mój profil</h1>

        <div class="row">
            <div class="col-md-1">
                Login:
            </div> 
            <div class="col-md-11">
                <b>${account.login}</b> 
            </div> 
        </div>
        <div class="row">
            <div class="col-md-1">
                Email:
            </div>
            <div class="col-md-11">
                <b>${account.email}</b>
            </div>
        </div>

        <sec:authorize access="hasAnyRole('ROLE_OPIEKUN_PRAKTYK', 'ROLE_ADMIN')" var="isAdminOrOpiekun" />
        <c:if test="${!isAdminOrOpiekun}">
            <div class="row">
                <div class="col-md-1">
                    <c:choose>
                        <c:when test="${not empty account.userProfile.firstName}">
                            Imię:
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-11">
                    <c:choose>
                        <c:when test="${not empty account.userProfile.firstName}">
                            <b>${account.userProfile.firstName}</b>
                        </c:when>
                        <c:otherwise>
                            Brak wprowadzonego imienia.
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="row">
                <div class="col-md-1">
                    <c:choose>
                        <c:when test="${not empty account.userProfile.lastName}">
                            Nazwisko:
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-11">
                    <c:choose>
                        <c:when test="${not empty account.userProfile.lastName}">
                            <b>${account.userProfile.lastName}</b>
                        </c:when>
                        <c:otherwise>
                            Brak wprowadzonego nazwiska.
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="row">
                <div class="col-md-1">
                    <c:choose>
                        <c:when test="${not empty account.userProfile.indexNo}">
                            Numer indeksu:
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-11">
                    <c:choose>
                        <c:when test="${not empty account.userProfile.indexNo}">
                            <b>${account.userProfile.indexNo}</b>
                        </c:when>
                        <c:otherwise>
                            Brak wprowadzonego numeru indeksu.
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-12">
                <c:choose>
                    <c:when test="${account.accountConfirmed}">
                        <b>Konto zostało potwierdzone.</b>
                    </c:when>
                    <c:otherwise>
                        <b>Konto nie zostało jeszcze potwierdzone.</b>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <c:choose>
                    <c:when test="${not empty account.accountRoleCollection}">
                        <b>
                            <c:forEach var="element" items="${account.accountRoleCollection}">
                                <c:if test= "${element.roleId.roleId == 1}">
                                    Posiada uprawnienia administratora. <br/>
                                </c:if>
                                <c:if test= "${element.roleId.roleId == 2}">
                                    Posiada uprawnienia opiekuna praktyk. <br/>
                                </c:if>
                                <c:if test= "${element.roleId.roleId == 3}">
                                    Posiada uprawnienia studenta ważne od
                                    <fmt:formatDate value="${element.roleStartTime}" pattern="dd-MM-yyyy" />
                                    do 
                                    <fmt:formatDate value="${element.roleEndTime}" pattern="dd-MM-yyyy" />.                   
                                    <br/>
                                </c:if>
                            </c:forEach>
                        </b>
                    </c:when>
                    <c:otherwise>
                        <b>Brak uprawnień.</b> Uprawnienia mogą zostać przyznane po zweryfikowaniu imienia, nazwiska oraz numeru indeksu. W tym celu skontaktuj się z opiekunem praktyk na uczelni.
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_STUDENT', 'ROLE_NONE', 'ROLE_EXPIRED')">
        <div class="text-right">
            <a class="btn btn-default" href="<c:url
                   value="/Konto/edycjaProfilu"/>">
                Edytuj
            </a>
        </div>
        </sec:authorize>

    </jsp:body>
</t:layout>


