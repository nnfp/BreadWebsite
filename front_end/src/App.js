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

  //state to hold all listing data into an array
  const [listings, setListings] = React.useState([]);
  //another state to hold the array above into this array
  const [allListings, setAllListings] = React.useState([]);

  //listing data
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
    websocket.send('Websocket now accepting data!');
  };

  React.useEffect(() => {
    websocket.addEventListener('message', handleWebsocketMessage);
    
    // try{
    // //call get request to backend to get all listings
    // axios.get('/api/viewListings')
    //   .then(function (res){
    //     try{
    //       //replace elements to show item data
    //       //probably better to just replace 1 element
    //     document.getElementById('XD').innerHTML = res.data.items[0].description;
    //     document.getElementById('2').innerHTML = res.data.items[0].type;
    //     document.getElementById('3').innerHTML = res.data.items[0].price;
    //     document.getElementById('4').innerHTML = res.data.items[0].title;
    //     }
    //     catch(e){
    //       console.log(e);
    //     }
    //   });
    // }
    // catch(e){
    //   console.log(e);
    // }
  }, []);
  function handleClick(){
    //post req to backend to create a new listing
    // axios.post('/api/createListing', {
    //   description: document.getElementById('input-description').value,
    //   type: document.getElementById('input-type').value,
    //   price: document.getElementById('input-price').value,
    //   title: document.getElementById('input-title').value
    // })
    //   .then(function (res){
    //     console.log('created new listing');
    //   });
    // axios.get('/api/viewListings', {
    // })
    //   .then(function (res){
    //     //should refresh listings after creating
    //     console.log('//');
    //   });

    var listingData = {
      descData: document.getElementById('input-description').value,
      typeData: document.getElementById('input-type').value,
      priceData: document.getElementById('input-price').value,
      titleData: document.getElementById('input-title').value
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
      <h1>Final Project App</h1>
      <div key='xd'>
          <input value={message} onChange={e => setMessage(e.target.value)} />
          <input id='input-description' value={desc} onChange={e => setDesc(e.target.value)} >
          </input>
          <input id='input-type' value={type} onChange={e => setType(e.target.value)} >
          </input>
          <input id='input-price' value={price} onChange={e => setPrice(e.target.value)} >
          </input>
          <input id='input-title' value={title} onChange={e => setTitle(e.target.value)}>
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
