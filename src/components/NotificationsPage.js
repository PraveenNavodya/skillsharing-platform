import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './FormStyles.css';

function NotificationsPage({ userId }) {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8081/api/notifications/user/${userId}`)
      .then(response => {
        setNotifications(response.data);
      })
      .catch(error => {
        console.error("Error fetching notifications:", error);
      });
  }, [userId]);

  return (
    <div className="form-container">
      <h2>🔔 Notifications</h2>
      {notifications.length === 0 ? (
        <p>No notifications yet.</p>
      ) : (
        <ul>
          {notifications.map((notif) => (
            <li key={notif.id} className={notif.seen ? 'seen' : 'unseen'}>
              <strong>{notif.message}</strong>
              <br />
              <small>{new Date(notif.timestamp).toLocaleString()}</small>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default NotificationsPage;
