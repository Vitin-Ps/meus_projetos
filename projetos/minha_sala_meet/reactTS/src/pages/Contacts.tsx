import React from 'react';
import { Link } from 'react-router-dom';

const Contacts = () => {
  return (
    <div>
      <h1>Contacts</h1>
      <ul>
        <li>
          <Link to="/contacts/1">Link1</Link>
        </li>
        <li>
          <Link to="/contacts/2">Link2</Link>
        </li>
        <li>
          <Link to="/contacts/3">Link3</Link>
        </li>
      </ul>
    </div>
  );
};

export default Contacts;
