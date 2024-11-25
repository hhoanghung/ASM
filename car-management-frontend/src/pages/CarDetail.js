import React, { useState, useEffect, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getCarById } from "../services/api";

const CarDetail = () => {
  const { id } = useParams(); // Lấy ID từ URL
  const navigate = useNavigate();
  const [car, setCar] = useState(null);

  // Định nghĩa hàm fetchCarDetail với useCallback
  const fetchCarDetail = useCallback(async () => {
    const data = await getCarById(id); // Gọi API để lấy chi tiết xe
    setCar(data);
  }, [id]);

  useEffect(() => {
    fetchCarDetail();
  }, [fetchCarDetail]); // Thêm fetchCarDetail vào mảng dependency

  if (!car) {
    return <div className="container">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>Car Details</h1>
      <div style={{ marginBottom: "20px" }}>
        <button onClick={() => navigate("/")} className="add-button">
          Back to List
        </button>
      </div>
      <table>
        <tbody>
          <tr>
            <th>Name</th>
            <td>{car.name}</td>
          </tr>
          <tr>
            <th>Manufacturer</th>
            <td>{car.manufacturer}</td>
          </tr>
          <tr>
            <th>Year</th>
            <td>{car.year}</td>
          </tr>
          <tr>
            <th>Price</th>
            <td>
              {car.price.toLocaleString("vi-VN", {
                style: "currency",
                currency: "VND",
              })}
            </td>
          </tr>
          <tr>
            <th>Description</th>
            <td>{car.description}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default CarDetail;
