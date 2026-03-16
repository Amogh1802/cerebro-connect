import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import Profile from './components/Profile';
import LandingPage from './components/LandingPage';
import './App.css';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem('token')
  );
  const role = localStorage.getItem('role');

  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/login/:role" element={<Login setIsAuthenticated={setIsAuthenticated} />} />
          <Route path="/login" element={<LandingPage />} />
          <Route
            path="/dashboard"
            element={isAuthenticated ? <Dashboard role={role} /> : <Navigate to="/" />}
          />
          <Route
            path="/profile"
            element={isAuthenticated ? <Profile role={role} /> : <Navigate to="/" />}
          />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;