
$(document).ready(function() {

    $("#register").submit(function(e) {
        data = {
            name: $("#nameField").val(),
            doc: $("#docField").val(),
            tel: $("#telField").val(),
            dir: $("#dirField").val()
        };
        $.ajax({
            url: "/nspapp/registerAction",
            type: 'GET',
            data: data,
            success: function(res) {
                alert(res);
                $("#register").trigger("reset");
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus);
                console.log(errorThrown);
                alert("Error!");
                $("#register").trigger("reset");
            }
        });
        e.preventDefault();
    });

});