-- Registro de sucursales y bodega
INSERT INTO ventas.sucursal (nombre, direccion, telefono)
VALUES
    ('Sucursal Central', '5a. Avenida 12-34, Zona 1, Quetzaltenango', '+502 1234-5678'),
    ('Sucursal Norte', '6a. Calle 1-23, Zona 2, La Esperanza', '+502 2345-6789'),
    ('Sucursal Sur', '10a. Avenida 20-30, Zona 3, Zunil', '+502 3456-7890');

INSERT INTO inventario.bodega (nombre, direccion, telefono) 
VALUES
    ('Bodega Central', '6a Avenida 0-30, Zona 4, Quetzaltenango', '+502 5678-1234');

-- Llenando stocks e inventarios de sucursales y la bodega
INSERT INTO ventas.stock (id_sucursal, id_producto, cantidad)
    SELECT 1, id, ROUND(RANDOM()*100) FROM ventas.producto ORDER BY RANDOM() LIMIT 30;

INSERT INTO ventas.stock (id_sucursal, id_producto, cantidad)
    SELECT 2, id, ROUND(RANDOM()*100) FROM ventas.producto ORDER BY RANDOM() LIMIT 25;

INSERT INTO ventas.stock (id_sucursal, id_producto, cantidad)
    SELECT 3, id, ROUND(RANDOM()*100) FROM ventas.producto ORDER BY RANDOM() LIMIT 15;

INSERT INTO inventario.inventario (id_bodega, id_producto, cantidad)
    SELECT 1, id, ROUND(RANDOM()*100) FROM ventas.producto ORDER BY RANDOM() LIMIT 30;
