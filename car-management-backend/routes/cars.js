const express = require("express");
const router = express.Router();
const Car = require("../models/car"); // Import model Car

// **Lấy danh sách xe (hỗ trợ tìm kiếm)**
router.get("/", async (req, res) => {
  const { search } = req.query; // Lấy từ khóa tìm kiếm từ query string
  try {
    const query = search
      ? {
          $or: [
            { name: { $regex: search, $options: "i" } }, // Tìm theo tên
            { manufacturer: { $regex: search, $options: "i" } }, // Tìm theo hãng sản xuất
          ],
        }
      : {}; // Nếu không có từ khóa, trả về tất cả
    const cars = await Car.find(query);
    res.json(cars);
  } catch (err) {
    res
      .status(500)
      .json({ message: "Error fetching cars", error: err.message });
  }
});

// **Lấy chi tiết một xe**
router.get("/:id", async (req, res) => {
  try {
    const car = await Car.findById(req.params.id); // Tìm xe theo ID
    if (!car) {
      return res.status(404).json({ message: "Car not found" });
    }
    res.json(car);
  } catch (err) {
    res.status(500).json({ message: "Error fetching car", error: err.message });
  }
});

// **Thêm mới xe**
router.post("/", async (req, res) => {
  const { name, manufacturer, year, price, description } = req.body;
  const car = new Car({
    name,
    manufacturer,
    year,
    price,
    description,
  });
  try {
    const newCar = await car.save();
    res.status(201).json(newCar);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// **Cập nhật thông tin xe**
router.put("/:id", async (req, res) => {
  const { name, manufacturer, year, price, description } = req.body;
  try {
    const updatedCar = await Car.findByIdAndUpdate(
      req.params.id,
      { name, manufacturer, year, price, description },
      { new: true, runValidators: true }
    );
    if (!updatedCar) {
      return res.status(404).json({ message: "Car not found" });
    }
    res.json(updatedCar);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// **Xóa xe**
router.delete("/:id", async (req, res) => {
  try {
    const deletedCar = await Car.findByIdAndDelete(req.params.id); // Xóa xe theo ID
    if (!deletedCar) {
      return res.status(404).json({ message: "Car not found" });
    }
    res.status(204).send(); // Trả về mã 204 (No Content)
  } catch (err) {
    res.status(500).json({ message: "Error deleting car", error: err.message });
  }
});

module.exports = router;
