-- Crea la información de prueba para la base de datos

-- Borrar datos de prueba
DELETE FROM ARTICULO_DE_ROPA_ENTITY;
DELETE FROM PACK_DE_SERVICIOS_ENTITY_SERVICIOS;
DELETE FROM PACK_DE_SERVICIOS_ENTITY;
DELETE FROM TRABAJADOR_ENTITY_SERVICIOS;
DELETE FROM SERVICIO_ENTITY;
DELETE FROM SERVICIO_EXTRA_ENTITY;
DELETE FROM TRABAJADOR_ENTITY_SEDES;
DELETE FROM TRABAJADOR_ENTITY;
DELETE FROM SEDE_ENTITY;
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

-- Create pack 1
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (1, 20, 'Ritual de relajación profunda', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 1);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (1, 1);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (1, 2);

-- Create pack 2
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (2, 15, 'Pack de belleza completa', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 2);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (2, 3);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (2, 4);

-- Create pack 3
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (3, 10, 'Pack de cuidado personal', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 3);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (3, 5);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (3, 6);

-- Create pack 4
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (4, 25, 'Pack de relajación y bienestar', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 4);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (4, 7);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (4, 8);

-- Create pack 5
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (5, 30, 'Pack de belleza y estilo', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 5);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (5, 9);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (5, 10);

-- Create pack 6
INSERT INTO PACK_DE_SERVICIOS_ENTITY (id, descuento, nombre, imagen, sede_id)
VALUES (6, 12, 'Pack de tratamientos especiales', 'https://amomentspeace.com/wp-content/uploads/2021/02/AdobeStock_149758419-scaled.jpeg', 6);

-- Add services to the pack
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (6, 11);
INSERT INTO PACK_DE_SERVICIOS_ENTITY_SERVICIOS (packs_de_servicios_id, servicios_id)
VALUES (6, 12);

-- Articulos
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (1, 'Crop Top The Spa', 'Camiseta corta', 15000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 100, 6, 'azul', 1);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (2, 'Pantalón Deportivo', 'Pantalón cómodo para entrenamiento', 25000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 50, 8, 'negro', 1);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (3, 'Vestido Elegante', 'Vestido para ocasiones especiales', 35000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 20, 10, 'rojo', 2);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (4, 'Camisa Casual', 'Camisa cómoda para uso diario', 20000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 30, 7, 'blanco', 2);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (5, 'Falda Plisada', 'Falda elegante y versátil', 18000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 40, 9, 'negro', 3);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (6, 'Short Deportivo', 'Short cómodo para actividades físicas', 12000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 60, 5, 'gris', 3);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (7, 'Camiseta Estampada', 'Camiseta con diseño exclusivo', 25000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 80, 7, 'azul', 4);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (8, 'Pantalón Casual', 'Pantalón cómodo para uso diario', 30000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 25, 6, 'negro', 4);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (9, 'Blusa Elegante', 'Blusa con estilo sofisticado', 28000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 35, 8, 'blanco', 5);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (10, 'Short Vaquero', 'Short de tela vaquera resistente', 22000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 15, 7, 'azul', 5);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (11, 'Camisa Formal', 'Camisa ideal para eventos elegantes', 40000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 50, 9, 'blanco', 6);
INSERT INTO ARTICULO_DE_ROPA_ENTITY (id, nombre, descripcion, precio, imagen, num_disponible, talla, color, sede_id)
VALUES (12, 'Pantalón de Vestir', 'Pantalón formal para ocasiones especiales', 35000.0, 'https://cdn.shopify.com/s/files/1/0156/6146/products/TrainingFractionCropTopToffeeBrownB2A5Y-NBH6.1054.142.jpg?v=1668618724g', 40, 8, 'negro', 6);

-- Servicios Extra
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (1, 'Sandwich Gourmet', 'Para disfrutar mientras espera ser atendido', 5000.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 1);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (2, 'Bebida Refrescante', 'Una bebida refrescante para acompañar su visita', 3000.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 1);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (3, 'Café Especial', 'Un café especial hecho por nuestros baristas', 4000.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 2);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (4, 'Postre Delicioso', 'Un postre delicioso para culminar su experiencia', 6000.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 2);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (5, 'Aperitivo Exclusivo', 'Un aperitivo exclusivo para abrir su apetito', 4500.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 3);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (6, 'Refresco Natural', 'Un refresco natural hecho con ingredientes frescos', 3500.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 3);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (7, 'Snack Saludable', 'Un snack saludable para picar entre servicios', 2500.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 4);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (8, 'Té Caliente', 'Un té caliente para relajarse durante su visita', 3500.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 4);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (9, 'Batido Energizante', 'Un batido energizante para revitalizarse', 4500.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 5);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (10, 'Galleta Casera', 'Una galleta casera para endulzar su día', 2000.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 5);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (11, 'Zumo Natural', 'Un zumo natural hecho con frutas frescas', 4000.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 6);
INSERT INTO SERVICIO_EXTRA_ENTITY (id, nombre, descripcion, precio, imagen, disponible, sede_id)
VALUES (12, 'Helado Artesanal', 'Un helado artesanal para refrescar su paladar', 5000.0, 'https://d320djwtwnl5uo.cloudfront.net/recetas/cover/s-ndw_AfsS859PRoHcJpKwd6r4Me0FUBDtCg.png', true, 6);

-- Trabajadores

-- Trabajador 1 
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (1, 'https://www.leisureopportunities.co.uk/images/464391_26750.jpg', 'Sophia Anderson', 10, true);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (1, 1);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (1, 1), (1, 2);

-- Trabajador 2
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (2, 'https://www.leisureopportunities.co.uk/images/464391_26750.jpg', 'John Smith', 8, false);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (2, 2), (2, 3);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (2, 3), (2, 4), (2, 5);

-- Trabajador 3
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (3, 'https://www.leisureopportunities.co.uk/images/464391_26750.jpg', 'Emily Johnson', 9, true);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (3, 4);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (3, 6), (3, 7);

-- Trabajador 4
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (4, 'https://www.leisureopportunities.co.uk/images/464391_26750.jpg', 'Michael Williams', 7, false);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (4, 5), (4, 6);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (4, 8), (4, 9), (4, 10);

-- Trabajador 5
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (5, 'https://www.leisureopportunities.co.uk/images/464391_26750.jpg', 'Olivia Davis', 9, true);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (5, 1), (5, 2);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (5, 11), (5, 12);

-- Trabajador 6
INSERT INTO TRABAJADOR_ENTITY (id, foto, nombre, calificacion, en_hall_of_fame)
VALUES (6, 'https://www.leisureopportunities.co.uk/images/464391_26750.jpg', 'James Brown', 8, false);

-- Trabajador-Sede association
INSERT INTO TRABAJADOR_ENTITY_SEDES (TRABAJADORES_ID, SEDES_ID)
VALUES (6, 3);

-- Trabajador-Servicios association
INSERT INTO TRABAJADOR_ENTITY_SERVICIOS (TRABAJADORES_ID, SERVICIOS_ID)
VALUES (6, 1), (6, 2), (6, 3), (6, 4);
