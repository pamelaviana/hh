let userId = document.getElementById("userId");
let medicationId = document.getElementById("medicationId");
let name = document.getElementById("name");
let description = document.getElementById("description");
let dosage = document.getElementById("dosage");
let frequency = document.getElementById("frequency");
let duration = document.getElementById("duration");

let btnRegisterAndUpdate = document.getElementById("btnRegisterAndUpdate");
let btnClear = document.getElementById("btnClear");

let isEditMode = false;

btnClear.addEventListener("click", function () {
    btnRegisterAndUpdate.textContent = "Add";
    isEditMode = false;
});

btnRegisterAndUpdate.addEventListener("click", function () {
    if(isEditMode){
        event.preventDefault();
        var url = btnRegisterAndUpdate.getAttribute('data-url') + "/" + userId.value;
        var medication = {
            id: medicationId.value,
            name: name.value,
            description: description.value,
            dosage: dosage.value,
            frequency: frequency.value,
            duration: duration.value
        }
        updateRequest(url, medication, function (response, error) {
            if (error) {
                console.error(error);
            } else {
                console.log(response);
                window.location.href = response.url;
            }
        });
    }
    btnRegisterAndUpdate.textContent = "Add";
});


var editMedication = function (self) {
    isEditMode = true;
    medicationId.value = self.getAttribute('data-id');
    name.value = self.getAttribute('data-name');
    description.value = self.getAttribute('data-description');
    dosage.value = self.getAttribute('data-dosage');
    frequency.value = self.getAttribute('data-frequency');
    duration.value = self.getAttribute('data-duration');
    btnRegisterAndUpdate.textContent = "Update";
}