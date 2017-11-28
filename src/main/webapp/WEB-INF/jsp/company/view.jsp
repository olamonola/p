<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout>
    <jsp:body>
        <h1>Widok firmy</h1>
        <c:if test="${not empty msg}">           
            <div class="alert alert-success" role="alert">	
                <div class="text-center">
                    <span class="glyphicon glyphicon-ok"></span>
                    ${msg}
                </div>
            </div>
        </c:if>
        <table class="table table-responsive">
            <tbody>
                <tr>
                    <td class="col-md-2">Nazwa:</td>
                    <td class="col-md-10"><b>${company.name}</b></td>
                </tr>
                <tr>
                    <td class="${empty company.phone ? 'grey' : ''}">
                        Telefon:
                    </td>
                    <td><b>${company.phone}</b></td>
                </tr>
                <tr>
                    <td class="${empty company.street ? 'grey' : ''}">
                        Ulica:
                    </td>
                    <td><b>${company.street}</b></td>
                </tr>
                <tr>
                    <td>Numer domu:</td>
                    <td><b>${company.streetNo}</b></td>
                </tr>
                <tr>
                    <td>Miejscowość:</td>
                    <td><b>${company.city}</b></td>
                </tr>
                <tr>
                    <td>Kod pocztowy:</td>
                    <td><b>${company.zip}</b></td>
                </tr>
                <tr>
                    <td class="${empty company.comments ? 'grey' : ''}">Komentarze:</td>
                    <td><b>${company.comments}</b></td>
                </tr>
            </tbody>         
        </table>
        <a class="btn btn-default" href="<c:url
               value="/Firma/lista/1"/>">
            Powrót do listy firm
        </a>
    </jsp:body>
</t:layout>