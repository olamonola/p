/* =========================================================
Funkcja ustawiania kalendarzy
 * ========================================================= */


$(document).ready(function () {

    $('#startDate').datepicker({
        format: "dd-mm-yyyy"
    });
    $('#endDate').datepicker({
        format: "dd-mm-yyyy"
    });

}
);