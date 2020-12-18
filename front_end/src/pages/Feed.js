import React from 'react';
import getSocket from './Admin';
import ws from "../SocketProvider";

function Feed() {
  console.log(getSocket());
  const [filterType, setFilterType] = React.useState("");
  function handleFilter() {
    var filter = 'filter ' + document.getElementById('input-type').value;
    console.log(filter);
    ws.send(filter);
  }
  return (
    <div className="feed-container">
      <div className="feed-input">
        <h2 className="feed-header">Filter Posts</h2>
        <form>
          <select
            id="input-type"
            value={filterType}
            onChange={e => setFilterType(e.target.value)}>
            <option value="" disabled defaultValue>
              Bread Type
            </option>
            <option value="Banana Bread">Banana Bread</option>
            <option value="Baguette">Baguette</option>
            <option value="Brioche">Brioche</option>
            <option value="Challah">Challah</option>
            <option value="Ciabatta">Ciabatta</option>
            <option value="Cornbread">Cornbread</option>
            <option value="Focaccia">Focaccia</option>
            <option value="Multigrain Bread">Multigrain Bread</option>
            <option value="Pita Bread">Pita Bread</option>
            <option value="Pumpernickel">Pumpernickel</option>
            <option value="Rye Bread">Rye Bread</option>
            <option value="Sourdough">Sourdough</option>
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

