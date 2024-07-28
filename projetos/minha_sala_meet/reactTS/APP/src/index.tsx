import React from 'react';
import ReactDOM from 'react-dom/client';
import './css/index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

// configurando o router
import { createBrowserRouter, Navigate, RouterProvider, useNavigate } from 'react-router-dom';
import Home from './pages/Home';
import Contacts from './pages/Contacts';
import ErrorPage from './pages/ErrorPage';
import ContactsDetails from './pages/ContactsDetails';

// const router = createBrowserRouter([
//   {
//     path: '/',
//     element: <Home />,
//   },
//   {
//     path: '/contacts',
//     element: <Contacts />,
//   },
// ]);

const Private = ({ Item }: any) => {
  const signed: boolean = false; // Substitua isso pela sua lógica de autenticação

  return signed ? <Item /> : <Home />;
};

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: '/',
        element: <Home />,
      },
      {
        path: '/contacts',
        element: <Private item={Contacts} />,
      },
      {
        path: '/contacts/:id',
        element: <ContactsDetails />,
      },
      {
        path: '/oldcontact',
        element: <Navigate to="/contacts" />,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
