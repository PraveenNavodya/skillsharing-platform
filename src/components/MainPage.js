// src/components/MainPage.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { signOut } from 'firebase/auth';
import { auth } from '../firebaseConfig';
import '../styles/MainPage.css';
import '../index.css';

function MainPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');

  useEffect(() => {
    const currentUser = auth.currentUser;
    if (currentUser) {
      setEmail(currentUser.email);
    }
  }, []);

  const handleSignOut = async () => {
    try {
      await signOut(auth);
      localStorage.removeItem("token");
      navigate('/login');
    } catch (error) {
      console.error("Sign out failed:", error);
    }
  };

  return (
    <div className="main-page-container">
      <h2>Welcome to SkillSharing!</h2>
      <p className="user-info">Signed in as: <strong>{email}</strong></p>

      <div className="button-column">
        <button onClick={() => navigate('/profile')}>👤 My Details</button>
        <button onClick={() => navigate('/notifications')}>🔔 Notifications</button>
        <button onClick={() => navigate('/create-post')}>➕ Add Post</button>
        <button onClick={() => navigate('/posts')}>📄 View Posts</button>
        <button onClick={() => navigate('/my-posts')}>🗂️ My Posts</button> {/* 👈 New Button */}
        <button className="logout" onClick={handleSignOut}>🚪 Sign Out</button>
      </div>
    </div>
  );
}

export default MainPage;
