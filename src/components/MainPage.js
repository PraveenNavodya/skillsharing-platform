// src/components/MainPage.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { signOut } from 'firebase/auth';
import { auth } from '../firebaseConfig';

function MainPage() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');

  useEffect(() => {
    const currentUser = auth.currentUser;
    if (currentUser) {
      setEmail(currentUser.email); // or use .displayName
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
    <div>
      <h2>Welcome to SkillSharing!</h2>
      <p>Signed in as: <strong>{email}</strong></p>
      <button onClick={() => navigate('/profile')}>My Details</button>
      <button onClick={() => navigate('/notifications')}>Notifications</button>
      <br /><br />
      <button onClick={handleSignOut}>Sign Out</button>
    </div>
  );
}

export default MainPage;
