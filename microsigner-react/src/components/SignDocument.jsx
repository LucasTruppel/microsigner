import React, { useState } from 'react';

const SignDocument = ({ onSign, onBack }) => {
  const [documentText, setDocumentText] = useState('');

  const handleSign = () => {
    onSign(documentText);
    setDocumentText('');
  };

  return (
    <div className="backcomponent">
      <h3>Sign Document</h3>
      <div className="document">
        <textarea
            rows="15"
            cols="50"
            placeholder="Enter document text..."
            value={documentText}
            onChange={(e) => setDocumentText(e.target.value)}
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
