<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<t:layout>
    <jsp:body>
        <div class="container">
            <div class="jumbotron">
                <h1>Praktyki </h1>

                <h2>Praktyki na uczelni</h2>

            </div>
            <t:cMessageDanger msg="${errormsg}"/>
            <c:if test="${not empty msg}">           
                <div class="alert alert-success" role="alert">	
                    <div class="text-center">
                        <span class="glyphicon glyphicon-ok"></span>
                        ${msg}
                    </div>
                </div>
            </c:if>

        </div>
    </jsp:body>
</t:layout>

