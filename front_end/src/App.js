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

  //listing handler
  websocket.onopen = function(event) {
    //websocket.send('Websocket now accepting data!');
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
    //refresh listings
    //websocket.send(listings);
    //single messages being sent
    setMessage('');
    websocket.send(message);
    //sending listingData object as json formatted to string
    websocket.send(JSON.stringify(listingData));
  }
  return (
    <div className="App">
      <div class="grid-container">
        <div class ="grid-header" id="website-header">
          <h1>Let's Get This Bread!</h1>
        </div>
        <nav class="grid-nav">
        <Link to="/">Home </Link>
        <Link to="/feed">Feed</Link>
        </nav>
        <div class="grid-post">
          <form class="grid-post-input">
          <input value={message} onChange={e => setMessage(e.target.value)} placeholder="message"/>
            <input id='input-description' value={desc} onChange={e => setDesc(e.target.value)} placeholder="Description">
            </input>
            <select id='input-type' value={type} onChange={e => setType(e.target.value)}>
              <option value="" disabled selected>Bread Type</option>
              <option value="bread 1">bread 1</option>
              <option value="bread 2">bread 2</option>
              <option value="bread 3">bread 3</option>
              <option value="bread 4">bread 4</option>
            </select>
            <input id='input-price' value={price} onChange={e => setPrice(e.target.value)} placeholder="Price">
            </input>
            <input id='input-title' value={title} onChange={e => setTitle(e.target.value)} placeholder="Listing Title">
            </input>
            <input id='post-id' value={postId} onChange={e => setPostId(e.target.value)} placeholder="Post ID">
            </input>
            <select id='post-option' value={postOption} onChange={e => setPostOption(e.target.value)}>
              <option value="" disabled selected>Post Option</option>
              <option value="create">Create</option>
              <option value="edit">Edit</option>
              <option value="delete">Delete</option>
            </select>
          </form>
          <button id='submit' class="grid-submit" onClick={handleClick}>
              Submit
            </button>
        </div>
        <div class="grid-listing">
          <h2>Listings:</h2>
            {messages.map(item => <div key={item.id}>{item}</div>)}
        </div>
      </div>
    </div>
  );
}

export default App;
