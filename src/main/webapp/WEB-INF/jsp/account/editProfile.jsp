<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

        <sec:authorize access="hasRole('ROLE_STUDENT')" var="isStudent" />
        <form:form method="post" modelAttribute="myProfile" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Edytuj profil</h2>
                </legend>

                <spring:bind path="email">
                    <div class="${status.error ? 'form-group has-error has-feedback' : 'form-group'}">
                        <label for="email" class="control-label col-sm-2">Email</label>
                        <div class="col-sm-4"> 
                            <form:input path="email" id="email" type="text" oninput="showPasswordInput()" class="form-control" placeholder=""></form:input>
                            <c:if test="${status.error}">
                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            </c:if>                 
                        </div>
                        <div class="col-sm-6"> 
                            <div class="help-block">
                                <form:errors path="email"/></div>
                        </div>
                    </div>   
                </spring:bind> 
                <spring:bind path="password">
                    <c:choose>
                        <c:when test="${status.error}">
                            <t:cpassword path="password" label="Hasło*" placeholder=""/>
                        </c:when>
                        <c:otherwise>
                            <div id="hiddenPassword" style="display:none;">
                                <t:cpassword path="password" label="Hasło*" placeholder=""/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </spring:bind> 
                <c:choose>
                    <c:when test="${myProfile.accountConfirmed}">
                        <form:input type="hidden" id="firstName" name="firstName" path="firstName" value="${myProfile.firstName}" />
                        <form:input type="hidden" id="lastName" name="lastName" path="lastName" value="${myProfile.lastName}" />
                        <form:input type="hidden" id="indexNo" name="indexNo" path="indexNo" value="${myProfile.indexNo}" />
                    </c:when>
                    <c:otherwise>
                        <t:cinput path="firstName" label="Imię" placeholder="Imię"/>
                        <t:cinput path="lastName" label="Nazwisko" placeholder="Nazwisko"/>
                        <t:cinput path="indexNo" label="Numer indeku" placeholder=""/>
                    </c:otherwise>
                </c:choose>

                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/Konto/profil"/>">
                        Anuluj
                    </a>
                    <input type="submit" value="Zapisz" class="btn btn-primary" />
                </div>
            </fieldset>
        </form:form>   
        <script src="<c:url value="/webjars/jquery/2.1.4/jquery.min.js"/>" ></script>
        <script src="<c:url value="/resources/static/js/password_needed_for_email_change.js"/>" /></script>
    </jsp:body>
</t:layout>


