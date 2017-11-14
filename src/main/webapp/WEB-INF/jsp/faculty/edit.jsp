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
        <form:form method="post" modelAttribute="faculty" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Zapisz kierunek</h2>
                </legend>
                <spring:bind path="name">
                    <div class="${status.error ? 'form-group has-error has-feedback' : 'form-group'}">
                        <label for="name" class="control-label col-sm-2">Nazwa kierunku</label>
                        <div class="col-sm-4"> 
                            <form:input path="name" id="name" type="text" class="form-control" placeholder="kierunek"></form:input>
                            <c:if test="${status.error}">
                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            </c:if>                 
                        </div>
                            <div class="col-sm-6"> 
                                <div class="help-block">
                                <form:errors path="name"/></div>
                        </div>
                    </div>   
                </spring:bind> 
                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/Fakultet/lista/1"/>">
                        Powrót do listy
                    </a>
                    <input type="submit" value="Zapisz" class="btn btn-primary" />
                </div>
            </fieldset>
        </form:form>     

    </jsp:body>
</t:layout>


