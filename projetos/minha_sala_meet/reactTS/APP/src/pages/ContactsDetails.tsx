import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';

const ContactsDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const handleContact = () => {
    console.log('Contato enviado');
    return navigate('/');
  };
  return (
    <div>
      <h1>ContactsDetails</h1>
      <p>Usuario: {id}</p>
      <button onClick={handleContact}>Clicar</button>
    </div>
  );
};

export default ContactsDetails;
