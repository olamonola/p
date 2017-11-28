<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<t:layout>
    <jsp:body>
        <c:choose>
            <c:when test="${empty internship.internshipId}"> 
                <div class="jumbotron">
                    <h2>
                        <span class="glyphicon glyphicon-plus" aria-hidden="true">
                        </span> 
                        Dodaj nową praktykę
                    </h2>
                    <p>
                        Wypełnij poniższy formularz. Dane firmy zostały uzupełnione. 
                        Jeżeli nie są poprawne, zmień je tutaj.
                    </p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="jumbotron">
                    <h2>
                        <span class="glyphicon glyphicon-edit" aria-hidden="true">
                        </span> 
                        Czy chcesz zmienić dane dotyczące tej praktyki?
                    </h2>
                    <p>
                        Jeśli chcesz zmienić dane dotyczące tej praktyki, wypełnij poniższy formularz i zatwierdź go
                        przyciskiem poniżej.
                    </p>
                </div>
            </c:otherwise>
        </c:choose>

        <t:cMessageDanger msg="${msg}"/>
        <form:form method="post" modelAttribute="internship" class="form-horizontal">
            <fieldset>
                <legend>
                    <c:choose>
                        <c:when test="${empty internship.internshipId}"> 
                            <h2>Dodaj praktykę</h2>

                        </c:when>
                        <c:otherwise>
                            <h2>Edytuj praktykę</h2>
                        </c:otherwise>
                    </c:choose>
                </legend>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Jeśli uzyskałeś potwierdzone zwolnienie z praktyk, zaznacz poniższe pole wyboru.</h3>
                    </div>
                    <div class="panel-body">
                        Jeśli uzyskałeś potwierdzone zwolnienie z praktyk, zaznacz poniższe pole wyboru, a następnie wypełnij formularz.<br><br>
                        <form:checkbox path="exemption" id="exemption" value="Jestem zwolniony z praktyk" /> Jestem zwolniony z praktyk<br><br>
                    </div>
                </div>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Informacje o twoich studiach</h3>
                    </div>
                    <div class="panel-body">
                        <t:cinput path="year" label="Rok studiów (1 - 4)*" placeholder="1"/>
                        <%--Wybór systemu studiów--%>
                        <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group'}">
                            <label for="system" class="control-label col-sm-2">
                                System studiów*
                            </label>
                            <div class="col-sm-4">
                                <form:select path="systemSystemId.systemId" class="form-control" id="system">
                                    <c:forEach items="${systems}" var="systems">
                                        <form:option value="${systems.systemId}">${systems.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="col-sm-6"> 
                                <div class="help-block">
                                    <form:errors path="${systemSystemId.systemId}"/>
                                </div>
                            </div>
                        </div>

                        <%--Wybór fakultetu--%>
                        <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group'}">
                            <label for="faculty" class="control-label col-sm-2">
                                Kierunek*
                            </label>
                            <div class="col-sm-4">
                                <form:select path="facultyId.facultyId" class="form-control" id="faculty">
                                    <c:forEach items="${faculties}" var="faculties">
                                        <form:option value="${faculties.facultyId}">${faculties.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="col-sm-6"> 
                                <div class="help-block">
                                    <form:errors path="${facultyId.facultyId}"/>
                                </div>
                            </div>
                        </div>



                        <%--Wybór specjalizacji--%>
                        <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group'}">
                            <label for="specialization" class="control-label col-sm-2">
                                Specjalność*
                            </label>
                            <div class="col-sm-4">
                                <form:select path="specializationId.specializationId" class="form-control" id="specialization">
                                    <c:forEach items="${specializations}" var="specializations">
                                        <form:option value="${specializations.specializationId}">${specializations.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="col-sm-6"> 
                                <div class="help-block">
                                    <form:errors path="${specializationId.specializationId}"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Informacje o praktykach</h3>
                    </div>
                    <div class="panel-body">
                        <div class="not_exempted">
                            <%--Datepicker - potrzebujemy 2: na rozpoczęcie i zakońćzenie praktyk--%>
                            <spring:bind path="startDate">
                                <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group has-feedback'}">
                                    <label for="startDate" class="control-label col-sm-2">
                                        Rozpoczęcie praktyk
                                    </label>
                                    <div class="col-sm-4">
                                        <div class="hero-unit">
                                            <input type="text" path="startDate" placeholder="dd-mm-rrrr" class="form-control" name="startDate" id="startDate" value = "<fmt:formatDate value="${internship.startDate}" pattern="dd-MM-yyyy" />"/>
                                            <i class="glyphicon glyphicon-calendar form-control-feedback"></i>
                                        </div>
                                    </div>
                                    <div class="col-sm-6"> 
                                        <div class="help-block">
                                            <form:errors path="startDate"/></div>
                                    </div>
                                </div>
                            </spring:bind>


                            <spring:bind path="endDate">
                                <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group has-feedback'}">
                                    <label for="endDate" class="control-label col-sm-2">
                                        Zakończenie praktyk
                                    </label>
                                    <div class="col-sm-4">
                                        <div class="hero-unit">
                                            <input type="text" path="endDate" placeholder="dd-mm-rrrr" class="form-control" name="endDate" id="endDate" value = "<fmt:formatDate value="${internship.endDate}" pattern="dd-MM-yyyy" />"/>
                                            <i class="glyphicon glyphicon-calendar form-control-feedback"></i>
                                        </div>
                                    </div>
                                    <div class="col-sm-6"> 
                                        <div class="help-block">
                                            <form:errors path="endDate"/></div>
                                    </div>
                                </div>
                            </spring:bind>
                        </div>

                        <%--Wybór typu praktyk--%>
                        <div class="${status.error ? 'form-group has-error has-feedback' : ' form-group'}">
                            <label for="internshipTypes" class="control-label col-sm-2">
                                Typ praktyk*
                            </label>
                            <div class="col-sm-4">
                                <form:select path="internshipTypeId.internshipTypeId" class="form-control" id="internshipType">
                                    <c:forEach items="${internshipTypes}" var="internshipTypes">
                                        <form:option value="${internshipTypes.internshipTypeId}">${internshipTypes.type}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="col-sm-6"> 
                                <div class="help-block">
                                    <form:errors path="${internshipTypeId.internshipTypeId}"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="not_exempted">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Dyrektor (Kierownik) firmy lub upoważniony przezeń pracownik</h3>
                        </div>
                        <div class="panel-body">
                            <t:cinput path="companyOwnerFirstName" label="Imię" placeholder="Imię"/>
                            <t:cinput path="companyOwnerLastName" label="Nazwisko" placeholder="Nazwisko"/>
                            <t:cinput path="companyOwnerPosition" label="Stanowisko" placeholder="Stanowisko"/>
                        </div>
                    </div>



                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Opiekun zakładowy praktyk</h3>
                        </div>
                        <div class="panel-body">
                            <t:cinput path="companyInternshipAdministratorFirstName" label="Imię opiekuna zakładowego" placeholder="Imię opiekuna zakładowego"/>
                            <t:cinput path="companyInternshipAdministratorLastName" label="Nazwisko opiekuna zakładowego" placeholder="Nazwisko opiekuna zakładowego"/>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">Dane firmy</h3>
                    </div>
                    <div class="panel-body">
                        <t:disabledinput path="companyName" label="Nazwa firmy" />
                        <t:disabledinput path="companyPhone" label="Telefon firmy" />
                        <t:disabledinput path="companyStreet" label="Ulica firmy" />
                        <t:disabledinput path="companyStreetNo" label="Numer budynku firmy" />
                        <t:disabledinput path="companyCity" label="Miejscowość firmy" />
                        <t:disabledinput path="companyZip" label="Kod pocztowy firmy"/>

                    </div>
                </div>

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">Komentarz odnośnie praktyk (do 150 znaków)</h3>
                    </div>
                    <div class="panel-body">
                        <t:cinput path="comments" label="Komentarz" placeholder=""/>
                    </div>
                </div>


                <div class="text-right">
                    <sec:authorize access="!hasRole('ROLE_STUDENT')">
                        <a class="btn btn-default" href="<c:url
                               value="/Praktyka/listaPraktyk/1"/>">
                            Powrót do listy
                        </a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <a class="btn btn-default" href="<c:url
                               value="/Firma/lista/1"/>">
                            Powrót do listy firm
                        </a>
                    </sec:authorize>

                    <input type="submit" value="Zapisz" class="btn btn-primary">
                </div>
            </fieldset>
        </form:form>     
        <script src="<c:url value="/webjars/jquery/2.1.4/jquery.min.js"/>" ></script>	 
        <script src="<c:url value="/webjars/bootstrap/3.3.4/js/bootstrap.min.js"/>" /></script>
    <script src="<c:url value="/resources/static/js/bootstrap-datepicker.js"/>" /></script>
<script src="<c:url value="/resources/static/js/calenders.js"/>" /></script>
<script src="<c:url value="/resources/static/js/hidden_exemption_inputs.js"/>" /></script>
</jsp:body>
</t:layout>


