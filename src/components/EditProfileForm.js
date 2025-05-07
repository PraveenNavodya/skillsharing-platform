// src/components/EditProfileForm.js
import React, { useState, useEffect } from 'react';
import { auth } from '../firebaseConfig';
import { useNavigate } from 'react-router-dom';
import '../styles/FormStyles.css'; // if not already included

function EditProfileForm() {
  const [form, setForm] = useState({
    fullName: '',
    email: '',
    bio: '',
    country: '',
    gender: '',
    language: ''
  });

  const navigate = useNavigate();

  useEffect(() => {
    const fetchProfile = async () => {
      const user = auth.currentUser;
      if (!user) return;

      const token = await user.getIdToken();

      const res = await fetch("http://localhost:8081/api/user/me", {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (res.ok) {
        const data = await res.json();
        const cleaned = {
          fullName: data.fullName || '',
          email: data.email || '',
          bio: data.bio || '',
          country: data.country || '',
          gender: data.gender || '',
          language: data.language || ''
        };
        setForm(cleaned);
      } else {
        alert("❌ Failed to fetch profile.");
      }
    };

    fetchProfile();
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const user = auth.currentUser;
    if (!user) return;

    const token = await user.getIdToken();

    const res = await fetch("http://localhost:8081/api/user/update", {
      method: 'PUT',
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(form)
    });

    if (res.ok) {
      alert("✅ Profile updated!");
      navigate('/profile');
    } else {
      alert("❌ Failed to update profile.");
    }
  };

  return (
    <div className="form-container">
      <h2>Edit Profile</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-field">
          <label>Full Name:</label>
          <input name="fullName" value={form.fullName} onChange={handleChange} />
        </div>
        <div className="form-field">
          <label>Email:</label>
          <input name="email" value={form.email} readOnly />
        </div>
        <div className="form-field">
          <label>Bio:</label>
          <input name="bio" value={form.bio} onChange={handleChange} />
        </div>
        <div className="form-field">
          <label>Country:</label>
          <input name="country" value={form.country} onChange={handleChange} />
        </div>
        <div className="form-field">
          <label>Gender:</label>
          <input name="gender" value={form.gender} onChange={handleChange} />
        </div>
        <div className="form-field">
          <label>Language:</label>
          <input name="language" value={form.language} onChange={handleChange} />
        </div>

        <button type="submit" className="form-button">Save Changes</button>
      </form>
    </div>
  );
}

export default EditProfileForm;
