/**
 * Author:  Thomas Jäger
 * Created: 17.04.2018
 * Last Change: 12.05.2018
 */

-- Löscht das Datenbankschema(Database), wenn es existiert
DROP SCHEMA IF EXISTS swt2bib;
-- Erstellt das Datenbankschema(Database), wenn es nicht existiert
CREATE SCHEMA IF NOT EXISTS swt2bib;
-- Nutzt das erstellte Datenbankchema
USE swt2bib;
-- Löscht Tabellen(Table), wenn diese existiert, auch wenn diese zusammenhängen
DROP TABLE IF EXISTS history;
DROP TABLE IF EXISTS Ausleihe;
DROP TABLE IF EXISTS Medien;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS KategorieMedien;
DROP TABLE IF EXISTS Sprache;

-- Erstelle Tabellen(Table)
-- Tebellen ohne Fremdschlüssel

CREATE TABLE Genre(
g_ID            INTEGER AUTO_INCREMENT,
g_Name          VARCHAR(150) UNIQUE,
CONSTRAINT g_gID_PK PRIMARY KEY (g_ID)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE KategorieMedien(
km_ID           INTEGER AUTO_INCREMENT,
km_name         VARCHAR(150) UNIQUE,
km_beschreibung VARCHAR(500),
CONSTRAINT km_kmID_PK PRIMARY KEY (km_ID)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

/*CREATE TABLE Sprache(
s_ID        INTEGER AUTO_INCREMENT,
s_Name      VARCHAR(20),
CONSTRAINT s_sID_PK PRIMARY KEY(s_ID)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;*/

-- Fremdschlüssel Tabellen
CREATE TABLE User(
u_ID            INTEGER AUTO_INCREMENT,
u_Vorname       VARCHAR(150),
u_Nachname      VARCHAR(150),
u_login         VARCHAR(8) UNIQUE,
u_passwd        VARCHAR(192),
u_Mitarbeiter   BOOLEAN DEFAULT false,
u_Strasse       VARCHAR(50),
u_Hausnummer    VARCHAR(50),
u_PLZ           INTEGER,
u_Ort           VARCHAR(25),
u_Anrede        VARCHAR(4) DEFAULT 'N.N.',
--s_ID            INTEGER,
--CONSTRAINT u_AnredeCK CHECK(u_Anrede IN ('Herr', 'Frau')),
CONSTRAINT u_uID_PK PRIMARY KEY (u_ID)--,
--CONSTRAINT u_sID_FK FOREIGN KEY (s_ID) REFERENCES Sprache(s_ID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE Medien(
m_ID BIGINT AUTO_INCREMENT,
m_Titel VARCHAR(150),
m_Author VARCHAR(150),
m_ISBN VARCHAR(24) UNIQUE,
m_Barcode BIGINT UNIQUE,
m_ausgeliehen   BOOLEAN DEFAULT FALSE,
m_Vorgemerkt    BOOLEAN DEFAULT FALSE,
m_Anzahl        INTEGER,
m_beschreibung  VARCHAR(500),
km_ID INTEGER,
g_ID INTEGER,
CONSTRAINT m_mID_PK PRIMARY KEY (m_ID),
CONSTRAINT m_kmID_FK FOREIGN KEY (km_ID) REFERENCES KategorieMedien(km_ID),
CONSTRAINT m_gID_FK FOREIGN KEY (g_ID) REFERENCES Genre(g_ID)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE Ausleihe(
a_ID BIGINT AUTO_INCREMENT,
a_DATE DATE,
u_ID INTEGER,
m_id BIGINT,
km_id   INTEGER,
CONSTRAINT a_aID_PK PRIMARY KEY (a_ID),
CONSTRAINT a_uID_FK FOREIGN KEY (u_ID) REFERENCES User(u_ID),
CONSTRAINT a_mID_FK FOREIGN KEY (m_ID) REFERENCES Medien(m_ID),
CONSTRAINT a_kmID_FK FOREIGN KEY (km_ID) REFERENCES KategorieMedien(km_ID)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE History(
h_ID BIGINT AUTO_INCREMENT,
u_ID INTEGER,
km_ID INTEGER,
m_ID BIGINT,
CONSTRAINT h_hID_PK PRIMARY KEY (h_ID),
CONSTRAINT h_uID_FK FOREIGN KEY (u_ID) REFERENCES User(u_ID),
CONSTRAINT h_kmID_FK FOREIGN KEY (km_ID) REFERENCES KategorieMedien(km_ID),
CONSTRAINT h_mID_FK FOREIGN KEY(m_ID) REFERENCES Medien(m_ID)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

---- Einfügen(Insert)
-- Password: !Administrator@swt2bib
-- Passwordalgorithmus: SHA-512
--DEfault Inserts
INSERT INTO User(u_Vorname, u_Nachname, u_Login, u_passwd, u_mitarbeiter,u_Strasse, u_Hausnummer, u_PLZ, u_Ort)
VALUES ('Admin', 'Min', 'admin001', '18b1dc1d41a312a1fd17414211d1c11af1641f61a016e13e18d1991fb15711f1d01d51151421501791c012f1311e719f10414a1e61451bc1471401b61971ba1f11311be1f41771c417a11f1651d617d1e916019a11c1ef1bf19b1dc123100125', true, 'TestStr.', 1, 12345, 'Testort');
-- Testinserts
INSERT INTO User(u_Vorname, u_Nachname, u_Login, u_passwd, u_mitarbeiter, u_Strasse, u_Hausnummer, u_PLZ, u_Ort)
VALUES ('vor', 'nach', 'user', 'pw', true,'Testweg', 12, 22554, 'Testdorf');
INSERT INTO Genre(g_name) VALUES ('Test');
INSERT INTO Genre(g_Name) VALUES ('Test1');
INSERT INTO KategorieMedien(km_name, km_beschreibung) VALUES ('Test1', 'Ein einfacher Test');
INSERT INTO KategorieMedien(km_name, km_beschreibung) VALUES ('Test2', 'Ein einfacher Test1');
INSERT INTO Medien(m_Titel, m_Author, m_ISBN, m_Barcode, m_Anzahl, m_beschreibung, km_id, g_id) 
VALUES('Datenbanken-Implementierungste', 'Andreas Heuer', '978-3826691560', 9783826691560, 1, 'Dieses Buch behandelt Konzepte und Techniken der Implementierung von Datenbanksystemen, die heutzutage die Kernkomponente von Informationssystemen darstellen.', 1, 1);
INSERT INTO Medien(m_Titel, m_Author, m_ISBN, m_Barcode, m_Anzahl, m_beschreibung, km_id, g_id) 
VALUES('BLub Buch', 'Test Author', '978-3826691561', 9783826691561, 2,'blub', 2, 2);
INSERT INTO Ausleihe(a_Date, u_ID, m_ID, km_ID) VALUES('2018-05-13',1,1,1);
INSERT INTO Ausleihe(a_Date, u_ID, m_ID, km_ID) VALUES('2018-05-12',2,2,2);
INSERT INTO History(u_ID, km_ID, m_ID) VALUES(1, 1, 1);
INSERT INTO History(u_ID, km_ID, m_ID) VALUES(2, 2, 2);
