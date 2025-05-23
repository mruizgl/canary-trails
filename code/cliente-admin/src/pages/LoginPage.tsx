import React from "react";
import LoginForm from "../components/LoginForm";
import "../styles/LoginPage.css"; 

const LoginPage: React.FC = () => {
  return (
    <div className="login-page">
      <div className="login-container">
        <h1 className="login-title">Iniciar sesión</h1>
        <LoginForm />
      </div>
    </div>
  );
};

export default LoginPage;
