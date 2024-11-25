import axios from "axios";

const API_BASE_URL = "http://localhost:5000/api/cars";

// Lấy danh sách xe (có hỗ trợ tìm kiếm)
export const getCars = async (search = "") => {
  const response = await axios.get(`${API_BASE_URL}?search=${search}`);
  return response.data;
};

// Lấy chi tiết một xe theo ID
export const getCarById = async (id) => {
  const response = await axios.get(`${API_BASE_URL}/${id}`);
  return response.data;
};

// Thêm mới xe
export const createCar = async (car) => {
  const response = await axios.post(API_BASE_URL, car);
  return response.data;
};

// Cập nhật thông tin xe
export const updateCar = async (id, car) => {
  const response = await axios.put(`${API_BASE_URL}/${id}`, car);
  return response.data;
};

// Xóa xe
export const deleteCar = async (id) => {
  const response = await axios.delete(`${API_BASE_URL}/${id}`);
  return response.data;
};
