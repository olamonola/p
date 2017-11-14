<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>


<t:layout>
    <jsp:body>
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


        <form:form method="post" modelAttribute="server" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Zmień nazwę serwera</h2>
                </legend>
                <t:cinput path="name" label="Nazwa serwera" placeholder=""/>
                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/Zarzadzanie/nazwaSerwera"/>">
                        Anuluj zmiany
                    </a>
                    <input type="submit" value="Zmień" class="btn btn-primary" />
                </div>
            </fieldset>
        </form:form>

    </jsp:body>
</t:layout>