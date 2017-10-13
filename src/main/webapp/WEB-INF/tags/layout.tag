<%@tag description="layout" pageEncoding="UTF-8"%>
<%@attribute name="scripts" fragment="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <link rel="icon" href="<c:url value="/resources/static/images/czerwony.jpg"/>"/>
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
                        <img src="<c:url value="/resources/static/images/czerwony.png"/>"/>                       
                    </a>
                    <a class="navbar-brand" href="/">Praktyki</a>
                </div>

                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">			
						<sec:authentication var="userName" property="principal" />
						<c:choose>
							<c:when test="${userName =='anonymousUser'}">
								<li>
                            <a href="<c:url value="../../../login"/>">Zaloguj</a>
                        </li>      
							</c:when>
							<c:otherwise>
							<li>
                            <a href="<c:url value="../../../logout"/>">Wyloguj</a>
                        </li>
						</c:otherwise>
					</c:choose>
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
        <jsp:invoke fragment="scripts"/>
</body>
</html>
