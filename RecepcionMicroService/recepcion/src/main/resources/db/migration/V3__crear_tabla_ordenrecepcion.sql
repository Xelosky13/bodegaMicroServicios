CREATE TABLE ordenrecepcion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecharecepcion DATETIME NOT NULL,
    proveedor_id INT NOT NULL,
    detallesrecepcion_id INT NOT NULL
);   

