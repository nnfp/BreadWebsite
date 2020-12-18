import React from 'react';
import { Switch, Route, Link } from 'react-router-dom';
import Admin from './pages/Admin';
import Feed from './pages/Feed';
import Home from './pages/Home';
import './App.css';
// React components
function App() {
  return (
    <div className="App">
      <div className="website-header site-banner">
          <h1>Let's Get This Bread!</h1>
        </div>
        <nav className="grid-nav website-header">
        <Link to="/" className="nav-button" >Home</Link>
        <Link to="/admin" className="nav-button">Admin</Link>
        <Link to="/feed" className="nav-button">Feed</Link>
        </nav>
      <Switch>
        <Route path="/admin" onEnter={() => window.location.reload()}>
          <Admin />
        </Route>
        <Route path="/feed" onEnter={() => window.location.reload()}>
          <Feed />
        </Route>
        <Route path="/" >
          <Home />
        </Route>
      </Switch>
    </div>
  );
}

export default App;

