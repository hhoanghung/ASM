import React, { useState, useEffect, useCallback } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { createCar, updateCar, getCars } from "../services/api";

const CarForm = () => {
  const [car, setCar] = useState({
    name: "",
    manufacturer: "",
    year: "",
    price: "",
    description: "",
  });
  const navigate = useNavigate();
  const { id } = useParams();

  const fetchCarDetails = useCallback(async () => {
    const cars = await getCars();
    const selectedCar = cars.find((c) => c._id === id);
    setCar(selectedCar || {});
  }, [id]); // Hàm chỉ chạy lại khi `id` thay đổi

  useEffect(() => {
    if (id) {
      fetchCarDetails();
    }
  }, [id, fetchCarDetails]); // Không còn cảnh báo

  const handleChange = (e) => {
    setCar({ ...car, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (id) {
      await updateCar(id, car);
    } else {
      await createCar(car);
    }
    navigate("/");
  };

  return (
    <div className="container">
      <h1>{id ? "Edit Car" : "Add Car"}</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="name"
          placeholder="Name"
          value={car.name}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="manufacturer"
          placeholder="Manufacturer"
          value={car.manufacturer}
          onChange={handleChange}
          required
        />
        <input
          type="number"
          name="year"
          placeholder="Year"
          value={car.year}
          onChange={handleChange}
          required
        />
        <input
          type="number"
          name="price"
          placeholder="Price"
          value={car.price}
          onChange={handleChange}
          required
        />
        <textarea
          name="description"
          placeholder="Description"
          value={car.description}
          onChange={handleChange}
          required
        />
        <button type="submit">Save</button>
      </form>
    </div>
  );
};

export default CarForm;
