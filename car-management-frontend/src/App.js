import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import CarList from "./pages/CarList";
import CarForm from "./pages/CarForm";
import CarDetail from "./pages/CarDetail";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<CarList />} />
        <Route path="/add" element={<CarForm />} />
        <Route path="/edit/:id" element={<CarForm />} />
        <Route path="/detail/:id" element={<CarDetail />} />{" "}
        {/* Đường dẫn chi tiết */}
      </Routes>
    </Router>
  );
}

export default App;
