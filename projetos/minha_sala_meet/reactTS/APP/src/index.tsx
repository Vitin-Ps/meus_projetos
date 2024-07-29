import React from 'react';
import ReactDOM from 'react-dom/client';
import './css/index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

// configurando o router
import { createBrowserRouter, Navigate, RouterProvider } from 'react-router-dom';
import Login from './pages/Login';
import Contacts from './pages/Contacts';
import ErrorPage from './pages/ErrorPage';
import ContactsDetails from './pages/ContactsDetails';
import { AuthProvider } from './contexts/Auth/AuthProvider';
import { RequireAuth } from './contexts/Auth/RequireAuth';

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

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: '/',
        element: <Login />,
      },
      {
        path: '/contacts',
        element: (
          <RequireAuth>
            <Contacts />
          </RequireAuth>
        ),
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
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
