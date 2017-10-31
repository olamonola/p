<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

        <form:form method="post" modelAttribute="studentRole" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Edytuj uprawnienia studenta (${name} ${lastName})</h2>
                </legend>

                <%--Datepicker - potrzebujemy 2: na rozpoczęcie i zakońćzenie roli--%>
                        <spring:bind path="roleStartTime">
                            <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group has-feedback'}">
                                <label for="roleStartTime" class="control-label col-sm-2">
                                    Uprawnienia ważne od
                                </label>
                                <div class="col-sm-4">
                                    <div class="hero-unit">
                                        <input type="text" path="roleStartTime" placeholder="dd-mm-rrrr" class="form-control" name="roleStartTime" id="roleStartTime" value = "<fmt:formatDate value="${studentRole.roleStartTime}" pattern="dd-MM-yyyy" />"/>
                                   <i class="glyphicon glyphicon-calendar form-control-feedback"></i>
                                    </div>
                                </div>
                                <div class="col-sm-6"> 
                                    <div class="help-block">
                                        <form:errors path="roleStartTime"/></div>
                                </div>
                            </div>
                        </spring:bind>
                        
                        <spring:bind path="roleEndTime">
                            <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group has-feedback'}">
                                <label for="roleEndTime" class="control-label col-sm-2">
                                    Uprawnienia ważne do
                                </label>
                                <div class="col-sm-4">
                                    <div class="hero-unit">
                                        <input type="text" path="roleEndTime" placeholder="dd-mm-rrrr" class="form-control" name="roleEndTime" id="roleEndTime" value = "<fmt:formatDate value="${studentRole.roleEndTime}" pattern="dd-MM-yyyy" />"/>
                                   <i class="glyphicon glyphicon-calendar form-control-feedback"></i>
                                    </div>
                                </div>
                                <div class="col-sm-6"> 
                                    <div class="help-block">
                                        <form:errors path="roleEndTime"/></div>
                                </div>
                            </div>
                        </spring:bind>

                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/Studenci/lista/1"/>">
                        Anuluj/Powrót
                    </a>
                    <input type="submit" value="Zapisz" class="btn btn-primary" />
                </div>
            </fieldset>
        </form:form>   
        <!-- Load jQuery and bootstrap datepicker scripts -->
        <script src="<c:url value="/webjars/jquery/2.1.4/jquery.min.js"/>" ></script>	 
        <script src="<c:url value="/webjars/bootstrap/3.3.4/js/bootstrap.min.js"/>" /></script>
    <script src="<c:url value="/resources/static/js/bootstrap-datepicker.js"/>" /></script>
<script type="text/javascript">

    $(document).ready(function () {

        $('#roleStartTime').datepicker({
            format: "dd-mm-yyyy"
        });
        $('#roleEndTime').datepicker({
            format: "dd-mm-yyyy"
        });

    });
</script>
</jsp:body>
</t:layout>


