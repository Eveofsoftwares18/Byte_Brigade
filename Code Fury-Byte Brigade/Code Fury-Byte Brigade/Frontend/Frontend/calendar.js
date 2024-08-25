document.addEventListener('DOMContentLoaded', function () {
    const calendarDays = document.getElementById('calendarDays');
    const monthYear = document.getElementById('monthYear');
    const bookingDetails = document.getElementById('bookingDetails');
    const prevMonthBtn = document.getElementById('prevMonth');
    const nextMonthBtn = document.getElementById('nextMonth');

    let currentMonth = new Date().getMonth();
    let currentYear = new Date().getFullYear();

    const rooms = [
        { id: 1, name: 'Training Room', colorClass: 'room-training' },
        { id: 2, name: 'Board Room', colorClass: 'room-board' },
        { id: 3, name: 'Conference Room', colorClass: 'room-conference' }
    ];

    // Example booking data
    const bookings = JSON.parse(localStorage.getItem('bookings')) || [
        { date: '2024-08-23', startTime: '10:00', endTime: '11:00', title: 'Team Meeting', roomId: 1 },
        { date: '2024-08-24', startTime: '14:00', endTime: '16:00', title: 'Client Presentation', roomId: 2 },
        { date: '2024-08-25', startTime: '09:00', endTime: '12:00', title: 'Board Meeting', roomId: 3 },
        { date: '2024-08-26', startTime: '13:00', endTime: '15:00', title: 'Training Session', roomId: 1 }
    ];

    function renderCalendar() {
        calendarDays.innerHTML = '';
        const firstDay = new Date(currentYear, currentMonth, 1);
        const lastDay = new Date(currentYear, currentMonth + 1, 0);
        const daysInMonth = lastDay.getDate();

        monthYear.textContent = `${firstDay.toLocaleString('default', { month: 'long' })} ${currentYear}`;

        // Fill the first row with empty days if the month does not start on Sunday
        const prevMonthLastDay = new Date(currentYear, currentMonth, 0).getDate();
        for (let i = firstDay.getDay() - 1; i >= 0; i--) {
            const day = prevMonthLastDay - i;
            const dayElem = document.createElement('div');
            dayElem.className = 'calendar-day empty';
            dayElem.textContent = day;
            calendarDays.appendChild(dayElem);
        }

        // Fill in the days of the current month
        for (let i = 1; i <= daysInMonth; i++) {
            const dayElem = document.createElement('div');
            dayElem.className = 'calendar-day';
            dayElem.textContent = i;
            dayElem.dataset.date = `${currentYear}-${String(currentMonth + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`;

            const bookingsOnDate = bookings.filter(booking => booking.date === dayElem.dataset.date);
            if (bookingsOnDate.length > 0) {
                bookingsOnDate.forEach(booking => {
                    const room = rooms.find(room => room.id === booking.roomId);
                    if (room) {
                        dayElem.classList.add(room.colorClass);
                    }
                });
            }

            dayElem.addEventListener('click', function () {
                showBookingDetails(dayElem.dataset.date);
            });
            calendarDays.appendChild(dayElem);
        }

        // Fill the remaining days with empty slots if the month does not end on Saturday
        const nextMonthDays = 7 - lastDay.getDay();
        for (let i = 1; i <= nextMonthDays; i++) {
            const dayElem = document.createElement('div');
            dayElem.className = 'calendar-day empty';
            dayElem.textContent = i;
            calendarDays.appendChild(dayElem);
        }
    }

    function showBookingDetails(date) {
        const bookingsOnDate = bookings.filter(booking => booking.date === date);
        if (bookingsOnDate.length === 0) {
            bookingDetails.innerHTML = `<h3>No bookings for ${date}</h3>`;
        } else {
            let detailsHtml = `<h3>Bookings for ${date}</h3>`;
            bookingsOnDate.forEach(booking => {
                const room = rooms.find(room => room.id === booking.roomId);
                detailsHtml += `<p><strong>Room:</strong> ${room ? room.name : 'Unknown'} <br> <strong>Event:</strong> ${booking.title} <br> <strong>Time:</strong> ${booking.startTime} - ${booking.endTime}</p>`;
            });
            bookingDetails.innerHTML = detailsHtml;
        }
    }

    prevMonthBtn.addEventListener('click', function () {
        if (currentMonth === 0) {
            currentMonth = 11;
            currentYear--;
        } else {
            currentMonth--;
        }
        renderCalendar();
    });

    nextMonthBtn.addEventListener('click', function () {
        if (currentMonth === 11) {
            currentMonth = 0;
            currentYear++;
        } else {
            currentMonth++;
        }
        renderCalendar();
    });

    renderCalendar();
});
