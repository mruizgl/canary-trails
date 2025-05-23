import React, { JSX, useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import { jwtDecode } from "jwt-decode";

interface Props {
  children: JSX.Element;
}

const PrivateRoute: React.FC<Props> = ({ children }) => {

  const { token } = useContext(AuthContext);

  if (!token) {

    const localToken = localStorage.getItem("token");

    if (!localToken) return <Navigate to="/" />;

      try {

        const decoded = jwtDecode<{ role: string }>(localToken);
        if (!decoded.role?.includes("ADMIN")) return <Navigate to="/" />;

      } catch {
        return <Navigate to="/" />;
      }

    } else {

      try {
        const decoded = jwtDecode<{ role: string }>(token);
        if (!decoded.role?.includes("ADMIN")) return <Navigate to="/" />;
      } catch {
        return <Navigate to="/" />;
      }
      
    }

  return children;
};

export default PrivateRoute;
