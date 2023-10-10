import React, { useState } from 'react';
import './App.css';
import Register from './components/Register';
import Login from './components/Login';
import SignDocument from './components/SignDocument';
import VerifySignature from './components/VerifySignature';

function App() {
  const [registeredUsers, setRegisteredUsers] = useState([]);
  const [loggedInUser, setLoggedInUser] = useState(null);
  const [signedDocuments, setSignedDocuments] = useState([]);
  const [verifiedDocuments, setVerifiedDocuments] = useState([]);

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
      alert('Invalid username or password.');
    }
  };

  const handleSignDocument = (documentText) => {
    setSignedDocuments([...signedDocuments, { username: loggedInUser.username, documentText }]);
    alert('Document signed successfully!');
  };

  const handleVerifySignature = (documentText, signature) => {
    const isVerified = signedDocuments.some(
      (doc) => doc.username === loggedInUser.username && doc.documentText === documentText
    );

    if (isVerified) {
      setVerifiedDocuments([...verifiedDocuments, { documentText, signature }]);
      alert('Signature verified successfully!');
    } else {
      alert('Signature verification failed.');
    }
  };

  return (
    <div className="App">
      <h1>MicroSigner</h1>
      {!loggedInUser ? (
        <>
          <div>
            <h2>Sign Up or Login</h2>
          </div>
          <Register onRegister={handleRegister} />
          <Login onLogin={handleLogin} />
        </>
      ) : (
        <>
          <div>
            <h2>Welcome, {loggedInUser.username}!</h2>
          </div>
          <SignDocument onSign={handleSignDocument} onBack={() => setLoggedInUser(null)} />
          <VerifySignature onVerify={handleVerifySignature} onBack={() => setLoggedInUser(null)} />
        </>
      )}
    </div>
  );
}

export default App;
