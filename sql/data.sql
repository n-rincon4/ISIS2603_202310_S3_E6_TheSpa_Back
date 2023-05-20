-- Crea la información de prueba para la base de datos

-- Borrar datos de prueba
DELETE FROM ARTICULO_DE_ROPA_ENTITY;
DELETE FROM PACK_DE_SERVICIOS_ENTITY;
DELETE FROM PACK_DE_SERVICIOS_ENTITY_SERVICIOS;
DELETE FROM SEDE_ENTITY;
DELETE FROM SERVICIO_ENTITY;
DELETE FROM SERVICIO_EXTRA_ENTITY;
DELETE FROM TRABAJADOR_ENTITY;
DELETE FROM TRABAJADOR_ENTITY_SEDES;
DELETE FROM TRABAJADOR_ENTITY_SERVICIOS;
DELETE FROM UBICACION_ENTITY;

-- Insertar datos de prueba

-- Ubicaciones
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (1, 4.603014, 74.065227, 'Bogotá', 'Cl. 20 #19a-71 a 19a-1, Bogotá');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (2, 4.712345, 74.987654, 'Medellín', 'Av. Principal #123, Medellín');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (3, 4.520202, 74.033393, 'Cali', 'Cra. 50 #10-20, Cali');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (4, 4.813459, 74.115678, 'Barranquilla', 'Cl. 80 #5-10, Barranquilla');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (5, 4.634567, 74.789012, 'Cartagena', 'Cra. 2 #15-30, Cartagena');
INSERT INTO UBICACION_ENTITY (id, latitud, longitud, ciudad, direccion)
VALUES (6, 4.432156, 74.222333, 'Santa Marta', 'Av. Playa #8-12, Santa Marta');

-- Sedes
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (1, 'Spa del Jardín', 'https://www.visit.alsace/wp-content/uploads/lei/pictures/215002309-nature-spa-la-cheneaudiere-11-1600x900.jpg', 1);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (2, 'Spa de la Montaña', 'https://www.visit.alsace/wp-content/uploads/lei/pictures/215002309-nature-spa-la-cheneaudiere-11-1600x900.jpg', 2);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (3, 'Spa del Mar', 'https://www.visit.alsace/wp-content/uploads/lei/pictures/215002309-nature-spa-la-cheneaudiere-11-1600x900.jpg', 3);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (4, 'Spa de la Ciudad', 'https://www.visit.alsace/wp-content/uploads/lei/pictures/215002309-nature-spa-la-cheneaudiere-11-1600x900.jpg', 4);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (5, 'Spa del Lago', 'https://www.visit.alsace/wp-content/uploads/lei/pictures/215002309-nature-spa-la-cheneaudiere-11-1600x900.jpg', 5);
INSERT INTO SEDE_ENTITY (id, nombre, imagen, ubicacion_id)
VALUES (6, 'Spa del Bosque', 'https://www.visit.alsace/wp-content/uploads/lei/pictures/215002309-nature-spa-la-cheneaudiere-11-1600x900.jpg', 6);

-- Servicios
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (1, '3', 'Ser mayor de 16 años', true, 8, 'masaje', 'masaje de relajacion', '30000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 1);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (2, '4', 'Ser mayor de 18 años', true, 9, 'facial', 'tratamiento facial', '50000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 1);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (3, '2', 'Ser mayor de 18 años', true, 10, 'manicure', 'manicure y pedicure', '25000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 2);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (4, '1', 'No aplican restricciones', true, 11, 'depilacion', 'depilacion de cejas', '15000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 2);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (5, '2', 'No aplican restricciones', true, 12, 'corte de pelo', 'corte de pelo unisex', '20000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 3);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (6, '1', 'Ser mayor de 16 años', true, 13, 'tinte', 'aplicación de tinte', '35000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 3);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (7, '3', 'Ser mayor de 18 años', true, 14, 'pedicure', 'tratamiento de pedicure', '30000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 4);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (8, '2', 'No aplican restricciones', true, 15, 'maquillaje', 'maquillaje profesional', '40000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 4);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (9, '1', 'Ser mayor de 16 años', true, 16, 'peinado', 'peinado de eventos', '25000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 5);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (10, '2', 'Ser mayor de 18 años', true, 17, 'uñas acrílicas', 'aplicación de uñas acrílicas', '45000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 5);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (11, '4', 'No aplican restricciones', true, 18, 'masaje deportivo', 'masaje para deportistas', '35000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 6);
INSERT INTO SERVICIO_ENTITY (id, duracion, restricciones, disponible, hora_inicio, nombre, descripcion, precio, imagen, sede_id)
VALUES (12, '2', 'Ser mayor de 16 años', true, 19, 'tratamiento capilar', 'tratamiento para el cabello', '50000', 'https://www.genesiscareer.edu/wp-content/uploads/2019/06/swedishmassage.jpg', 6);

