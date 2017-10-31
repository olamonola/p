<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
        <form:form method="post" modelAttribute="registerAccount" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Zarejestruj się</h2>
                </legend>
                <t:cinput path="login" label="Login" placeholder="Login"/>
                <t:cpassword path="password" label="Hasło" placeholder="Hasło"/>
                <t:cpassword path="repeatPassword" label="Powtórz hasło" placeholder="Hasło"/>
                <t:cinput path="Email" label="Email" placeholder="email"/>
                <t:cpassword path="secretCode" label="Kod rejestracji" placeholder=""/>
                <div class="row">
                    <div class="col-md-6"><div class="text-right">
                            <input type="submit" value="Zarejestruj" class="btn btn-primary" />
                        </div></div>
                </div>

            </fieldset>
        </form:form>     

    </jsp:body>
</t:layout>


