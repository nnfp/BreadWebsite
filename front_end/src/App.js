import React from 'react';
import { Switch, Route, Link } from 'react-router-dom';
import Admin from './pages/Admin';
import Feed from './pages/Feed';
import Home from './pages/Home';
import axios from 'axios';
import './App.css';

// React components

const websocket = new WebSocket('ws://localhost:1234/ws');
//requires server running in backend

function App() {
  const [message, setMessage] = React.useState('');
  const [messages, setMessages] = React.useState(['']);
  //listing data
  const [postId, setPostId] = React.useState('');
  const [postOption, setPostOption] = React.useState('');
  const [desc, setDesc] = React.useState('');
  const [type, setType] = React.useState('');
  const [price, setPrice] = React.useState('');
  const [title, setTitle] = React.useState('');

  //can edit this to get all listings possibly
  const handleWebsocketMessage = (messageEvent) => {
    const newMessages = JSON.parse(messageEvent.data);
    setMessages(newMessages);
  };
  React.useEffect(() => {
    websocket.addEventListener('message', handleWebsocketMessage);
  }, []);
  function handleClick(){
    var listingData = {
      descData: document.getElementById('input-description').value,
      typeData: document.getElementById('input-type').value,
      priceData: document.getElementById('input-price').value,
      titleData: document.getElementById('input-title').value,
      postId: document.getElementById('post-id').value,
      postOption: document.getElementById('post-option').value
    }
  }
  return (
    <div className="App">
      <div className="website-header site-banner">
          <h1>Let's Get This Bread!</h1>
        </div>
      <div className="grid-container">
        
        <nav className="grid-nav website-header">
        <a href="/" className="nav-button" id="home">Home</a>
        <a href="/feed" className="nav-button">Feed</a>
        </nav>
        <div className="grid-post" key="app-key">
          <form className="grid-post-input">
            <h2 id="post-header">Create a Post:</h2>
            <input id='input-title' value={title} onChange={e => setTitle(e.target.value)} placeholder="Listing Title">
            </input>
            <br />
            <input id='input-description' value={desc} onChange={e => setDesc(e.target.value)} placeholder="Description">
            </input>
            <br />
            <input id='input-price' value={price} onChange={e => setPrice(e.target.value)} placeholder="Price">
            </input>
            <br />
            <input id='post-id' value={postId} onChange={e => setPostId(e.target.value)} placeholder="Post ID">
            </input>
            <br />
            <select id='input-type' value={type} onChange={e => setType(e.target.value)}>
              <option value="" disabled defaultValue>Bread Type</option>
            </select>
            <select id='post-option' value={postOption} onChange={e => setPostOption(e.target.value)}>
              <option value="" disabled defaultValue>Post Option</option>
              <option value="create">Create</option>
              <option value="edit">Edit</option>
              <option value="delete">Delete</option>
            </select>
          </form>
          <button id='submit' className="grid-submit" onClick={handleClick}>
              Submit
            </button>
        </div>
        <div className="grid-listing website-header" id="listings-header">
          <h2>Listings</h2>
            {messages.map(item => <div key={item.id}>{item}</div>)}
        </div>
      </div>
      <div className="footer">
          
      </div>
    </div>
  );
}

export default App;

