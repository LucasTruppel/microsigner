import React, { useState } from 'react';

const VerifySignature = ({ onVerify, onBack }) => {
  const [documentText, setDocumentText] = useState('');
  const [signature, setSignature] = useState('');

  const handleVerify = () => {
    onVerify(documentText, signature);
    setDocumentText('');
    setSignature('');
  };

  return (
    <div>
      <h2>Verify Signature</h2>
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
            placeholder="Enter the signer's name..."
            value={signature}
            onChange={(e) => setSignature(e.target.value)}
        />
      </div>
      <div className="signature">
        <input
            type="text"
            placeholder="Enter a public key..."
            value={signature}
            onChange={(e) => setSignature(e.target.value)}
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
