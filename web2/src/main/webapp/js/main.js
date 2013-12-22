$(document).ready(function() {
    $.get("http://localhost:8080/kuzoff-ws/api/table", null,
        function(responseData) {
            $('#tablesTmpl').tmpl(responseData).appendTo('#selectTable');
        }
    );
});