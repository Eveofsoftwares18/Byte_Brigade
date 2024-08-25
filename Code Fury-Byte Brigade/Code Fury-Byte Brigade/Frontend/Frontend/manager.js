// In manager.js, add this function to save bookings
function saveBooking(roomId, date, startTime, endTime, title) {
    let bookings = JSON.parse(localStorage.getItem('bookings')) || [];
    bookings.push({ roomId, date, startTime, endTime, title });
    localStorage.setItem('bookings', JSON.stringify(bookings));
}

// Modify the handleBookingSubmit function to save the booking
function handleBookingSubmit(e) {
    e.preventDefault();
    const roomId = document.getElementById('roomSelect').value;
    const date = document.getElementById('bookingDate').value;
    const startTime = document.getElementById('startTime').value;
    const endTime = document.getElementById('endTime').value;
    const title = document.getElementById('bookingTitle').value; // Add this input to your HTML
    const selectedRoom = rooms.find(room => room.id == roomId);

    if (selectedRoom) {
        const isAvailable = true; // Simulate room availability check
        if (isAvailable) {
            saveBooking(roomId, date, startTime, endTime, title);
            alert(`Room booked successfully!\nRoom: ${selectedRoom.name}\nDate: ${date}\nStart Time: ${startTime}\nEnd Time: ${endTime}\nTotal Price: $${selectedRoom.pricing}`);
        } else {
            alert('Booking is not available for the selected room.');
        }
    } else {
        alert('Invalid room selection.');
    }
}
// In manager.js

// ... (keep existing code)

function handleBookingSubmit(e) {
    e.preventDefault();
    const roomId = document.getElementById('roomSelect').value;
    const date = document.getElementById('bookingDate').value;
    const startTime = document.getElementById('startTime').value;
    const endTime = document.getElementById('endTime').value;
    const title = document.getElementById('bookingTitle').value;
    const selectedRoom = rooms.find(room => room.id == roomId);

    if (selectedRoom) {
        const isAvailable = true; // Simulate room availability check
        if (isAvailable) {
            saveBooking(roomId, date, startTime, endTime, title);
            console.log('Booking submitted:', { roomId, date, startTime, endTime, title });
            alert(`Room booked successfully!\nRoom: ${selectedRoom.name}\nDate: ${date}\nStart Time: ${startTime}\nEnd Time: ${endTime}\nTotal Price: $${selectedRoom.pricing}`);
        } else {
            alert('Booking is not available for the selected room.');
        }
    } else {
        alert('Invalid room selection.');
    }
}

function saveBooking(roomId, date, startTime, endTime, title) {
    let bookings = JSON.parse(localStorage.getItem('bookings')) || [];
    bookings.push({ roomId: parseInt(roomId), date, startTime, endTime, title });
    localStorage.setItem('bookings', JSON.stringify(bookings));
    console.log('Booking saved:', { roomId: parseInt(roomId), date, startTime, endTime, title });
}

// ... (keep existing code)