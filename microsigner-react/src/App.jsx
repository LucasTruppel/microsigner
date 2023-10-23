import React, { useState } from 'react';
import './App.css';
import Register from './components/Register';
import Login from './components/Login';
import SignDocument from './components/SignDocument';
import VerifySignature from './components/VerifySignature';

const url = "http://localhost:9080/";

function App() {

  const [loggedInUser, setLoggedInUser] = useState(null);
  const [jwt, setjwt] = useState("");

  const handleRegister = (user) => {
    const { name, username, password } = user;
    const xhr = new XMLHttpRequest();
    xhr.open('POST', url + 'signup');
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

    xhr.send(JSON.stringify({ username: username, name: name, password: password}));
  };


  const handleLogin = (user) => {
    const { username, password } = user;

    const xhr = new XMLHttpRequest();
    xhr.open('POST',  url + 'login');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 400) {
          setjwt(JSON.parse(xhr.responseText).jwt);
          setLoggedInUser(username);
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
    //documentText is just a string.

    const xhr = new XMLHttpRequest();
    xhr.open('POST',  url + 'sign');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Authorization', "Bearer " + jwt);
    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 400) {
        const {signatureBase64, text} = JSON.parse(xhr.responseText);
        alert('Document signed successfully!\nSignature: ' + signatureBase64);

      } else {
        alert('Error: ' + xhr.status + xhr.statusText + "\n"
        + JSON.parse(xhr.responseText).message);
      }
    };
    xhr.onerror = function () {
        alert('Network Error');
    };

    xhr.send(JSON.stringify({ text: documentText}));
  };


  const handleVerifySignature = (documentText, username, signature) => {
    //documentText and signature are strings.
    //body with text, signatureBase64 and signerUsername. data received is a {signerUsername : true}

    const xhr = new XMLHttpRequest();
    xhr.open('POST',  url + 'verify');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Authorization', "Bearer " + jwt);
    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 400) {
        const {validSignature} = JSON.parse(xhr.responseText);
        if (validSignature) { 
          alert('Signature verified successfully!'); 
        } else {
          alert('Signature was NOT verified.');
        }
      } else {
        alert('Error: ' + xhr.status + xhr.statusText + "\n"
        + JSON.parse(xhr.responseText).message) + "\n"
        + 'Signature was NOT verified.';
      }
    };
    xhr.onerror = function () {
        alert('Network Error');
    };

    xhr.send(JSON.stringify({text: documentText, signatureBase64: signature, signerUsername: username}));
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
