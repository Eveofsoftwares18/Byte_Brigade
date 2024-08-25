// Example in Node.js with Express
app.get('/api/user/profile', (req, res) => {
    // Assuming you have middleware to authenticate the user
    const user = req.user; // The logged-in user object

    if (user) {
        res.json({
            username: user.username,
            email: user.email,
            department: user.department
        });
    } else {
        res.status(401).json({ error: 'Unauthorized' });
    }
});
