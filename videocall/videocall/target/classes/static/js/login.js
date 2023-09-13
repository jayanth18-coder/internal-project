function handleLogin(event) {
    event.preventDefault();

    // Get user input
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Validate user inputs (client-side validation)
    if (!email || !password) {
        alert('Please provide both email and password.');
        return;
    }

    const user = {
        email: email,
        password: password
    };

    fetch('http://localhost:8080/api/v1/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
    .then(response => {
        if (!response.ok) {
            // Handle specific error messages from the server if available
            return response.json().then(data => {
                throw new Error(data.message || 'Login failed.');
            });
        }
        return response.json();
    })
    .then((response) => {
        localStorage.setItem('connectedUser', JSON.stringify(response));
        window.location.href = 'index.html';
    })
    .catch(error => {
        console.error('POST request error', error);
        alert('An error occurred while logging in. Please try again later.');
    });
}

const loginForm = document.getElementById("loginForm");

// Check if the form element exists before attaching the event listener
if (loginForm) {
    loginForm.addEventListener("submit", handleLogin);
} else {
    console.error('Form element with ID "loginForm" not found.');
}
