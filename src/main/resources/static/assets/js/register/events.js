let userId = document.getElementById("userId");
let firstName = document.getElementById("firstName");
let lastName = document.getElementById("lastName");
let email = document.getElementById("email");
let password = document.getElementById("password");
let passwordConfirm = document.getElementById("passwordConfirm");
let roleSelector = document.getElementById("roleSelector");
let btnRegisterAndUpdate = document.getElementById("btnRegisterAndUpdate");
let btnClear = document.getElementById("btnClear");
let registerForm = document.getElementById("registerForm");

let isEditMode = false;

btnClear.addEventListener("click", function () {
    btnRegisterAndUpdate.textContent = "Register";
    isEditMode = false;
});

btnRegisterAndUpdate.addEventListener("click", function () {
    if(isEditMode){
        event.preventDefault();
        var url = btnRegisterAndUpdate.getAttribute('data-url') + "/" + userId.value;
        var user = {
            firstName: firstName.value,
            lastName: lastName.value,
            email: email.value,
            password: password.value,
            passwordConfirm: passwordConfirm.value,
            userRole: roleSelector.value
        }
        updateRequest(url, user, function (response, error) {
            if (error) {
                console.error(error);
            } else {
                console.log(response);
                window.location.href = response.url;
            }
        });
    }
    btnRegisterAndUpdate.textContent = "Register";
});

var editUser = function (self) {
    isEditMode = true;
    userId.value = self.getAttribute('data-id');
    firstName.value = self.getAttribute('data-firstName');
    lastName.value = self.getAttribute('data-lastName');
    email.value = self.getAttribute('data-email');
    roleSelector.value = self.getAttribute('data-role');
    btnRegisterAndUpdate.textContent = "Update";
}

