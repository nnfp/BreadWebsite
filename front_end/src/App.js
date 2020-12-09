import React from 'react';
import './App.css';

// React components

const websocket = new WebSocket('ws://localhost:1234/ws');
//requires server running in backend

function App() {
  // const [message, setMessage] = React.useState('');
  // const [messages, setMessages] = React.useState('');
  //listing data
  const [postId, setPostId] = React.useState('');
  const [postOption, setPostOption] = React.useState('');
  const [desc, setDesc] = React.useState('');
  const [type, setType] = React.useState('');
  const [price, setPrice] = React.useState('');
  const [title, setTitle] = React.useState('');

  //can edit this to get all listings possibly
  const handleWebsocketMessage = (messageEvent) => {
    const newMessage = messageEvent.data;
    parseJson(newMessage);
  };

  React.useEffect(() => {
    websocket.addEventListener('message', handleWebsocketMessage);
  }, []);

  websocket.onmessage = function(event) {
    console.log("Websocket message recieved: " +  event);
  };

  function handleClick(){

    var listingData = {
      descData: document.getElementById('input-description').value,
      typeData: document.getElementById('input-type').value,
      priceData: document.getElementById('input-price').value,
      titleData: document.getElementById('input-title').value,
      postId: document.getElementById('post-id').value,
      postOption: document.getElementById('post-option').value
    }

    //sends JSON to back end as a string
    websocket.send(JSON.stringify(listingData));

    //displayListing to be called to render listings on screen after user creates/edit/delete
    // parseJson(newMessage);
    // displayListing(docJson);
  }

  function parseJson(res){
    //console.log('grabJSON MESSAGE RECIEVED: '+ res);
    var docJson = {
      descData: "",
      typeData: "",
      priceData: "",
      titleData: "",
      postId: "nullPost"
    }

    //string manipulation to separate message
    if (res.includes("{")) {
      var copyRes = ""
      copyRes = res.replace('{"_id": ', "");
      var splitArray = copyRes.split("}, ");
      var allData = splitArray[1];
      allData = allData.substring(0, allData.length-1);
      
      //keyValueArray contains all key value pairs
      //order: postId, desc, type, price, title, ts (timestamp)
      
      var keyValueArray = allData.split('", ');

      
      var j,temp,pairHolder;
      var finalArray=[];
      for (j=0;j<6;j++){
        temp = keyValueArray[j];
        //console.log("TEMP: "+ temp);
        pairHolder = temp.split(": ");
        finalArray.push(pairHolder[0]);
        finalArray.push(pairHolder[1]);
      }

      //finalArray contains all data individually split from their key pair values
      //keys are even index numbers, values are odd index numbers
      //loop to remove quotes(") from all elements in finalArray
      var i;
      for(i=0; i<finalArray.length; i++){
        finalArray[i] = finalArray[i].replace('"', '');
      }

      docJson.postId = finalArray[1];
      docJson.descData = finalArray[3];
      docJson.typeData = finalArray[5];
      docJson.priceData = finalArray[7];
      docJson.titleData = finalArray[9];
    }
    console.log("docJson data after parsing: " + JSON.stringify(docJson));

    var blankDoc = Boolean(docJson.postId == "nullPost");
    if(!blankDoc){
      displayListing(docJson);
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
          <div id="listings">
          {/* {messages.slice(0).reverse().map(item => <div key={item.id}>{item}</div>)} */}
          </div>
        </div>
      </div>
      <div className="footer">
          
      </div>
    </div>
  );
}
function displayListing(jsonDoc) {
  let listingDiv = document.createElement('div');
  let listingTitle = document.createElement('p');
  let listingDesc = document.createElement('p');
  let listingType = document.createElement('p');
  let listingPrice = document.createElement('p'); 
  let listingHeader = document.createElement('h3'); 
  listingDiv.setAttribute("id", jsonDoc.postId);
  listingDiv.setAttribute("class", "individual-posts");
  listingTitle.innerHTML = "Title: " + jsonDoc.titleData;
  listingDesc.innerHTML = "Description: " + jsonDoc.descData;
  listingType.innerHTML = "Type: " + jsonDoc.typeData;
  listingPrice.innerHTML = "Price: " + jsonDoc.priceData;
  listingHeader.innerHTML = "POST #" + jsonDoc.postId;
  document.getElementById('listings').appendChild(listingDiv); 
  listingDiv.appendChild(listingHeader);
  listingDiv.appendChild(listingTitle);
  listingDiv.appendChild(listingDesc);
  listingDiv.appendChild(listingType);
  listingDiv.appendChild(listingPrice);
}

export default App;

