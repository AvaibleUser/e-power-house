-- Se realizan ventas
INSERT INTO ventas.venta (cui_empleado, nit_cliente, id_sucursal, fecha, descuento)
    SELECT cui, 'CF', 1, '2022-11-02', 0.00 FROM empleados.empleado
        WHERE id_sucursal = 1 ORDER BY RANDOM() LIMIT 5;

INSERT INTO ventas.venta (cui_empleado, nit_cliente, id_sucursal, fecha, descuento)
    SELECT cui, '2345678901234', 2, '2023-01-06', 0.00 FROM empleados.empleado
        WHERE id_sucursal = 2 ORDER BY RANDOM() LIMIT 3;

INSERT INTO ventas.venta (cui_empleado, nit_cliente, id_sucursal, fecha, descuento)
    SELECT cui, '5678901234567', 2, '2022-11-17', 0.00 FROM empleados.empleado
        WHERE id_sucursal = 2 ORDER BY RANDOM() LIMIT 2;

INSERT INTO ventas.venta (cui_empleado, nit_cliente, id_sucursal, fecha, descuento)
    SELECT cui, '7890123456789', 3, '2022-12-12', 0.00 FROM empleados.empleado
        WHERE id_sucursal = 3 ORDER BY RANDOM() LIMIT 1;

INSERT INTO ventas.venta (cui_empleado, nit_cliente, id_sucursal, fecha, descuento)
    SELECT cui, '8901234567890', 3, '2023-02-02', 0.00 FROM empleados.empleado
        WHERE id_sucursal = 3 ORDER BY RANDOM() LIMIT 4;

-- Se registran los productos que se compraron en la venta
INSERT INTO ventas.detalle_venta (id_venta, id_producto, cantidad, precio_unidad)
    SELECT MOD(ROW_NUMBER() OVER (), 5) + 1, id, ROUND(RANDOM() * 3), precio_unidad FROM ventas.producto ORDER BY RANDOM() LIMIT 12;
