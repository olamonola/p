<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<t:layout>
    <jsp:body>
        <c:if test="${param.errormsg != null}">
            <t:cMessageDanger msg="${param.errormsg}"/>
        </c:if>
        <c:if test="${param.msg != null}">
            <div class="alert alert-success" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-ok"></span>
                    ${param.msg}
                </div>
            </div>
        </c:if>
        <t:cMessageDanger msg="${errormsg}"/>
        <c:if test="${not empty msg}">           
            <div class="alert alert-success" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-ok"></span>
                    ${msg}
                </div>
            </div>
        </c:if>
        <h1>Zapomnaiłeś/aś hasła?</h1>
        <p>
            Aby zresetować zapomniane hasło, podaj swój adres e-mail, a wyślemy Ci dalsze instrukcje.
        </p>

        <form:form method="post" modelAttribute="myEmail" class="form-horizontal">
            <fieldset>
                <legend>
                </legend>

                <spring:bind path="email">
                    <div class="${status.error ? 'form-group has-error has-feedback' : 'form-group'}">
                        <label for="email" class="control-label col-sm-2">Email</label>
                        <div class="col-sm-4"> 
                            <form:input path="email" id="email" type="text" class="form-control" placeholder="email"></form:input>
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

                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/"/>">
                        Anuluj
                    </a>
                    <input type="submit" value="Wyślij" class="btn btn-primary" />
                </div>
            </fieldset>
        </form:form>   
    </jsp:body>
</t:layout>


