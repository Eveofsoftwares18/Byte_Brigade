document.addEventListener('DOMContentLoaded', function() {
    loadRoomDetails();
    setupImportUsersButton();
});

function loadRoomDetails() {
    // In a real application, you would fetch this data from a server
    const rooms = [
        { name: 'Room A', capacity: '5-10', amenities: ['Whiteboard', 'Projector', 'WiFi'] },
        { name: 'Room B', capacity: '11-20', amenities: ['Projector', 'Conference Call', 'TV'] },
        { name: 'Room C', capacity: '20+', amenities: ['Whiteboard', 'Projector', 'Coffee Machine'] },
    ];

    const roomsSection = document.getElementById('rooms');
    
    rooms.forEach(room => {
        const roomDiv = document.createElement('div');
        roomDiv.className = 'room';
        roomDiv.innerHTML = `
            <h3>${room.name}</h3>
            <p>Capacity: ${room.capacity}</p>
            <p>Amenities: ${room.amenities.join(', ')}</p>
        `;
        roomsSection.appendChild(roomDiv);
    });
}

function setupImportUsersButton() {
    const importButton = document.createElement('button');
    importButton.textContent = 'Import Users';
    importButton.id = 'importUsersButton';
    importButton.addEventListener('click', importUsers);

    const mainElement = document.querySelector('main');
    mainElement.insertBefore(importButton, mainElement.firstChild);
}

function importUsers() {
    // In a real application, this would send a request to the server
    // to load data from the XML file. Here, we'll simulate the process.
    console.log('Importing users...');
    
    // Simulating an AJAX request
    setTimeout(() => {
        const users = [
            { name: 'John Doe', email: 'john@example.com', phone: '1234567890', role: 'Manager' },
            { name: 'Jane Smith', email: 'jane@example.com', phone: '0987654321', role: 'Admin' },
            { name: 'Bob Johnson', email: 'bob@example.com', phone: '1122334455', role: 'Member' },
        ];

        displayImportedUsers(users);
    }, 1000);
}

function displayImportedUsers(users) {
    const usersSection = document.createElement('section');
    usersSection.id = 'importedUsers';
    usersSection.innerHTML = '<h2>Imported Users</h2>';

    const userList = document.createElement('ul');
    users.forEach(user => {
        const userItem = document.createElement('li');
        userItem.textContent = `${user.name} (${user.role}) - ${user.email} - ${user.phone}`;
        userList.appendChild(userItem);
    });

    usersSection.appendChild(userList);
    document.querySelector('main').appendChild(usersSection);

    alert('Users imported successfully!');
}