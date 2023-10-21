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

  var jwt = "";

  const handleRegister = (user) => {
    //setRegisteredUsers([...registeredUsers, user]);   
    const { username, password } = user;
    const xhr = new XMLHttpRequest();

    //Boolean argument is for asynchronicity. (true for asynchronous)
    xhr.open('POST', 'http://localhost:9080/signup', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 400) {
          const {username, name} = JSON.parse(xhr.responseText);
          alert("User \"" + username + "\", with name \"" + name + "\", has signed up sucessfully!");
      } else {
          alert('Error: ' + xhr.status + xhr.statusText + "\n"
          + JSON.parse(xhr.responseText).message);

      }
    };
    xhr.onerror = function () {
        alert('Network Error');
    };
  
    xhr.send(JSON.stringify({ username: username, name: 'bar', password: password}));


  };

  const handleLogin = (user) => {
    const { username, password } = user;

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:9080/login', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 400) {
          jwt = JSON.parse(xhr.responseText).jwt;
          setLoggedInUser(true);
          alert('Login successful!');
      } else {
        alert('Error: ' + xhr.status + xhr.statusText + "\n"
        + JSON.parse(xhr.responseText).message);
      }
    };
    xhr.onerror = function () {
        alert('Network Error');
    };
    xhr.send(JSON.stringify({ username: username, password: password}));

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
