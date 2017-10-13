<%@tag description="custom message" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="msg" required="true" rtexprvalue="true" %>
        <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.4/css/bootstrap.min.css"/>" />
        <link rel="stylesheet" href="<c:url value="/resources/static/css/custom.css"/>"/>
<c:if test="${not empty msg}">           
            <div class="alert alert-danger" role="alert">	<div class="text-center">
                    <span class="glyphicon glyphicon-remove"></span>
                    ${msg}
                </div>
            </div>
        </c:if>