CREATE TABLE itemsPedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);