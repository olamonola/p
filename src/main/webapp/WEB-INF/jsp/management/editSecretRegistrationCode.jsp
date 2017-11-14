<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>


<t:layout>
    <jsp:body>
        <script src="<c:url value="/webjars/jquery/2.1.4/jquery.min.js"/>" ></script>
        <script src="<c:url value="/resources/static/js/show_or_hide_registration_code.js"/>" /></script>
        <c:if test="${param.errormsg != null}">           
        <div class="alert alert-danger" role="alert">	
            <div class="text-center">
                <span class="glyphicon glyphicon-remove"></span>
                ${param.errormsg}
            </div>
        </div>
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


    <form:form method="post" modelAttribute="secretCode" class="form-horizontal">
        <fieldset>
            <legend>
                <h2>Zmień kod rejestracji</h2>
            </legend>

            <spring:bind path="code">
                <div class="${status.error ? 'form-group has-error has-feedback' : 'form-group'}">
                    <label for="code" class="control-label col-sm-2">Kod rejestracji</label>
                    <div class="col-sm-4"> 
                        <form:input path="code" id="code" type="password" class="form-control" placeholder="Kod rejestracji"></form:input>
                        <c:if test="${status.error}">
                            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                        </c:if>                 
                    </div>
                    <div class="col-sm-6"> 
                        <div class="help-block">
                            <form:errors path="code"/></div>
                    </div>
                </div>
            </spring:bind>

            <div class="form-group">
                <div class="col-md-6 col-md-offset-2">
                    <input type="checkbox" name="showCode" id="showCode" value="showCode"> Pokaż kod<br>
                </div>
            </div>
            
            <div class="form-group codeshown" style="display:none;">
                <label for="code" class="control-label col-sm-2">Kod rejestracji</label>
                <div class="col-sm-4"> 
                    <input id="code2" type="text" class="form-control" placeholder="Kod rejestracji" value="${secretCode.code}" readonly/>
                </div>
            </div>

            <div class="text-right">
                <a class="btn btn-default" href="<c:url
                       value="/Zarzadzanie/kodRejestracji"/>">
                    Anuluj zmiany
                </a>
                <input type="submit" value="Zmień" class="btn btn-primary" />
            </div>
        </fieldset>
    </form:form>

</jsp:body>
</t:layout>