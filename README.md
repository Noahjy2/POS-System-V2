# POS System V2

A simple Point of Sale (POS) system built with Java, JDBC, and MySQL.

This project is the second version of my POS System. It replaces file-based data storage with a MySQL database using JDBC.

## Features

- Product management
    - Add products
    - View products
    - Search products
    - Update products
    - Delete products
- Shopping cart management
- Add and remove products from cart
- Update product quantity
- Checkout system
- Order management
- Inventory management
- Sales summary
- MySQL database persistence

## Technologies

- Java
- JDBC
- MySQL
- IntelliJ IDEA
- Git & GitHub

## Project Structure

```text
src/
├── MainConsole.java
├── DatabaseConnection.java
├── DatabaseManager.java
├── Product.java
├── ProductManager.java
├── CartItem.java
├── ShoppingCart.java
├── Order.java
└── POSSystem.java