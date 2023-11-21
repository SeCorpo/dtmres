import {StorageManager} from "../classes/storageManager.js";

// const adminLoginDiv = document.getElementById("wrapper");
// const adminPasswordField = document.getElementById("admin-password");
// const adminPasswordButton = document.getElementById("admin-password-button");
// //const passwordVisibleToggle = document.getElementById("show-password-toggle-admin-login");

// adminPasswordButton.addEventListener("click", async e => {
//     e.preventDefault();
//     if (await loginCheck(adminPasswordField.value)) {
//         await loadReservationTable();
//     } else {
//         adminPasswordField.value = '';
//
//           document.getElementById("error-message").style.display = 'block';
//     }
// });



document.addEventListener('DOMContentLoaded', async (event) => {
    // const togglePassword = document.getElementById('toggle-password');
    // const adminPasswordField = document.getElementById('admin-password'); // Make sure this ID matches your HTML
    // const togglePasswordImage = togglePassword.querySelector('img');
    //
    // togglePassword.addEventListener('click', () => {
    //     // Check if the password is currently visible
    //     const isPasswordVisible = adminPasswordField.getAttribute('type') === 'text';
    //     adminPasswordField.setAttribute('type', isPasswordVisible ? 'password' : 'text');
    //
    //     // Toggle the eye/eye-off icon
    //     togglePasswordImage.src = isPasswordVisible ?
    //         '../icons/eye-outline.svg' :
    //         '../icons/eye-off-outline.svg';
    // });

    await loadReservationTable();
});




//Switches from the login display to the reservation display.
//This way you always need to log in when going to /admin, instead of from a separate page
//The reservation display only gets loaded when the password is entered
async function loadReservationTable() {
    //switch view
    //adminLoginDiv.style.display = "none";
    const reservationDisplay = document.getElementById("reservation-display-div");
    reservationDisplay.style.display = "block";

    getTableHeader();
    await setReservationTable();
}

async function setReservationTable() {
    const products = await StorageManager.getAllProducts();
    let table = document.getElementById("reservation-table");
    table.appendChild(getTableHeader());

    const reservations = await StorageManager.getReservations();
    reservations.forEach(reservation => {
        const tableRow = reservation.getTableRow(products);
        table.appendChild(tableRow)
    });
}

function getTableHeader() {
    let tableHeader = document.createElement("tr");

    tableHeader.innerHTML = `
        <th>Email</th>
        <th>Hoeveelheid producten</th>
        <th>Datum(s)</th>
        <th>Acties</th>
        `;
    return tableHeader;
}