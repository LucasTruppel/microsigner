import React, { useState } from 'react';
import './App.css';
import Register from './components/Register';
import Login from './components/Login';

function App() {
  const [registeredUsers, setRegisteredUsers] = useState([]);
  const [loggedInUser, setLoggedInUser] = useState(null);

  const handleRegister = (user) => {
    setRegisteredUsers([...registeredUsers, user]);
  };

  const handleLogin = (user) => {
    const foundUser = registeredUsers.find(
      (u) => u.username === user.username && u.password === user.password
    );

    if (foundUser) {
      setLoggedInUser(foundUser);
      alert('Login successful!');
    } else {
      alert('Invalid username or password!');
    }
  };

  return (
    <div className="App">
      <h1>Distributed Computing App in React</h1>
      {!loggedInUser ? (
        <>
          <Register onRegister={handleRegister} />
          <Login onLogin={handleLogin} />
        </>
      ) : (
        <div>
          <h2>Welcome, {loggedInUser.username}!</h2>
          <button onClick={() => setLoggedInUser(null)}>Logout</button>
        </div>
      )}
    </div>
  );
}

export default App;
