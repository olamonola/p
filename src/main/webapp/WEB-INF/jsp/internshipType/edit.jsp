<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<t:layout>
    <jsp:body>
        <t:cMessageDanger msg="${msg}"/>
        <form:form method="post" modelAttribute="internshipType" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Zapisz typ praktyk</h2>
                </legend>
                <spring:bind path="type">
                    <div class="${status.error ? 'form-group has-error has-feedback' : 'form-group'}">
                        <label for="name" class="control-label col-sm-2">Nazwa typu praktyk</label>
                        <div class="col-sm-4"> 
                            <form:input path="type" id="type" type="text" class="form-control" placeholder="typ praktyk"></form:input>
                            <c:if test="${status.error}">
                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            </c:if>                 
                        </div>
                            <div class="col-sm-6"> 
                                <div class="help-block">
                                <form:errors path="type"/></div>
                        </div>
                    </div>   
                </spring:bind> 
                <spring:bind path="duration">
                    <div class="${status.error ? 'form-group has-error has-feedback' : 'form-group'}">
                        <label for="name" class="control-label col-sm-2">Czas trwania praktyk</label>
                        <div class="col-sm-4"> 
                            <form:input path="duration" id="duration" type="text" class="form-control" placeholder="czas trwania"></form:input>
                            <c:if test="${status.error}">
                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            </c:if>                 
                        </div>
                            <div class="col-sm-6"> 
                                <div class="help-block">
                                <form:errors path="duration"/></div>
                        </div>
                    </div>   
                </spring:bind> 
                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/TypPraktyk/lista/1"/>">
                        Powrót do listy
                    </a>
                    <input type="submit" value="Zapisz" class="btn btn-primary" />
                </div>
            </fieldset>
        </form:form>     

    </jsp:body>
</t:layout>


