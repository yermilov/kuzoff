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
    
    $("#buttonUnique").click(function(e){
        $.post("http://localhost:8080/kuzoff-ws/api/table/" + $('#selectTable').val() + "/unique", null,
            function(responseData) {
                $('#tableData').empty();
                $('#rowTmpl').tmpl(responseData).appendTo('#tableData');
            }
        );
        e.preventDefault();
    }); 
});

function loadDataForTable(tableName) {
    $.get("http://localhost:8080/kuzoff-ws/api/table/" + tableName + "/data", null,
        function(responseData) {
            $('#tableData').empty();
            $('#rowTmpl').tmpl(responseData).appendTo('#tableData');
        }
    );
}
