document.addEventListener('DOMContentLoaded', function() {
    // Registration form submission
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (password !== confirmPassword) {
                alert('Passwords do not match!');
                return;
            }

            // Check if email is already registered
            let users = JSON.parse(localStorage.getItem('users')) || [];
            if (users.some(user => user.email === email)) {
                alert('This email is already registered!');
                return;
            }

            // Add new user
            users.push({ username, email, password });
            localStorage.setItem('users', JSON.stringify(users));

            alert('Registration successful! Please login.');
            window.location.href = 'login.html';
        });
    }

    // Login form submission
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            // Check if user exists and password is correct
            let users = JSON.parse(localStorage.getItem('users')) || [];
            const user = users.find(u => u.email === email && u.password === password);

            if (user) {
                localStorage.setItem('loggedInUser', JSON.stringify(user));
                window.location.href = 'manager.html';
            } else {
                alert('Invalid email or password!');
            }
        });
    }

    // Check if user is logged in
    function checkLoggedIn() {
        return localStorage.getItem('loggedInUser') !== null;
    }

    // Redirect to dashboard if already logged in
    if (checkLoggedIn() && (window.location.href.includes('login.html') || window.location.href.includes('register.html'))) {
        window.location.href = 'manager.html';
    }

    // Redirect to login if trying to access manager page while not logged in
    if (!checkLoggedIn() && window.location.href.includes('manager.html')) {
        window.location.href = 'login.html';
    }

    // Logout functionality
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            localStorage.removeItem('loggedInUser');
            window.location.href = 'login.html';
        });
    }

    // Display user email in manager dashboard
    const userEmailSpan = document.getElementById('userEmail');
    if (userEmailSpan) {
        const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
        if (loggedInUser) {
            userEmailSpan.textContent = loggedInUser.email;
        }
    }

    // ... (rest of your existing code)
});

document.addEventListener('DOMContentLoaded', function() {
    // Password validation function
    function isPasswordValid(password) {
        const minLength = 8;
        const hasUpperCase = /[A-Z]/.test(password);
        const hasLowerCase = /[a-z]/.test(password);
        const hasNumbers = /\d/.test(password);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

        return password.length >= minLength && hasUpperCase && hasLowerCase && hasNumbers && hasSpecialChar;
    }

    // Registration form submission
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (!isPasswordValid(password)) {
                alert('Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.');
                return;
            }

            if (password !== confirmPassword) {
                alert('Passwords do not match!');
                return;
            }

            // Check if email is already registered
            let users = JSON.parse(localStorage.getItem('users')) || [];
            if (users.some(user => user.email === email)) {
                alert('This email is already registered!');
                return;
            }

            // Add new user
            users.push({ username, email, password });
            localStorage.setItem('users', JSON.stringify(users));

            alert('Registration successful! Please login.');
            window.location.href = 'login.html';
        });
    }

    // ... (rest of your existing code)
});