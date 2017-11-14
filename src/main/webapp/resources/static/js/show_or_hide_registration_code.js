/* =========================================================
 Funkcja chowa pola formularza dodania nowej praktyki,
 jeśli użytkownik zaznaczy pole wyboru zwolnienia z praktyk. 
 Po odznaczeniu tego pola, te same pola znów są pokazane.
 * ========================================================= */

$(document).ready(function () {

    var checkbox = $("#showCode");
    var hidden = $(".codeshown"); 
    if (!checkbox.is(':checked')) {
            hidden.hide();
        } else {
            hidden.show();
        }
        ;
    checkbox.change(function () {
        if (!checkbox.is(':checked')) {
            hidden.hide();
        } else {
            hidden.show();
        }
        ;
    });

}
);
  