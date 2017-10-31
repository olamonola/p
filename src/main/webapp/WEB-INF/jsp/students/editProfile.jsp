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
        
        <form:form method="post" modelAttribute="studentData" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Edytuj dane studenta</h2>
                </legend>
                    <t:cinput path="firstName" label="Imię" placeholder="Imię"/>
                    <t:cinput path="lastName" label="Nazwisko" placeholder="Nazwisko"/>
                    <t:cinput path="indexNo" label="Numer indeku" placeholder=""/>

                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/Studenci/lista/1"/>">
                        Anuluj
                    </a>
                    <input type="submit" value="Zapisz" class="btn btn-primary" />
                </div>
            </fieldset>
        </form:form>   
    </jsp:body>
</t:layout>


