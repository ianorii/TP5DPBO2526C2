CREATE DATABASE `db_people`;

USE `db_people`;

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `thn_lahir` int(11) NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `kebugaran` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

LOCK TABLES `person` WRITE;
INSERT INTO `person` VALUES ('P001','Andi Pratama',1995,'Pemula',6),('P002','Budi Santoso',1990,'Menengah',8),('P003','Rizky Firmansyah',2000,'Pemula',5),('P004','Dedi Kurniawan',1988,'Ahli',9),('P005','Fajar Nugroho',1998,'Menengah',7),('P006','Hendra Gunawan',1992,'Ahli',10),('P007','Yoga Saputra',2002,'Pemula',4),('P008','Ilham Ramadhan',1996,'Menengah',7),('P009','Bagas Mahendra',2001,'Pemula',6),('P010','Eko Wijaya',1985,'Ahli',8);
UNLOCK TABLES;