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

function loadWeather(){
    $.ajax({
        url: "http://api.openweathermap.org/data/2.5/weather?zip=" + 
                document.getElementById("recruitInfo:zipcode").value + 
                ",us&appid=58df88917a0cb920eac73723a75f30e6",
        dataType: 'jsonp',
        success: function(jsonPData){
            var kelvin = jsonPData["main"]["temp"];
            var farenheit = kelvin * 9/5 - 459.67;
            var weather = "The current weather is " + jsonPData["weather"][0]["main"] + " and " +
                    farenheit.toFixed(1) + "&#8457";
            $("#weather").html(weather);
        }
    });
}
