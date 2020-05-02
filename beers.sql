--
-- File generated with SQLiteStudio v3.2.1 on Sat May 2 19:57:04 2020
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: beers
CREATE TABLE [beers](
   "Id"           INTEGER  NOT NULL PRIMARY KEY, 
  "name"         VARCHAR(32) NOT NULL,
  "Manufacturer" VARCHAR(43) NOT NULL,
  "Country"      VARCHAR(14) NOT NULL,
  "Abv"          VARCHAR(6) NOT NULL,
  "Type"         VARCHAR(16) NOT NULL
);
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (1, 'KEO', 'KEO', 'Cyprus', '4.50%', 'Pilsner');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (2, 'LEON', 'Photos Photiades Breweries (Carlsberg)', 'Cyprus', '4.50%', 'Malt');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (3, 'True Ale Wheat Ale', 'True Ale Cyprus', 'Cyprus', '5.00%', 'Wheat Ale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (4, 'Prime Pale', 'Prime Microbrewery', 'Cyprus', '4.50%', 'Pale Ale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (5, 'Humor IPA', 'Hop Thirsty Friends', 'Cyprus', '6.50%', 'IPA');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (6, 'Fix Hellas', 'Olympic Brewery', 'Greece', '5.00%', 'Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (7, 'Mythos', 'Mythos Brewery', 'Greece', '5.00%', 'Blonde Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (8, 'Nissos', 'Cyclades Microbrewery', 'Greece', '5.00%', 'Pilsner');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (9, 'Alfa', 'Athenian Brewery', 'Greece', '5.00%', 'Blonde Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (10, 'Vergina', 'Macedonian Thrace Brewery', 'Greece', '5.00%', 'Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (11, 'Pils Hellas', 'Hellenic Brewery of Atalanti S.A.', 'Greece', '5.00%', 'Blonde Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (12, 'Magnus Magister Premium Lager', 'Magnus Magister (Rhodes)', 'Greece', '4.90%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (13, 'Corfu Beer Special Red', 'Corfu Beer', 'Greece', '5.00%', 'Red Ale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (14, 'Peiraiki', 'Piraeus Microbrewery', 'Greece', '5.00%', 'Pale Ale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (15, 'Neda', 'Messinian Microbrewery', 'Greece', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (16, 'Corona Extra', 'Grupo Modelo - Corona', 'Mexico', '4.50%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (17, 'Heineken', 'Heineken Nederland', 'Netherlands', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (18, 'Amstel Beer', 'Heineken Nederland', 'Netherlands', '5.00%', 'Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (19, 'Budweiser', 'Anheuser-Busch InBev', 'United States', '5.00%', 'Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (20, 'Snow Beer', 'China Resources Snow Breweries', 'China', '4.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (21, 'Coors Light', 'Coors Brewing Company', 'United States', '4.20%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (22, 'Skol', 'Ambev (AB InBev)', 'Brazil', '4.70%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (23, 'Tsingtao', 'Tsingtao Brewery', 'China', '4.80%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (24, 'Bud Light', 'Anheuser-Busch InBev', 'United States', '4.20%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (25, 'Harbin', 'Harbin Beer Company', 'China', '4.80%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (26, 'Yanjing Beer', 'Beijing Yanjing Brewery Co.', 'China', '3.60%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (27, 'Weihenstephaner Hefeweissbier', 'Bayerische Staatsbrauerei Weihenstephan', 'Germany', '5.40%', 'Wheat');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (28, 'Berliner Kindl Weisse', 'Berliner Kindl Schultheiss Brauerei', 'Germany', '3.00%', 'Golden wheat');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (29, 'Stella Artois', 'Anheuser-Busch InBev Belgium', 'Belgium', '5.20%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (30, 'Allagash White', 'Allagash Brewing Company', 'United States', '5.20%', 'Witbier');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (31, 'Blue Moon Belgian White', 'Blue Moon Brewing Company', 'Belgium', '5.40%', 'Witbier');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (32, 'Sierra Nevada Pale Ale', 'Sierra Nevada Brewing Company', 'United States', '5.60%', 'Pale Ale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (33, 'Peroni Nastro Azzurro', 'Birra Peroni Industriale', 'Italy', '5.10%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (34, 'Guinness Black Lager', 'St. James''s Gate', 'Ireland', '4.50%', 'Black Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (35, 'Modelo Especial', 'Grupo Modelo - Corona', 'Mexico', '4.50%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (36, 'Samuel Adams Boston Lager', 'Boston Beer Company', 'United States', '5.00%', 'Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (37, 'Gold Label', 'Samlesbury (AB InBev UK)', 'England', '7.50%', 'Witbier');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (38, 'Carta Blanca', 'FEMSA - Cuauht?moc-Moctezuma', 'Mexico', '4.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (39, 'Westmalle Tripel', 'Brouwerij der Trappisten van Westmalle', 'Belgium', '9.50%', 'Tripel');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (40, 'Hoegaarden', 'Brouwerij Hoegaarden (AB InBev)', 'Belgium', '4.90%', 'Witbier');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (41, 'Brooklyn Black Ops', 'Brooklyn Brewery', 'United States', '11.60%', 'Imperial Stout');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (42, 'Duvel', 'Duvel Moortgat', 'Belgium', '8.50%', 'Ale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (43, 'Aguila Imperial', 'Grupo Empresarial Bavaria', 'Colombia', '6.00%', 'Imperial Pilsner');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (44, 'Super Bock', 'Unicer Bebidas', 'Portugal', '5.20%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (45, 'Somersby Apple Cider', 'Carlsberg Brewery', 'Denmark', '4.50%', 'Apple Cider');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (46, 'Tuborg Gron (Green)', 'Carlsberg Brewery', 'Denmark', '4.60%', 'Pilsner');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (47, 'Hite Beer', 'HiteJinro', 'South Korea', '4.30%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (48, 'Efes Dark', 'Anadolu Efes', 'Turkey', '6.10%', 'Dark Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (49, 'Gordon Finest Red', 'John Martin', 'Belgium', '8.40%', 'Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (50, 'Martins Pale Ale', 'John Martin', 'Belgium', '6.00%', 'Ale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (51, 'Timmermans Kriek Lambicus', 'John Martin', 'Belgium', '4.00%', 'Lambic');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (52, 'Ambar Especial', 'La Zaragozana', 'Spain', '5.20%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (53, 'Russian River Pliny the Younger', 'Russian River Brewing Company', 'United States', '11.00%', 'Imperial');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (54, 'Cristal', 'Cerveceria Bucanero S.A.', 'Cuba', '4.90%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (55, 'Jupiler', 'Brasserie Piedboeuf', 'Belgium', '5.20%', 'Pilsner');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (56, 'Cerveza Salva Vida', 'Cerveceria Hondurena', 'Honduras', '4.80%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (57, 'Hahn Super Dry', 'Hahn Brewery', 'Australia', '4.60%', 'Crisp Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (58, 'Castel Beer', 'Les Brasseries du Cameroun', 'Cameroon', '5.20%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (59, 'Carling', 'Obolon', 'Ukraine', '4.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (60, 'Imperial', 'Cerveceria Santa Fe', 'Argentina', '5.50%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (61, 'Victoria Bitter', 'Carlton & United Breweries', 'Australia', '4.90%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (62, 'Kronenbourg 1664', 'Kronenbourg (Carlsberg)', 'France', '5.50%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (63, 'Star Lager (Nigeria)', 'Nigerian Breweries PLC (Heineken)', 'Nigeria', '5.10%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (64, 'Quilmes Cristal', 'Cerveceria Malteria Quilmes SAICAY', 'Argentina', '4.90%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (65, 'Ozujsko Pivo', 'Zagrebacka Pivovara', 'Croatia', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (66, 'Club Premium (Ecuador)', 'Cerveceria Nacional Ecuador', 'Ecuador', '4.40%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (67, 'Club Premium Lager (Ghana)', 'Accra Brewery', 'Ghana', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (68, 'Beck''s Dark', 'Anheuser-Busch InBev', 'United States', '4.80%', 'Dark Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (69, '5 Seeds Crisp Apple Cider', 'Tooheys Brothers', 'Australia', '5.00%', 'Apple Cider');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (70, 'Tooheys New', 'Tooheys Brothers', 'Australia', '4.60%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (71, 'Hahn Ice Beer', 'Hahn Brewers', 'Australia', '4.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (72, 'Castle Lite', 'SAB - South African Breweries', 'South Africa', '4.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (73, 'Waterloo Strong Dark', 'John Martin', 'Belgium', '8.50%', 'Ale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (74, 'Blue Point Toasted Lager', 'Blue Point Brewing', 'United States', '5.50%', 'Amber Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (75, 'Brahma Chopp', 'Ambev', 'Brazil', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (76, 'Paulaner Hefe-Weissbier', 'Paulaner Brauerei', 'Germany', '5.50%', 'Pale Wheat');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (77, 'Harviestoun Old Engine Oil', 'Harviestoun Brewery', 'Scotland', '6.00%', 'Porter');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (78, 'Chang Classic', 'ThaiBev - Thai Beverage', 'Thailand', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (79, 'Dragon Stout', 'Desnoes and Geddes', 'Jamaica', '7.50%', 'Stout');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (80, 'Kostritzer Schwarzbier', 'Kostritzer Schwarzbierbrauerei', 'Germany', '4.80%', 'Black Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (81, 'Grisette Blanche', 'Brasserie St-Feuillien / Friart', 'Belgium', '5.50%', 'Witbier');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (82, 'Cisk Export', 'Simonds Farsons Cisk', 'Malta', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (83, 'Alpine Lager', 'Moosehead Breweries', 'Canada', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (84, 'Hook Norton Old Hooky', 'Hook Norton Brewery', 'England', '4.60%', 'Bitter');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (85, 'Carlsberg Pilsner', 'Carlsberg Brewery', 'Denmark', '4.60%', 'Pilsner');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (86, 'Harpoon Winter Warmer', 'Harpoon Brewery', 'United States', '5.90%', 'Flavored');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (87, 'Lowlander I.P.A.', 'Lowlander Beer Microbrewery', 'Netherlands', '6.00%', 'IPA Flavored');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (88, 'Primator 24 Double', 'Pivovar Nachod', 'Czech Republic', '10.50%', 'Bock');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (89, 'Thomas Creek Class Five IPA', 'Thomas Creek Brewery', 'United States', '5.50%', 'IPA');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (90, 'Kingfisher Premium Lager Beer', 'United Breweries Group', 'India', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (91, 'Beerlao Lager Beer', 'Lao Brewery Co (Carlsberg)', 'Laos', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (92, 'Tiger Beer', 'Singapore Brewery', 'Singapore', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (93, 'Tongerlo Nox', 'Haacht', 'Belgium', '6.50%', 'Dubbel');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (94, 'Krombacher Pils', 'Krombacher Privatbrauerei Kreuztal', 'Germany', '4.80%', 'Pilsner');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (95, 'Saigon Export', 'Sabeco - Saigon Beer Alcohol Beverage Corp.', 'Vietnam', '4.90%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (96, 'Karlovacko Pivo', 'Heineken Hrvatska', 'Croatia', '5.00%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (97, 'Dos Equis XX Ambar (Amber)', 'FEMSA - Cuauhtemoc-Moctezuma (Heineken)', 'Mexico', '4.70%', 'Amber Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (98, 'Spencer Trappist Ale', 'Spencer Brewery', 'United States', '6.50%', 'Ale Pale');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (99, 'Mahou Cinco Estrellas', 'Mahou (Mahou San Miguel)', 'Spain', '5.50%', 'Pale Lager');
INSERT INTO beers (Id, name, Manufacturer, Country, Abv, Type) VALUES (100, 'Harp Lager', 'St. James''s Gate (Diageo)', 'Ireland', '4.30%', 'Pale Lager');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
