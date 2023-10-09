import React, { useState } from 'react';

const SignDocument = ({ onSign, onBack }) => {
  const [documentText, setDocumentText] = useState('');
  const [privateKey, setPrivateKey] = useState('');
  const [signer, setSigner] = useState('');

  const handleSign = () => {
    onSign(documentText, privateKey, signer);
    setDocumentText('');
    setPrivateKey('');
    setSigner('');
  };

  return (
    <div className="component">
      <h3>Sign Document</h3>
      <div className="document">
        <textarea
            rows="10"
            cols="50"
            placeholder="Enter document text..."
            value={documentText}
            onChange={(e) => setDocumentText(e.target.value)}
        />
      </div>
      <div className="signer">
        <input
          type="text"
          placeholder="Your Name..."
          value={signer}
          onChange={(e) => setSigner(e.target.value)}
        />
      </div>
      <div className="privateKey">
        <input
          type="text"
          placeholder="Enter Private Key..."
          value={privateKey}
          onChange={(e) => setPrivateKey(e.target.value)}
        />
      </div>
      <div className="button">
        <button onClick={handleSign}>Sign</button>
        <button onClick={onBack}>Back</button>
      </div>
    </div>
  );
};

export default SignDocument;
