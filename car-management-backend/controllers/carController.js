const Car = require("../models/car");

// Lấy danh sách xe
exports.getCars = async (req, res) => {
  try {
    const cars = await Car.find();
    res.json(cars);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
};

// Thêm mới xe
exports.createCar = async (req, res) => {
  try {
    const car = new Car(req.body);
    const newCar = await car.save();
    res.status(201).json(newCar);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
};

// Cập nhật thông tin xe
exports.updateCar = async (req, res) => {
  try {
    const updatedCar = await Car.findByIdAndUpdate(req.params.id, req.body, {
      new: true,
    });
    res.json(updatedCar);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
};

// Xóa xe
exports.deleteCar = async (req, res) => {
  try {
    await Car.findByIdAndDelete(req.params.id);
    res.status(204).send();
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
};
