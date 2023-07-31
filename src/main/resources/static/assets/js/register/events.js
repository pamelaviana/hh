let firstName = document.getElementById("firstName");
let lastName = document.getElementById("lastName");
let email = document.getElementById("email");
let password = document.getElementById("password");
let roleSelector = document.getElementById("roleSelector");
let btnRegisterAndUpdate = document.getElementById("btnRegisterAndUpdate");
let registerForm = document.getElementById("registerForm");

var editUser = function (self) {
    let id = self.getAttribute('data-id');
    firstName.value = btn.getAttribute('data-firstName');
    lastName.value = btn.getAttribute('data-lastName');
    email.value = btn.getAttribute('data-email');
    password.value = btn.getAttribute('data-password');
    roleSelector.value = btn.getAttribute('data-role');
    btnRegisterAndUpdate.value = "Update";
    registerForm.action = "/user/update/" + id;
}