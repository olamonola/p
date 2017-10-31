<%@tag description="layout" pageEncoding="UTF-8"%>
<%@attribute name="scripts" fragment="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="pl">
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta name="description" content="Praktyki"/>
        <meta name="author" content="Aleksandra Woźniak"/>
        <link rel="icon" href="<c:url value="/resources/static/images/czerwony.png"/>"/>
        <title>Praktyki</title>

        <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.4/css/bootstrap.min.css"/>" />
        <link rel="stylesheet" href="<c:url value="/resources/static/css/custom.css"/>"/>
		<link rel="stylesheet" href="<c:url value="/resources/static/css/datepicker.css"/>"/>
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">

                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Przełącz nawigację</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="<c:url value="/"/>">
                        <img src="<c:url value="/resources/static/images/czerwony.png"/>" style="width: 20px;"/>                       
                    </a>
                    <a class="navbar-brand" href="/">Praktyki</a>
                </div>

                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
					<sec:authorize access="hasAnyRole('ROLE_OPIEKUN_PRAKTYK', 'ROLE_ADMIN')">
                        <li class="dropdown">
                            <a href="/" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Zarządzanie aplikacją<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="<c:url value="/Fakultet/lista/1"/>">Kierunki</a></li>
                                <li><a href="<c:url value="/System/lista/1"/>">Systemy studiów</a></li>
								<li><a href="<c:url value="/Specjalizacja/lista/1"/>">Specjalności</a></li>
								<li><a href="<c:url value="/TypPraktyk/lista/1"/>">Typy praktyk</a></li>
								<li><a href="<c:url value="/Zarzadzanie/kodRejestracji"/>">Kod rejestracji</a></li>
								<li><a href="<c:url value="/Zarzadzanie/nazwaSerwera"/>">Nazwa serwera</a></li>
								<li><a href="<c:url value="/Zarzadzanie/reprezentant"/>">Reprezentant</a></li>
                            </ul>
                        </li>
						<li>
                            <a href="<c:url value="/Studenci/lista/1"/>">Studenci</a>
                        </li>
						<li>
                            <a href="<c:url value="/Firma/lista/1"/>">Firmy</a>
                        </li>	
						<li class="dropdown">
                            <a href="/" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Praktyki<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="<c:url value="/Praktyka/listaPraktyk/1"/>">Praktyki</a></li>
                                <li><a href="<c:url value="/Praktyka/wykaz/1"/>">Wykaz</a></li>
								<li><a href="<c:url value="/Praktyka/sprawozdanie/1"/>">Sprawozdanie</a></li>
                            </ul>
                        </li>
						</sec:authorize>
						
						<sec:authorize access="hasAnyRole('ROLE_STUDENT')">
						<li>
                            <a href="<c:url value="/Praktyka/mojePraktyki/1"/>">Moje praktyki</a>
                        </li>
						<li>
                            <a href="<c:url value="/Firma/lista/1"/>">Firmy</a>
                        </li>
						</sec:authorize>
						
						<sec:authentication var="userName" property="principal" />
						
						<c:if test="${userName !='anonymousUser'}">
							
							<li class="dropdown">
								<a href="<c:url value="/Konto/profil"/>" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Moje konto<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="<c:url value="/Konto/profil"/>">Wyświetl</a></li>
									<sec:authorize access="hasAnyRole('ROLE_STUDENT', 'ROLE_NONE')">
									<li><a href="<c:url value="/Konto/edycjaProfilu"/>">Edytuj</a></li>
									</sec:authorize>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_OPIEKUN_PRAKTYK')">
									<li><a href="<c:url value="/Konto/zmianaMaila"/>">Zmień email</a></li>
									</sec:authorize>
									<li><a href="<c:url value="/Konto/zmianaHasla"/>">Zmień hasło</a></li>
								</ul>
							</li>
						</c:if>
						
						<c:choose>
							<c:when test="${userName =='anonymousUser'}">
								<li>
                            <a href="<c:url value="/login"/>">Zaloguj</a>
                        </li>      
							</c:when>
							<c:otherwise>
							<li>
                            <a href="<c:url value="/logout"/>">Wyloguj</a>
                        </li>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${userName =='anonymousUser'}">
							<li>
								<a href="<c:url value="/Konto/rejestracja"/>">Zarejestruj</a>
							</li>      
					</c:if>
						 
						
						

                    </ul>
                </div>
            </div>
        </nav>

        <div id="body" class="container">
            <jsp:doBody/>
        </div>

        <footer class="footer">
            <div class="panel-footer">
                <div class="text-center">
                    <p>
                        © Copyright 2017 Aleksandra Woźniak<br/>
                        All Rights Reserved.
                    </p>
                </div>
            </div>
        </footer>

        <script src="<c:url value="/webjars/jquery/2.1.4/jquery.min.js"/>" ></script>	 
        <script src="<c:url value="/webjars/bootstrap/3.3.4/js/bootstrap.min.js"/>" /></script>
		<script src="<c:url value="/resources/static/js/bootstrap-datepicker.js"/>" /></script>
        <jsp:invoke fragment="scripts"/>
</body>
</html>
