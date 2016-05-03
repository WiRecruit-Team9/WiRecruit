function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function geocode() {
    var address = document.getElementById("recruitInfo:address").value + ", " + 
            document.getElementById("recruitInfo:city").value + ", " + 
            document.getElementById("recruitInfo:state").value + " " + 
            document.getElementById("recruitInfo:zipcode").value;
    
    PF('geoMap').geocode(address);
}
