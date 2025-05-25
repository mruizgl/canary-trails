import React, { createContext, useState, ReactNode, useEffect } from "react";

interface AuthContextType {
  token: string;
  login: (token: string) => void;
  logout: () => void;
}
const AuthContext = createContext<AuthContextType>({} as AuthContextType);

export const AuthProvider = (props: any) => {

  const [token, setToken] = useState<string>("");

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    if (storedToken) {
      setToken(storedToken);
    }
  }, [])
  

  const login = (newToken: string) => {
    localStorage.setItem("token", newToken);
    setToken(newToken);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken("");
  };

  const contextValues = {
    token,
    login,
    logout,
}

  return (
    <AuthContext.Provider value={contextValues}>
      {props.children}
    </AuthContext.Provider>
  );
};

export function useAppContext() {
  return React.useContext(AuthContext);
}
