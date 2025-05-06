import React, { useState, useEffect } from 'react';
import { auth } from '../firebaseConfig';
import { useNavigate } from 'react-router-dom';

function EditProfileForm() {
  const [form, setForm] = useState({
    fullName: '',
    email: '',
    bio: '',
    country: '',
    gender: '',
    language: '',
    profilePictureUrl: ''
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
        setForm(data);
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
        <button type="submit" className="form-button">Save Changes</button>
      </form>
    </div>
  );
}

export default EditProfileForm;
