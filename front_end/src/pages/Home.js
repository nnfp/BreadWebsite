import React from 'react';
import bread from '../images/bread-banner.jpg';
function Home() {
  return (
    <div>
      <img src={bread} alt="bread banner" id="bread-banner"></img>
      <div className="mission-statement">
        <p>Hello, we are a small team dedicated to the sharing the love of warm, fresh bread to people around them.</p>
      </div>
    </div>
  );
}

export default Home;