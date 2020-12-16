import React from 'react';
import getSocket from './Admin';
//import handleClick from './Admin';

const websocket = require('./Admin.js');

function Feed() {
  //comment out line below to stop all listings from showing
  console.log(getSocket());
  const [filterType, setFilterType] = React.useState("");
  const handleWebsocketMessage = (messageEvent) => {
  const newRes = messageEvent.data;
  
  //calls function that parses incoming JSON and calls another function which renders it
  // parseJson(newRes);
};

  function handleFilter() {
    // websocket.send(`filter ${document.getElementById('input-type').value}`);
    window.location.reload();
  }
  function getListings(){
    fetch(`/getNotes`)
        .then(res => res.json()) // async (parse json)
        .then(data => console.log(data)); // also async
  }
  
  return (
    <div className="feed-container">
      <div className="feed-input">
        <h2 className="feed-header">Filter Posts</h2>
        <form>
          <select
            id="input-type"
            value={filterType}
            onChange={(e) => setFilterType(e.target.value)}>
            <option value="" disabled defaultValue>
              Bread Type
            </option>
            <option value="banana_bread">Banana Bread</option>
            <option value="baguette">Baguette</option>
            <option value="brioche">Brioche</option>
            <option value="challah">Challah</option>
            <option value="ciabatta">Ciabatta</option>
            <option value="cornbread">Cornbread</option>
            <option value="focaccia">Focaccia</option>
            <option value="multigrain_bread">Multigrain Bread</option>
            <option value="pita_bread">Pita Bread</option>
            <option value="pumpernickel">Pumpernickel</option>
            <option value="rye_bread">Rye Bread</option>
            <option value="sourdough">Sourdough</option>
          </select>
        </form>
        <button id="feed-submit" onClick={handleFilter}>
          Submit
        </button>
      </div>
      <div className="feed-listings-header">
        <h2 id="listings-header">Feed Listings:</h2>
        <div id="listings"></div>
      </div>
    </div>
  );
}

export default Feed;

