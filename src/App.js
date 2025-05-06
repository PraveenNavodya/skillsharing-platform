import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { auth } from './firebaseConfig';
import { onAuthStateChanged } from 'firebase/auth';

import MainPage from './components/MainPage';
import UserProfilePage from './components/UserProfilePage';
import CreateProfileForm from './components/CreateProfileForm';
import EditProfileForm from './components/EditProfileForm';
import NotificationsPage from './components/NotificationsPage';
import LoginPage from "./components/LoginPage";

function App() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, (currentUser) => {
      setUser(currentUser);
      setLoading(false);
    });
    return () => unsubscribe();
  }, []);

  if (loading) return <p>Loading...</p>;

  return (
    <Router>
      <Routes>
        <Route path="/login" element={!user ? <LoginPage /> : <Navigate to="/" />} />
        <Route path="/" element={user ? <MainPage /> : <Navigate to="/login" />} />
        <Route path="/profile" element={user ? <UserProfilePage /> : <Navigate to="/login" />} />
        <Route path="/profile/create" element={user ? <CreateProfileForm /> : <Navigate to="/login" />} />
        <Route path="/profile/edit" element={user ? <EditProfileForm /> : <Navigate to="/login" />} />
        <Route path="/notifications" element={user ? <NotificationsPage /> : <Navigate to="/login" />} />
      </Routes>
    </Router>
  );
}

export default App;
