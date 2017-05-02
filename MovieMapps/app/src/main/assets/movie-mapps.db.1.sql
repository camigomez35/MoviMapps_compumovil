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
    sala TEXT
);

INSERT INTO cinema (name, latitude, longitude ) VALUES('Cine Colombia Los Molinos', 6.232321400000001, -75.60410279999996);
INSERT INTO cinema (name, latitude, longitude ) VALUES('Procinal Puerta del norte', 6.339409599999999, -75.54321419999997);
INSERT INTO cinema (name, latitude, longitude ) VALUES('Procinal Florida', 6.270901899999999, -75.57674639999999);
INSERT INTO cinema (name, latitude, longitude ) VALUES('Royal Films Bosque Plaza', 6.268674619223301, -75.56475877761841);

INSERT INTO funciones_table (hora, lugar, sala) VALUES('3:30 p.m.', 'Cine Colombia Los Molinos', 'Sala 4');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('4:30 p.m.', 'Procinal Florida', 'Sala 1');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('6:30 p.m.', 'Royal Films Bosque Plaza', 'Sala 2');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('3:00 p.m.', 'Procinal Florida', 'Sala 5');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('10:00 a.m.', 'Procinal Puerta del norte', 'Sala 3');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('3:30 p.m.', 'Cine Colombia Los Molinos', 'Sala 4');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('4:30 p.m.', 'Procinal Florida', 'Sala 1');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('6:30 p.m.', 'Royal Films Bosque Plaza', 'Sala 2');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('3:00 p.m.', 'Procinal Florida', 'Sala 5');
INSERT INTO funciones_table (hora, lugar, sala) VALUES('10:00 a.m.', 'Procinal Puerta del norte', 'Sala 3');