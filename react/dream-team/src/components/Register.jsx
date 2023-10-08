import React, { useState } from 'react';

const Register = ({ onRegister }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = () => {
    onRegister({ username, password });
    setUsername('');
    setPassword('');
  };

  return (
    <div>
      <h2>Register</h2>
      <div className="username">
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div className="password">
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <div className="button">
        <button onClick={handleRegister}>Register</button>
      </div>
    </div>
  );
};

export default Register;
