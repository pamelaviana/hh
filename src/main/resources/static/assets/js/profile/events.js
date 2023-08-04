const doctorId = document.getElementById("doctorId");

doctorId.addEventListener("change", function (event) {

    let selectOption = event.target;
    let url = doctorId.getAttribute('data-url');
    let user = {
        id: selectOption.value
    };
    updateRequest(url, user, function (response, error) {
        if (error) {
            console.error(error);
        } else {
            console.log(response);
            if(response != null && response.url != null && response.url != undefined)
                window.location.href = response.url;
        }
    });
});