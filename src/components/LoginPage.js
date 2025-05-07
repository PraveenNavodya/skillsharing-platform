// src/components/LoginPage.js

import React from 'react';
import { useNavigate } from 'react-router-dom';
import { getAuth, signInWithPopup, GoogleAuthProvider } from 'firebase/auth';
import { auth } from '../firebaseConfig';
import '../styles/FormStyles.css'; // ✅ Import your existing styles
import '../styles/LoginPage.css';
function LoginPage() {
  const navigate = useNavigate();

  const handleGoogleLogin = async () => {
    const provider = new GoogleAuthProvider();
    try {
      const result = await signInWithPopup(auth, provider);
      const user = result.user;

      const token = await user.getIdToken();
      console.log("🔥 Firebase token:", token);

      localStorage.setItem('token', token);

      const response = await fetch("http://localhost:8081/api/auth", {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json"
        }
      });

      if (response.ok) {
        navigate('/');
      } else {
        alert("Login failed. Try again.");
      }
    } catch (error) {
      console.error("Google sign-in error:", error);
      alert("Login failed. Try again.");
    }
  };

  return (
    <div className="login-wrapper">
      <div className="form-container">
        <h2>Login to SkillSharing</h2>
        <button className="form-button" onClick={handleGoogleLogin}>
          Sign in with Google
        </button>
      </div>
    </div>
  );
}

export default LoginPage;
