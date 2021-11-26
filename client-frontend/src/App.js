import './App.css';
import NavbarMain from "./components/navbar/Navbar-main";
import Login from "./components/login/Login";
import Profile from "./components/profile/Profile";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
      <Router>
          <div className="App">
            <NavbarMain/>
            <Routes>
                <Route path="/profile" element={<Profile />}/>
                <Route path="/" element={<Login/>}/>
            </Routes>
        </div>
      </Router>
  );
}

export default App;
