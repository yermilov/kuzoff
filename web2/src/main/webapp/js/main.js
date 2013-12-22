$(document).ready(function() {
    var data = [
        { value: "value1", label: "label1"},
        { value: "value2", label: "label2"}
    ];
    
    $('#tablesTmpl').tmpl(data).appendTo('#selectTable');
});