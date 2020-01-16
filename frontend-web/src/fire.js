import firebase from 'firebase';

const config = {
  apiKey: "AIzaSyDz4egwjPkrIKzp58SYqd6O-diU1_2eU6g",
    authDomain: "stiedharmaagung-a9cbb.firebaseapp.com",
    databaseURL: "https://stiedharmaagung-a9cbb.firebaseio.com",
    projectId: "stiedharmaagung-a9cbb",
    storageBucket: "stiedharmaagung-a9cbb.appspot.com",
    messagingSenderId: "573518049008",
    appId: "1:573518049008:web:24de47eec5c4d77aed1c35",
    measurementId: "G-BV0Z7LC4DR"
  };
  
  var fire = firebase.initializeApp(config);
  fire.analytics();

  export default fire;
