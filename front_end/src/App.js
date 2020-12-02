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

  //can edit this to get all listings possibly
  const handleWebsocketMessage = (messageEvent) => {
    const newMessages = JSON.parse(messageEvent.data);
    setMessages(newMessages);
  };

  React.useEffect(() => {
    websocket.addEventListener('message', handleWebsocketMessage);
    try{
    //call get request to backend to get all listings
    axios.get('/api/viewListings')
      .then(function (res){
        try{
          //replace elements to show item data
          //probably better to just replace 1 element
        document.getElementById('XD').innerHTML = res.data.items[0].description;
        document.getElementById('2').innerHTML = res.data.items[0].type;
        document.getElementById('3').innerHTML = res.data.items[0].price;
        document.getElementById('4').innerHTML = res.data.items[0].title;
        }
        catch(e){
          console.log(e);
        }
      });
    }
    catch(e){
      console.log(e);
    }
  }, []);
  function handleClick(){
    //post req to backend to create a new listing
    axios.post('/api/createListing', {
      description: document.getElementById('input-description').value,
      type: document.getElementById('input-type').value,
      price: document.getElementById('input-price').value,
      title: document.getElementById('input-title').value
    })
      .then(function (res){
        console.log('created new listing');
      });
    axios.get('/api/viewListings', {
    })
      .then(function (res){
        //should refresh listings after creating
        console.log('//');
      });
    setMessage('');
    //refresh listings
    websocket.send(message);
  }
  return (
    <div className="App">
      <h1>Final Project App</h1>
        <div key='xd'>
          <input value={message} onChange={e => setMessage(e.target.value)} />
          <input id='input-description'>
          </input>
          <input id='input-type'>
          </input>
          <input id='input-price'>
          </input>
          <input id='input-title'>
          </input>
          <button id='submit' onClick={handleClick}>
            Submit
          </button>
        </div>
        <div key="kekw">
          <h2>Messages:</h2>
          {messages.map(item => <div key={item.id}>{item}</div>)}
        </div>

      <nav>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/admin">Admin</Link>
          </li>
          <li>
            <Link to="/feed">Feed</Link>
          </li>
        </ul>
      </nav>
      <Switch>
        <Route path="/admin">
          <Admin />
        </Route>
        <Route path="/feed">
          <Feed />
        </Route>
        <Route path="/">
          <Home />
        </Route>
      </Switch>
    </div>
  );
}

export default App;
