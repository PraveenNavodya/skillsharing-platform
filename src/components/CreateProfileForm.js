// src/components/CreateProfileForm.js
import React, { useState, useEffect } from 'react';
import { auth } from '../firebaseConfig';
import { useNavigate } from 'react-router-dom';
import '../styles/FormStyles.css';  // ✅ correct if CSS is in src/styles

function CreateProfileForm() {
  const [form, setForm] = useState({
    fullName: '',
    email: '',
    bio: '',
    country: '',
    gender: '',
    language: '',
    profilePictureUrl: ''
  });

  const [profileExists, setProfileExists] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const checkExistingProfile = async () => {
      const user = auth.currentUser;
      if (!user) return;

      const token = await user.getIdToken();
      const res = await fetch("http://localhost:8081/api/user/me", {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (res.ok) {
        const data = await res.json();
        if (data && data.fullName) {
          setProfileExists(true);
        }
      }

      // Set email in form
      setForm((prev) => ({ ...prev, email: user.email }));
    };

    checkExistingProfile();
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const user = auth.currentUser;
    if (!user) return;

    const token = await user.getIdToken();

    const res = await fetch("http://localhost:8081/api/user/create", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(form)
    });

    if (res.ok) {
      alert("✅ Profile created!");
      navigate('/profile');
    } else {
      const text = await res.text();
      alert("❌ Failed to create profile: " + text);
    }
  };

  if (profileExists) {
    return (
      <div className="form-container">
        <h3>✅ Profile already exists</h3>
        <button onClick={() => navigate('/profile')}>View My Profile</button>
      </div>
    );
  }

  return (
    <div className="form-container">
      <h2>Create Profile</h2>
      <form onSubmit={handleSubmit}>
        {Object.entries(form).map(([key, value]) => (
          <div key={key} className="form-field">
            <label htmlFor={key}>{key.charAt(0).toUpperCase() + key.slice(1)}:</label>
            <input
              id={key}
              name={key}
              type="text"
              placeholder={key}
              value={value}
              onChange={handleChange}
              readOnly={key === 'email'}
            />
          </div>
        ))}
        <button type="submit" className="form-button">Create Profile</button>
      </form>
    </div>
  );
}

export default CreateProfileForm;
