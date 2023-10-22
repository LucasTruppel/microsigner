import React, { useState } from 'react';

const VerifySignature = ({ onVerify, onBack }) => {
  const [documentText, setDocumentText] = useState('');
  const [username, setUsername] = useState('');
  const [signature, setSignature] = useState('');

  const handleVerify = () => {
    onVerify(documentText, username, signature);
    setDocumentText('');
    setUsername('');
    setSignature('');
  };

  return (
    <div className="backcomponent">
      <h3>Verify Signature</h3>
      <div className="document">
        <textarea
            rows="5"
            cols="50"
            placeholder="Enter document text..."
            value={documentText}
            onChange={(e) => setDocumentText(e.target.value)}
        />
      </div>
      <div className="signature">
        <textarea
          rows="5"
          cols="50"
          type="text"
          placeholder="Enter Signature..."
          value={signature}
          onChange={(e) => setSignature(e.target.value)}
        />
      </div>
      <div className="username">
        <input
          type="text"
          placeholder="Enter Subject Username..."
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div className="button">
        <button onClick={handleVerify}>Verify</button>
        <button onClick={onBack}>Back</button>
      </div>
    </div>
  );
};

export default VerifySignature;
