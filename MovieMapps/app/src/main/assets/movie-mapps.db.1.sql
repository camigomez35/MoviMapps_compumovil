CREATE TABLE movie(
    id INTEGER PRIMARY KEY,
    title TEXT,
    overview TEXT,
    releasedate TEXT,
    poster TEXT
);

CREATE TABLE user(
    id BIGINT PRIMARY KEY,
    name TEXT,
    photo TEXT,
    email TEXT
);

CREATE TABLE cinema(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    latitude FLOAT,
    longitude FLOAT
);

CREATE TABLE funciones_table(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    hora TEXT,
    lugar TEXT,
    sala TEXT,
    precio TEXT
);

INSERT INTO cinema (name, latitude, longitude ) VALUES('Cine Colombia Los Molinos', 6.232321400000001, -75.60410279999996);
INSERT INTO cinema (name, latitude, longitude ) VALUES('Procinal Puerta del norte', 6.339409599999999, -75.54321419999997);
INSERT INTO cinema (name, latitude, longitude ) VALUES('Procinal Florida', 6.270901899999999, -75.57674639999999);
INSERT INTO cinema (name, latitude, longitude ) VALUES('Royal Films Bosque Plaza', 6.268674619223301, -75.56475877761841);
INSERT INTO cinema (name, latitude, longitude ) VALUES('Procinal Aventura', 6.264183999999999, -75.56703700000003);

INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('3:30 p.m.', 'Cine Colombia Los Molinos', 'Sala 4', '$5.000');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('4:30 p.m.', 'Procinal Florida', 'Sala 1', '$7.000');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('6:30 p.m.', 'Royal Films Bosque Plaza', 'Sala 2', '$12.000');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('3:00 p.m.', 'Procinal Florida', 'Sala 5', '$6.500');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('10:00 a.m.', 'Procinal Puerta del norte', 'Sala 3', '$2.800');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('3:30 p.m.', 'Cine Colombia Los Molinos', 'Sala 4', '$15.000');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('4:30 p.m.', 'Procinal Florida', 'Sala 1', '$12.000');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('6:30 p.m.', 'Royal Films Bosque Plaza', 'Sala 2', '$4.500');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('3:00 p.m.', 'Procinal Florida', 'Sala 5', '$5.000');
INSERT INTO funciones_table (hora, lugar, sala, precio) VALUES('10:00 a.m.', 'Procinal Puerta del norte', 'Sala 3', '$5.000');