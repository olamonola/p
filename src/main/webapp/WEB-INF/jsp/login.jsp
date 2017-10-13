<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>


<t:layout>
    <jsp:body>

       <c:if test="${param.error != null}">
            <t:cMessageDanger msg="Błędne hasło lub nazwa użytkownika."/>
        </c:if>
        <c:if test="${param.logout != null}">
            <div class="alert alert-success" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-ok"></span>
                    Użytkownik został wylogowany.
                </div>
            </div>
        </c:if>
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
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Zaloguj się</div>
                    <div class="panel-body">
                        <form:form action="/login" method="post" class="form-horizontal">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label for="username" class="control-label col-sm-4">
                                    <span class="glyphicon glyphicon-user"></span> Nazwa użytkownika</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" name="username" id="username" placeholder="Login">
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="password" class="control-label col-sm-4"><span class="glyphicon glyphicon-eye-open"></span> Hasło</label>
                                <div class="col-sm-8">
                                    <input type="password" class="form-control" name="password" id="password" placeholder="Hasło">
                                </div>
                            </div>
                            
                            
                            <div class="text-right"><button type="submit" value="Zaloguj" class="btn btn-primary">
                                    Zaloguj
                                </button></div>
                            </form:form>
                    </div>
                </div>
            </div>
        </jsp:body>
    </t:layout>