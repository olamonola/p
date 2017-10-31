<%@tag description="input" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="path" required="true" rtexprvalue="true" %>
<%@ attribute name="label" required="true" rtexprvalue="true" %>
<%@ attribute name="placeholder" required="true" rtexprvalue="true" %>
<%@ attribute name="glyphicon" required="true" rtexprvalue="true" %>





<div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon ${glyphicon}"></i></span>
						<form:input path="${path}" id="${path}" class="form-control input-sm" placeholder="${placeholder}"></form:input>
                    </div>