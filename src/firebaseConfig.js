// src/firebaseConfig.js
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyCObdZjAe1wd2ticDUl9_NqxaUma2XAfto",
  authDomain: "skillsharing-7659a.firebaseapp.com",
  projectId: "skillsharing-7659a",
  storageBucket: "skillsharing-7659a.firebasestorage.app",
  messagingSenderId: "924217257278",
  appId: "1:924217257278:web:31a21c6084c7eefa3f906f",
  measurementId: "G-2H6522VRFC"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Export auth for use in your app
export const auth = getAuth(app);
