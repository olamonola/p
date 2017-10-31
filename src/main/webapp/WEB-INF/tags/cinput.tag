<%@tag description="input" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="path" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="placeholder" required="true" rtexprvalue="true" %>

<spring:bind path="${path}">
                    <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group'}">
                        <label for="${path}" class="control-label col-sm-2">${label}</label>
                        <div class="col-sm-4"> 
                            <form:input path="${path}" id="${path}" class="form-control" placeholder="${placeholder}"></form:input>
                            <c:if test="${status.error}">
                                <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                            </c:if>                 
                        </div>
                            <div class="col-sm-6"> 
                                <div class="help-block">
                                <form:errors path="${path}"/></div>
                        </div>
                    </div>   
</spring:bind> 