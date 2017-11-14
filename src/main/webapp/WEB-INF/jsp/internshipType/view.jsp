<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout>
    <jsp:body>
        <h1>Widok typu praktyk</h1>
        <c:if test="${not empty msg}">           
            <div class="alert alert-success" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-ok"></span>
                    ${msg}
                </div>
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-2">Nazwa:</div>
            <div class="col-md-10"><b>${internshipType.type}</b></div>
        </div>
        <div class="row">
            <div class="col-md-2">Czas trwania:</div>
            <div class="col-md-10"><b>${internshipType.duration}</b></div>
        </div>
        <a class="btn btn-default" href="<c:url
               value="/TypPraktyk/lista/1"/>">
            Powrót do listy 
        </a>
    </jsp:body>
</t:layout>