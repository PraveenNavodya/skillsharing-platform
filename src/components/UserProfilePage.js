// src/components/UserProfilePage.js
import React, { useEffect, useState } from 'react';
import { auth } from '../firebaseConfig';
import { useNavigate } from 'react-router-dom';

function UserProfilePage() {
  const [hasProfile, setHasProfile] = useState(false);
  const [profileData, setProfileData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const checkExistingProfile = async () => {
      const user = auth.currentUser;
      if (!user) return;

      const token = await user.getIdToken();

      const res = await fetch("http://localhost:8081/api/user/me", {
        headers: { Authorization: `Bearer ${token}` }
      });

      let data = {};
      try {
        data = await res.json(); // This avoids crashing on empty response
      } catch (e) {
        console.warn("No JSON response received:", e);
      }

      if (data && data.fullName) {
        setHasProfile(true);
        setProfileData(data);
      } else {
        setHasProfile(false);
      }
    };

    checkExistingProfile();
  }, []);

  const handleCreate = () => navigate('/profile/create');
  const handleEdit = () => navigate('/profile/edit');
  const handleClear = async () => {
    const token = await auth.currentUser.getIdToken();
    await fetch("http://localhost:8081/api/user/delete", {
      method: "DELETE",
      headers: { Authorization: `Bearer ${token}` }
    });
    setHasProfile(false);
    setProfileData(null);
  };

  return (
    <div className="form-container">
      <h2>My Profile</h2>
      {hasProfile && profileData ? (
        <div>
          <p><strong>Name:</strong> {profileData.fullName}</p>
          <p><strong>Email:</strong> {profileData.email}</p>
          <p><strong>Bio:</strong> {profileData.bio}</p>
          <p><strong>Country:</strong> {profileData.country}</p>
          <p><strong>Gender:</strong> {profileData.gender}</p>
          <p><strong>Language:</strong> {profileData.language}</p>
          <button onClick={handleEdit}>Edit Profile</button>
          <button onClick={handleClear}>Clear Profile</button>
        </div>
      ) : (
        <div>
           <p>No profile details found.</p>
           <button onClick={handleCreate}>Add Details</button>
        </div>
      )}
    </div>
  );
}

export default UserProfilePage;
