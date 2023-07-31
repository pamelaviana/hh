var csrfToken = document.querySelector("[name='_csrf']").value;

var deleteRequest = function (btn, deleteElement=null) {
    var url = btn.getAttribute('data-url');
    $.ajax({
        type: "DELETE",
        url: url,
        beforeSend: function(xhr) {
            // Set the CSRF token in the request headers
            xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken);
        },
        success: function(response) {
            // get the closest tr parent element and remove it
            if (deleteElement != null && deleteElement != undefined) {
                btn.closest(deleteElement).remove();
            }
            console.log(response.message);
            if(response.url != null && response.url != undefined)
                window.location.href = response.url;
        },
        error: function(xhr, status, error) {
            // Handle the error response, if needed
            console.error("Error deleting mode:", error);
        }
    });
};