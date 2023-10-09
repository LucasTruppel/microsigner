import React, { useState } from 'react';

const VerifySignature = ({ onVerify, onBack }) => {
  const [documentText, setDocumentText] = useState('');
  const [publicKey, setPublicKey] = useState('');
  const [signer, setSigner] = useState('');
  const [signature, setSignature] = useState('');

  const handleVerify = () => {
    onVerify(documentText, publicKey, signer, signature);
    setDocumentText('');
    setPublicKey('');
    setSigner('');
    setSignature('');
  };

  return (
    <div className="component">
      <h3>Verify Signature</h3>
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
          placeholder="Signer Name..."
          value={signer}
          onChange={(e) => setSigner(e.target.value)}
        />
      </div>
      <div className="publicKey">
        <input
          type="text"
          placeholder="Enter Public Key..."
          value={publicKey}
          onChange={(e) => setPublicKey(e.target.value)}
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
