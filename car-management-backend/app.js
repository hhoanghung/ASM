const express = require("express");
const mongoose = require("mongoose");
const cors = require("cors");
const carRoutes = require("./routes/cars");

require("dotenv").config();

const app = express();
app.use(cors());
app.use(express.json()); // Để xử lý JSON từ client

// Kết nối MongoDB
mongoose
  .connect(process.env.MONGO_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => console.log("Connected to MongoDB"))
  .catch((err) => console.error("Error connecting to MongoDB:", err));

// Sử dụng routes
app.use("/api/cars", carRoutes);

// Khởi động server
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
