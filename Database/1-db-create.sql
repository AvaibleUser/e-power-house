CREATE DATABASE e_power_house
\c e_power_house

CREATE SCHEMA ventas;
CREATE SCHEMA inventario;
CREATE SCHEMA empleado;
CREATE TYPE puesto AS ENUM ('vendedor', 'inventarista', 'bodeguero', 'administrador');

CREATE TABLE ventas.cliente (
  nit CHAR(13),
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  direccion VARCHAR(100),
  telefono VARCHAR(15),
  fecha_nacimiento DATE,
  PRIMARY KEY (nit)
);

CREATE TABLE ventas.producto (
  id SERIAL,
  nombre VARCHAR(50),
  descripcion TEXT,
  precio_unidad NUMERIC(10, 2),
  costo_unidad NUMERIC(10, 2),
  PRIMARY KEY (id)
);

CREATE TABLE ventas.sucursal (
  id SERIAL,
  nombre VARCHAR(50),
  direccion VARCHAR(100),
  telefono VARCHAR(15),
  PRIMARY KEY (id)
);

CREATE TABLE ventas.stock (
  id_sucursal INTEGER,
  id_producto INTEGER,
  cantidad INTEGER,
  PRIMARY KEY (id_sucursal, id_producto),
  FOREIGN KEY (id_sucursal) REFERENCES ventas.sucursal(id),
  FOREIGN KEY (id_producto) REFERENCES ventas.producto(id)
);

CREATE TABLE inventario.bodega (
  id SERIAL,
  nombre VARCHAR(50),
  direccion VARCHAR(100),
  telefono VARCHAR(15),
  PRIMARY KEY (id),
);

CREATE TABLE inventario.inventario (
  id_bodega INTEGER,
  id_producto INTEGER,
  cantidad INTEGER,
  PRIMARY KEY (id_bodega, id_producto),
  FOREIGN KEY (id_bodega) REFERENCES inventario.bodega(id),
  FOREIGN KEY (id_producto) REFERENCES ventas.producto(id)
);

CREATE TABLE empleados.empleado (
  cui CHAR(13),
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  contrasena CHAR(60),
  id_sucursal INTEGER,
  id_bodega INTEGER,
  puesto_trabajo puesto,
  fecha_nacimiento DATE,
  direccion VARCHAR(100),
  correo_electronico VARCHAR(50),
  telefono VARCHAR(15),
  PRIMARY KEY (cui),
  FOREIGN KEY (id_sucursal) REFERENCES ventas.sucursal(id),
  FOREIGN KEY (id_bodega) REFERENCES inventario.bodega(id)
);

CREATE TABLE ventas.venta (
  id SERIAL,
  cui_empleado CHAR(13),
  nit_cliente CHAR(13),
  id_sucursal INTEGER,
  fecha DATE,
  descuento NUMERIC(10, 2),
  PRIMARY KEY (id),
  FOREIGN KEY (cui_empleado) REFERENCES empleados.empleado(cui),
  FOREIGN KEY (id_sucursal) REFERENCES ventas.sucursal(id),
  FOREIGN KEY (nit_cliente) REFERENCES ventas.cliente(nit)
);

CREATE TABLE ventas.detalle_venta (
  id_venta INTEGER,
  id_producto INTEGER,
  cantidad INTEGER,
  precio_unidad NUMERIC(10, 2),
  PRIMARY KEY (id_venta, id_producto),
  FOREIGN KEY (id_venta) REFERENCES ventas.venta(id),
  FOREIGN KEY (id_producto) REFERENCES ventas.producto(id)
);
