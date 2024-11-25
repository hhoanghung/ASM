import React, { useEffect, useState, useCallback } from "react";
import { getCars, deleteCar } from "../services/api";
import { useNavigate } from "react-router-dom";

const CarList = () => {
  const [cars, setCars] = useState([]);
  const [search, setSearch] = useState(""); // Từ khóa tìm kiếm
  const navigate = useNavigate();

  // Định nghĩa hàm fetchCars với useCallback
  const fetchCars = useCallback(async () => {
    const data = await getCars(search); // Gọi API với từ khóa tìm kiếm
    setCars(data);
  }, [search]);

  useEffect(() => {
    fetchCars();
  }, [fetchCars]); // Thêm fetchCars vào mảng dependency

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this car?")) {
      await deleteCar(id);
      fetchCars();
    }
  };

  return (
    <div className="container">
      <h1>Car List</h1>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          marginBottom: "20px",
        }}
      >
        <button className="add-button" onClick={() => navigate("/add")}>
          Add New Car
        </button>
        <input
          type="text"
          placeholder="Search cars..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          style={{
            padding: "10px",
            border: "1px solid #ddd",
            borderRadius: "4px",
            width: "200px",
          }}
        />
      </div>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Manufacturer</th>
            <th>Year</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {cars.map((car) => (
            <tr key={car._id}>
              <td>{car.name}</td>
              <td>{car.manufacturer}</td>
              <td>{car.year}</td>
              <td>
                {car.price.toLocaleString("vi-VN", {
                  style: "currency",
                  currency: "VND",
                })}
              </td>
              <td>
                <button onClick={() => navigate(`/edit/${car._id}`)}>
                  Edit
                </button>
                <button onClick={() => navigate(`/detail/${car._id}`)}>
                  View
                </button>
                <button onClick={() => handleDelete(car._id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CarList;
