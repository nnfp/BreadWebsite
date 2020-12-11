import React from 'react';

function Feed() {
  const [filterType, setFilterType] = React.useState("");
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
        <div id="feed-listings"></div>
      </div>
    </div>
  );
}
function handleFilter() {
  window.location.reload();
}
export default Feed;
