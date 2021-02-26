$(document).ready(function() {
    $.ajax({
        url: "/nspapp/getAction",
        type: 'GET',
        dataType: 'json',
        success: function(res) {
            console.log(res);
            $("#json").html(JSON.stringify(res, undefined, 2));
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
            console.log(errorThrown);
            alert(errorThrown);
        }
    });
});