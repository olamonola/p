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
        <form:form method="post" modelAttribute="company" class="form-horizontal">
            <fieldset>
                <legend>
                    <h2>Zapisz firmę</h2>
                </legend>
                <t:cinput path="name" label="Nazwa firmy*" placeholder="Nazwa firmy"/>
                <t:cinput path="phone" label="Telefon" placeholder="Telefon*"/>
                <t:cinput path="street" label="Ulica" placeholder="Ulica"/>
                <t:cinput path="streetNo" label="Numer budynku*" placeholder="Numer budynku"/>
                <t:cinput path="city" label="Miejscowość*" placeholder="Miejscowość"/>
                <t:cinput path="zip" label="Kod pocztowy*" placeholder="00-000"/>
                <t:cinput path="comments" label="Komentarze" placeholder=""/>
                <div class="text-right">
                    <a class="btn btn-default" href="<c:url
                           value="/Firma/lista/1"/>">
                        Powrót do listy
                    </a>
                    <input type="submit" value="Zapisz" class="btn btn-primary" />
                </div>
            </fieldset>
        </form:form>     

    </jsp:body>
</t:layout>


