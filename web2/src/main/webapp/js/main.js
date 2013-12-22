$(document).ready(function() {
    $.get("http://localhost:8080/kuzoff-ws/api/table", null,
        function(responseData) {
            $('#tablesTmpl').tmpl(responseData).appendTo('#selectTable');
            loadDataForTable($('#selectTable').val());
        }
    );
    
    $('#selectTable').on('change', function() {
        loadDataForTable($(this).val());
    });
});

function loadDataForTable(tableName) {
    $('#tableData').empty();
    $.get("http://localhost:8080/kuzoff-ws/api/table/" + tableName + "/data", null,
        function(responseData) {
            $('#rowTmpl').tmpl(responseData).appendTo('#tableData');
        }
    );
}
