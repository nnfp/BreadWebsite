import React from 'react';

const websocket = new WebSocket('ws://localhost:1234/ws');
var s;


function Feed() {
  const [filterType, setFilterType] = React.useState("");
  const handleWebsocketMessage = (messageEvent) => {
  const newRes = messageEvent.data;
  //calls function that parses incoming JSON and calls another function which renders it
  parseJson(newRes);
};
React.useEffect(() => {
  websocket.addEventListener('message', handleWebsocketMessage);
}, []);

  function handleFilter() {
    s = document.getElementById('input-type');
    websocket.send('filter '+ document.getElementById('input-type').value);
    window.location.reload();
  }
  function parseJson(res){
    //console.log("FUNCTION BEGUN: parseJson()");
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
        if(temp !== undefined){
          pairHolder = temp.split(": ");
          finalArray.push(pairHolder[0]);
          finalArray.push(pairHolder[1]);
        }
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

    var blankDoc = Boolean(docJson.postId === "nullPost");
      if(!blankDoc){
        //console.log("END OF parseJson(): docJson displaying and pushing postId(" + docJson.postId + ").");
        //docJsonList.push(docJson);
        displayListing(docJson);
      }
      //console.log("docJsonList currently holds: " + JSON.stringify(docJsonList));
    }

  function displayListing(jsonDoc) {
    //if(jsonDoc.typeData.toString() !== filterType){
      console.log(jsonDoc.typeData.toString());
      console.log(filterType);
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
      document.getElementById('feed-listings').appendChild(listingDiv); 
      listingDiv.appendChild(listingHeader);
      listingDiv.appendChild(listingTitle);
      listingDiv.appendChild(listingDesc);
      listingDiv.appendChild(listingType);
      listingDiv.appendChild(listingPrice);
    //}
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
        <div id="feed-listings"></div>
      </div>
    </div>
  );
}

export default Feed;
