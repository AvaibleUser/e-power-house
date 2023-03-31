\c e_power_house

-- Registro de empleados de ventas
INSERT INTO empleados.empleado (cui, nombre, apellido, id_sucursal, puesto_trabajo, fecha_nacimiento, direccion, correo_electronico, contrasena, telefono)
VALUES 
    ('1234567890102', 'Juan', 'Pérez', 1, 'vendedor', '1995-04-12', '4a Calle 3-23, Zona 3, Quetzaltenango', 'juan.perez@ejemplo.com', 'seguro123', '+502 1234-5678'),
    ('7894561230103', 'María', 'López', 1, 'vendedor', '1992-10-27', '2a Avenida 10-11, Zona 1, Quetzaltenango', 'maria.lopez@ejemplo.com', 'seguro123', '+502 2345-6789'),
    ('4561237890104', 'Pedro', 'García', 1, 'vendedor', '1990-02-14', '6a Calle 15-31, Zona 7, Cantel', 'pedro.garcia@ejemplo.com', 'seguro123', '+502 3456-7890'),
    ('9876543210105', 'Lucía', 'Hernández', 2, 'vendedor', '1998-08-08', '1a Avenida 12-42, Zona 2, Quetzaltenango', 'lucia.hernandez@ejemplo.com', 'seguro123', '+502 4567-8901'),
    ('6543219870106', 'Jorge', 'Martínez', 2, 'vendedor', '1994-05-22', '8a Calle 5-27, Zona 8, Quetzaltenango', 'jorge.martinez@ejemplo.com', 'seguro123', '+502 5678-9012'),
    ('3219876540107', 'Marta', 'González', 2, 'vendedor', '1991-11-17', '3a Avenida 8-10, Zona 4, Quetzaltenango', 'marta.gonzalez@ejemplo.com', 'seguro123', '+502 6789-0123'),
    ('7418529630108', 'Luis', 'Sánchez', 3, 'vendedor', '1997-03-03', '9a Calle 2-15, Zona 6, Coatepeque', 'luis.sanchez@ejemplo.com', 'seguro123', '+502 7890-1234'),
    ('3692581470109', 'Ana', 'Ramírez', 3, 'vendedor', '1993-12-29', '7a Avenida 16-3, Zona 9, Quetzaltenango', 'ana.ramirez@ejemplo.com', 'seguro123', '+502 8901-2345'),
    ('1472583690110', 'Carlos', 'Vásquez', 3, 'vendedor', '1999-09-18', '5a Calle 20-19, Zona 5, San Mateo', 'carlos.vasquez@ejemplo.com', 'seguro123', '+502 9012-3456');

-- Registro de empleados de inventarios
INSERT INTO empleados.empleado (cui, nombre, apellido, id_sucursal, puesto_trabajo, fecha_nacimiento, direccion, correo_electronico, telefono, contrasena) 
VALUES
    ('536282087010', 'María', 'González', 1, 'inventarista', '1985-10-23', '1ra Avenida 10-15 Zona 1, San Juan Ostuncalco', 'maria.gonzalez@gmail.com', '+502 4321-0987', 'seguro123'),
    ('389760014703', 'Pedro', 'Rodríguez', 2, 'inventarista', '1995-07-14', '3ra Calle 5-10 Zona 2, Quetzaltenango', 'pedro.rodriguez@gmail.com', '+502 5498-2376', 'seguro123'),
    ('874149658125', 'Ana', 'López', 3, 'inventarista', '1998-02-18', '5ta Avenida 15-20 Zona 10, San Juan Ostuncalco', 'ana.lopez@gmail.com', '+502 6543-0912', 'seguro123');

-- Registro de emplados de la bodega
INSERT INTO empleados.empleado (cui, nombre, apellido, id_bodega, puesto_trabajo, fecha_nacimiento, direccion, correo_electronico, telefono, contrasena)
VALUES 
    ('415783690312', 'Juan', 'Pérez', 1, 'bodeguero', '1990-05-12', '2da Avenida 5-33 Zona 4, Quetzaltenango', 'juan.perez@gmail.com', '+502 4367-0915', 'seguro123'),
    ('325689743215', 'María', 'García', 1, 'bodeguero', '1985-09-22', '10a Calle 13-55 Zona 1, San Juan Ostuncalco', 'maria.garcia@gmail.com', '+502 5298-3671', 'seguro123'),
    ('236598412587', 'Pedro', 'González', 1, 'bodeguero', '1978-12-06', '6ta Avenida 9-30 Zona 3, Quetzaltenango', 'pedro.gonzalez@gmail.com', '+502 4889-7245', 'seguro123'),
    ('489125365874', 'Ana', 'Martínez', 1, 'bodeguero', '1995-02-18', '14 Calle 8-48 Zona 2, Quetzaltenango', 'ana.martinez@gmail.com', '+502 6201-9347', 'seguro123');

-- Registro del administrador
INSERT INTO empleados.empleado (cui, nombre, apellido, puesto_trabajo, fecha_nacimiento, direccion, correo_electronico, telefono, contrasena)
VALUES
    ('758316491753', 'Karla', 'González', 'administrador', '1985-11-27', '15 Avenida 8-50 Zona 2, Quetzaltenango', 'karla.gonzalez@gmail.com', '+502 4111-2288', 'seguro123');


-- Registro de clientes
INSERT INTO ventas.cliente (nit, nombre, apellido, direccion, telefono, fecha_nacimiento)
VALUES
    ('1234567890123', 'María', 'García', '10ma calle 0-85 zona 3, Quetzaltenango', '+502 7766-4433', '1990-02-02'),
    ('2345678901234', 'Juan', 'Pérez', '12va avenida 1-45 zona 2, Zunil', '+502 7922-1122', '1980-01-01'),
    ('3456789012345', 'Pedro', 'Castillo', '5ta calle 6-23 zona 1, Almolonga', '+502 5858-5858', '1985-03-03'),
    ('4567890123456', 'Luisa', 'Hernández', '7ma avenida 3-21 zona 4, San Francisco', '+502 5874-7854', '1995-04-04'),
    ('5678901234567', 'Ana', 'Morales', '2da calle 8-12 zona 6, Cantel', '+502 7766-8877', '1987-05-05'),
    ('6789012345678', 'Carlos', 'Gómez', '4ta avenida 1-45 zona 5, Coatepeque', '+502 5888-4477', '1991-06-06'),
    ('7890123456789', 'Lucía', 'Martínez', '8va calle 7-21 zona 7, Ostuncalco', '+502 5588-2255', '1983-07-07'),
    ('8901234567890', 'Jorge', 'Rodríguez', '6ta avenida 2-50 zona 8, Colomba', '+502 7722-5599', '1997-08-08'),
    ('CF', 'Consumidor', 'Final', '-', '-', NULL);
