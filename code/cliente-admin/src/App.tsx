import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import LoginPage from "./pages/LoginPage";
import PrivateRoute from "./components/PrivateRoute";
import DashboardPage from "./pages/DashboardPage";
import FloraAdminPage from "./pages/FloraAdminPage";
import FaunaAdminPage from "./pages/FaunaAdminPage";
import RutaAdminPage from "./pages/RutaAdminPage";
import CrearRutaPage from "./pages/CrearRutaPage";
import CrearFaunaPage from "./pages/CrearFaunaPage";
import EditarFaunaPage from "./pages/EditarFaunaPage";
import EditarRutaPage from "./pages/EditarRutaPage";
import CrearFloraPage from "./pages/CrearFloraPage";
import EditarFloraPage from "./pages/EditarFloraPage";
import EstadisticasPage from "./pages/EstadisticasPage";

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/dashboard"
            element={
              <PrivateRoute>
                <DashboardPage />
              </PrivateRoute>
            }
          />
          <Route path="/admin/floras" element={<PrivateRoute><FloraAdminPage /></PrivateRoute>} />
          <Route
            path="/admin/faunas"
            element={
              <PrivateRoute>
                <FaunaAdminPage />
              </PrivateRoute>
            }
          />
          <Route path="/admin/rutas" element={<PrivateRoute><RutaAdminPage /></PrivateRoute>} />
          <Route path="/admin/rutas/crear" element={<CrearRutaPage />} />
          <Route path="/admin/faunas/crear" element={<CrearFaunaPage />} />
          <Route path="/admin/faunas/editar/:id" element={<EditarFaunaPage />} />
          <Route path="/admin/rutas/editar/:id" element={<EditarRutaPage />} />
          <Route path="/admin/floras/crear" element={<CrearFloraPage />} />
          <Route path="/admin/floras/editar/:id" element={<EditarFloraPage />} />
          <Route path="/admin/estadisticas" element={<EstadisticasPage />} />



        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;