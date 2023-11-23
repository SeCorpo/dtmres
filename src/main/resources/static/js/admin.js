import {StorageManager} from "../classes/storageManager.js";

document.addEventListener('DOMContentLoaded', async (event) => {

    await loadReservationTable();
});




async function loadReservationTable() {
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