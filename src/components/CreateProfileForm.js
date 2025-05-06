// src/components/CreateProfileForm.js
import React, { useState, useEffect } from 'react';
import { auth } from '../firebaseConfig';
import { useNavigate } from 'react-router-dom';

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
      const res = await fetch(`http://localhost:8081/api/user/${user.uid}`, {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (res.ok) {
        const data = await res.json();
        if (data && data.fullName) {
          setProfileExists(true);
        }
      }
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
      const text = await res.text(); // Not res.json()!
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
        {Object.keys(form).map((key) => (
          <input
            key={key}
            name={key}
            placeholder={key}
            value={form[key]}
            onChange={handleChange}
            className="form-field"
          />
        ))}
        <button type="submit" className="form-button">Create Profile</button>
      </form>
    </div>
  );
}

export default CreateProfileForm;
