import React, { useState } from 'react';

const Register = ({ onRegister }) => {
  const [name, setName] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = () => {
    onRegister({ name, username, password });
    setName('');
    setUsername('');
    setPassword('');
  };

  return (
    <div className="frontcomponent">
      <h3>Sign Up</h3>
      <div className="name">
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
      </div>
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
        <button onClick={handleRegister}>Sign Up</button>
      </div>
    </div>
  );
};

export default Register;
