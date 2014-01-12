$(document).ready(function () {
    loadTableList('#selectTable1', '#table1Data');
    loadTableList('#selectTable2', '#table2Data');

    prepareUniqueButton();
    prepareDifferenceButton();
});

function loadTableList(selectOneSelector, tableSelector) {
    $.get("http://localhost:8080/kuzoff-ws/api/table", null,
        function (responseData) {
            $('#tablesTmpl').tmpl(responseData).appendTo(selectOneSelector);
            loadDataForTable($(selectOneSelector).val(), tableSelector);
        }
    );

    $(selectOneSelector).on('change', function () {
        loadDataForTable($(this).val(), tableSelector);
    });
}

function loadDataForTable(tableName, tableSelector) {
    $.get("http://localhost:8080/kuzoff-ws/api/table/" + tableName + "/data", null,
        function (responseData) {
            $('#tableData').empty();
            $('#rowTmpl').tmpl(responseData).appendTo(tableSelector);
        }
    );
}

function prepareUniqueButton() {
    $("#buttonUnique").click(function (e) {
        $.post("http://localhost:8080/kuzoff-ws/api/table/" + $('#selectTable1').val() + "/unique", null,
            function (responseData) {
                $('#table1Data').empty();
                $('#rowTmpl').tmpl(responseData).appendTo('#table1Data');
            }
        );
        e.preventDefault();
    });
}

function prepareDifferenceButton() {
    $("#buttonDifference").click(function (e) {
        $.post("http://localhost:8080/kuzoff-ws/api/table/" + $('#selectTable1').val() + "/diff/" + $('#selectTable2').val(), null,
            function (responseData) {
                $('#table1Data').empty();
                $('#rowTmpl').tmpl(responseData).appendTo('#table1Data');
            }
        );
        e.preventDefault();
    });
}
