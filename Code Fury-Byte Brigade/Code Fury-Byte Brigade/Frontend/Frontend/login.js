document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const userType = document.getElementById('userType').value;

    // This is a dummy login function. In a real application, you would send this data to a server for authentication.
    login(email, password, userType);
});

function login(email, password, userType) {
    // Dummy login logic
    if (email && password) {
        switch(userType) {
            case 'admin':
                alert('Admin login successful. Redirecting to admin dashboard...');
                // Redirect to admin page
                break;
            case 'manager':
                alert('Manager login successful. Redirecting to manager dashboard...');
                window.location.href = 'manager.html';
                break;
            case 'member':
                alert('Member login successful. Redirecting to member dashboard...');
                // Redirect to member page
                break;
            default:
                alert('Invalid user type');
        }
    } else {
        alert('Please enter both email and password');
    }
}