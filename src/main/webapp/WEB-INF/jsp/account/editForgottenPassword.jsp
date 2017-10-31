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

        <form:form method="post" modelAttribute="resetPassword" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Zmień hasło</h2>
                </legend>
                <t:cpassword path="password" label="Nowe hasło*" placeholder=""/>
                <t:cpassword path="repeatPassword" label="Powtórz nowe hasło*" placeholder=""/>
                <form:input type="hidden" id="accountId" name="accountId" path="accountId" value="${resetPassword.accountId}" />
                <form:input type="hidden" id="token" name="token" path="token" value="${resetPassword.token}" />
                
                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/"/>">
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


