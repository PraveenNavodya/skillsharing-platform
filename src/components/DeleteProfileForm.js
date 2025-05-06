import React, { useState } from 'react';
import axios from 'axios';
import './FormStyles.css';

function DeleteProfileForm() {
  const [id, setId] = useState('');

  const handleDelete = async (e) => {
    e.preventDefault();
    try {
      await axios.delete(`http://localhost:8081/api/user/delete/${id}`);
      alert("🗑️ Profile deleted!");
    } catch (err) {
      alert("❌ Error: " + err.message);
    }
  };

  return (
    <form className="form-container" onSubmit={handleDelete}>
      <h2>Delete Profile Details</h2>
      <input
        className="form-field"
        name="id"
        placeholder="Enter User ID"
        value={id}
        onChange={(e) => setId(e.target.value)}
      />
      <button className="form-button" type="submit">Delete</button>
    </form>
  );
}

export default DeleteProfileForm;
