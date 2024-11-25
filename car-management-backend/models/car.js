const mongoose = require("mongoose");

const carSchema = new mongoose.Schema({
  name: { type: String, required: true },
  manufacturer: { type: String, required: true },
  year: { type: Number, required: true },
  price: { type: Number, required: true },
  description: { type: String, required: true },
  image: { type: String }, // URL hoặc đường dẫn file
});

module.exports = mongoose.model("Car", carSchema);
