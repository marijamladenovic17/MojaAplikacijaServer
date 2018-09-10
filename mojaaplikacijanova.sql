/*
SQLyog Community v12.5.1 (64 bit)
MySQL - 10.1.26-MariaDB : Database - mojaaplikacijanovo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mojaaplikacijanovo` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `mojaaplikacijanovo`;

/*Table structure for table `clan` */

DROP TABLE IF EXISTS `clan`;

CREATE TABLE `clan` (
  `clanID` int(11) NOT NULL,
  `ime` varchar(255) DEFAULT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `komisijaID` int(11) DEFAULT NULL,
  PRIMARY KEY (`clanID`),
  KEY `komisijaID` (`komisijaID`),
  CONSTRAINT `clan_ibfk_1` FOREIGN KEY (`komisijaID`) REFERENCES `komisija` (`komisijaID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clan` */

insert  into `clan`(`clanID`,`ime`,`prezime`,`komisijaID`) values 
(1,'Dusan','Savic',2),
(2,'Sladjana','Benkovic',2),
(3,'Veljko','Jeremic',1),
(4,'Mladen','Cudanov',NULL),
(5,'Dusan','Barac',1),
(6,'Dejan','Stojimirovic',NULL),
(7,'Sinisa','Vlajic',NULL);

/*Table structure for table `drzevljanstvo` */

DROP TABLE IF EXISTS `drzevljanstvo`;

CREATE TABLE `drzevljanstvo` (
  `drzevljanstvoID` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`drzevljanstvoID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `drzevljanstvo` */

insert  into `drzevljanstvo`(`drzevljanstvoID`,`naziv`) values 
(1,'srpsko'),
(2,'bosnjacko'),
(3,'crnogorsko'),
(4,'makedonsko'),
(5,'ostalo');

/*Table structure for table `grupazadatka` */

DROP TABLE IF EXISTS `grupazadatka`;

CREATE TABLE `grupazadatka` (
  `brojGrupe` varchar(255) NOT NULL,
  `testID` int(11) DEFAULT NULL,
  PRIMARY KEY (`brojGrupe`),
  KEY `testID` (`testID`),
  CONSTRAINT `grupazadatka_ibfk_1` FOREIGN KEY (`testID`) REFERENCES `test` (`testID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `grupazadatka` */

insert  into `grupazadatka`(`brojGrupe`,`testID`) values 
('111',1),
('444',1),
('222',2);

/*Table structure for table `kandidat` */

DROP TABLE IF EXISTS `kandidat`;

CREATE TABLE `kandidat` (
  `prezime` varchar(255) DEFAULT NULL,
  `sifraPrijave` varchar(255) DEFAULT NULL,
  `jmbg` varchar(255) NOT NULL,
  `imeRoditelja` varchar(255) DEFAULT NULL,
  `ime` varchar(255) DEFAULT NULL,
  `pol` varchar(255) DEFAULT NULL,
  `mobilni` varchar(255) DEFAULT NULL,
  `fiksni` varchar(255) DEFAULT NULL,
  `drzavljanstvoID` int(11) DEFAULT NULL,
  `sifraZanimanjaRoditelja` int(11) DEFAULT NULL,
  `sifraSS` int(11) DEFAULT NULL,
  `nacionalnostID` int(11) DEFAULT NULL,
  `ukupanRezultat` double DEFAULT NULL,
  PRIMARY KEY (`jmbg`),
  KEY `drzevljanstvoID` (`drzavljanstvoID`),
  KEY `sifraZanimanjaRoditelja` (`sifraZanimanjaRoditelja`),
  KEY `sifraSS` (`sifraSS`),
  KEY `nacionalnostID` (`nacionalnostID`),
  CONSTRAINT `kandidat_ibfk_1` FOREIGN KEY (`drzavljanstvoID`) REFERENCES `drzevljanstvo` (`drzevljanstvoID`),
  CONSTRAINT `kandidat_ibfk_2` FOREIGN KEY (`sifraZanimanjaRoditelja`) REFERENCES `zanimanjeroditelja` (`sifraZanimanjaID`),
  CONSTRAINT `kandidat_ibfk_3` FOREIGN KEY (`sifraSS`) REFERENCES `srednjaskola` (`sifraSrednjeSkole`),
  CONSTRAINT `kandidat_ibfk_4` FOREIGN KEY (`nacionalnostID`) REFERENCES `nacionalnost` (`nacionalnostID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `kandidat` */

insert  into `kandidat`(`prezime`,`sifraPrijave`,`jmbg`,`imeRoditelja`,`ime`,`pol`,`mobilni`,`fiksni`,`drzavljanstvoID`,`sifraZanimanjaRoditelja`,`sifraSS`,`nacionalnostID`,`ukupanRezultat`) values 
('Bor','5652','1203444444446','Bor','Bor','Muski','+6520689596245','+6520652159632',1,100,200,300,0),
('vhjvnn','456','1210121222122','kjh','jbn','Zenski','+652656562456','+895221653248',1,100,200,300,0),
('Mikic','222','2011992767032','M','Mika','Zenski','0657878787','021678765',1,100,200,300,65),
('Peric','111','3008994767032','P','Pera','Muski','06111111111','01111111111',1,100,200,300,66),
('Mladenovic','7777','3112215648886','Bora','Marija','Zenski','+659658821354','+6268952626164',1,100,200,300,0),
('joka',NULL,'99999999','joka','joka','Zenski','888888888','888888888',NULL,NULL,NULL,NULL,0);

/*Table structure for table `karton` */

DROP TABLE IF EXISTS `karton`;

CREATE TABLE `karton` (
  `brojUnosa` int(11) DEFAULT NULL,
  `kartonID` int(11) NOT NULL,
  `brojKartona` int(11) DEFAULT NULL,
  `kandidatID` varchar(255) DEFAULT NULL,
  `rezultatTesta` double DEFAULT NULL,
  `brojGrupe` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`kartonID`),
  KEY `brojGrupe` (`brojGrupe`),
  KEY `kandidatID` (`kandidatID`),
  CONSTRAINT `karton_ibfk_1` FOREIGN KEY (`brojGrupe`) REFERENCES `grupazadatka` (`brojGrupe`),
  CONSTRAINT `karton_ibfk_2` FOREIGN KEY (`kandidatID`) REFERENCES `kandidat` (`jmbg`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `karton` */

insert  into `karton`(`brojUnosa`,`kartonID`,`brojKartona`,`kandidatID`,`rezultatTesta`,`brojGrupe`) values 
(1,1,555,'3008994767032',66,'111'),
(2,2,555,NULL,NULL,'111'),
(1,3,666,'2011992767032',45,'111'),
(2,4,666,NULL,NULL,'111'),
(1,5,777,'2011992767032',85,'222'),
(2,6,777,NULL,NULL,'222'),
(1,7,446,'3112215648886',0,'111'),
(2,8,446,NULL,NULL,'111');

/*Table structure for table `komisija` */

DROP TABLE IF EXISTS `komisija`;

CREATE TABLE `komisija` (
  `komisijaID` int(11) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`komisijaID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `komisija` */

insert  into `komisija`(`komisijaID`,`user`,`password`) values 
(1,'kom1','k1'),
(2,'kom2','k2');

/*Table structure for table `nacionalnost` */

DROP TABLE IF EXISTS `nacionalnost`;

CREATE TABLE `nacionalnost` (
  `nacionalnostID` int(11) NOT NULL,
  `naziv` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nacionalnostID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `nacionalnost` */

insert  into `nacionalnost`(`nacionalnostID`,`naziv`) values 
(300,'Srbin'),
(301,'Bosnjak');

/*Table structure for table `rang` */

DROP TABLE IF EXISTS `rang`;

CREATE TABLE `rang` (
  `brojKartona` int(11) DEFAULT NULL,
  `rang` int(3) DEFAULT NULL,
  `brojPoena` float DEFAULT NULL,
  `redniBroj` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rang` */

/*Table structure for table `rang-lista` */

DROP TABLE IF EXISTS `rang-lista`;

CREATE TABLE `rang-lista` (
  `sifraRL` varchar(25) NOT NULL,
  `godina` int(4) DEFAULT NULL,
  PRIMARY KEY (`sifraRL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rang-lista` */

/*Table structure for table `rang_lista` */

DROP TABLE IF EXISTS `rang_lista`;

CREATE TABLE `rang_lista` (
  `sifraRL` varchar(25) NOT NULL,
  `godina` int(4) DEFAULT NULL,
  PRIMARY KEY (`sifraRL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rang_lista` */

insert  into `rang_lista`(`sifraRL`,`godina`) values 
('sifra',2018);

/*Table structure for table `resenje` */

DROP TABLE IF EXISTS `resenje`;

CREATE TABLE `resenje` (
  `resenjeID` int(11) NOT NULL AUTO_INCREMENT,
  `rbZadatka` int(11) DEFAULT NULL,
  `odgovor` varchar(1) DEFAULT NULL,
  `brojGrupe` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resenjeID`),
  KEY `brojGrupe` (`brojGrupe`),
  CONSTRAINT `resenje_ibfk_1` FOREIGN KEY (`brojGrupe`) REFERENCES `grupazadatka` (`brojGrupe`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;

/*Data for the table `resenje` */

insert  into `resenje`(`resenjeID`,`rbZadatka`,`odgovor`,`brojGrupe`) values 
(71,1,'A','111'),
(72,2,'A','111'),
(73,3,'A','111'),
(74,4,'A','111'),
(75,5,'A','111'),
(76,6,'A','111'),
(77,7,'A','111'),
(78,8,'A','111'),
(79,9,'A','111'),
(80,10,'A','111'),
(81,11,'A','111'),
(82,12,'A','111'),
(83,13,'A','111'),
(84,14,'A','111'),
(85,15,'A','111'),
(86,16,'A','111'),
(87,17,'A','111'),
(88,18,'A','111'),
(89,19,'A','111'),
(90,20,'A','111'),
(91,1,'B','222'),
(92,2,'B','222'),
(93,3,'B','222'),
(94,4,'B','222'),
(95,5,'B','222'),
(96,6,'B','222'),
(97,7,'B','222'),
(98,8,'B','222'),
(99,9,'B','222'),
(100,10,'B','222'),
(101,11,'B','222'),
(102,12,'B','222'),
(103,13,'B','222'),
(104,14,'B','222'),
(105,15,'B','222'),
(106,16,'B','222'),
(107,17,'B','222'),
(108,18,'B','222'),
(109,19,'B','222'),
(110,20,'B','222'),
(111,21,'B','222'),
(112,22,'B','222'),
(113,23,'B','222'),
(114,24,'B','222'),
(115,25,'B','222'),
(116,26,'B','222'),
(117,27,'B','222'),
(118,28,'B','222'),
(119,29,'B','222'),
(120,30,'B','222'),
(121,1,'A','444'),
(122,2,'A','444'),
(123,3,'A','444'),
(124,4,'A','444'),
(125,5,'A','444'),
(126,6,'A','444'),
(127,7,'A','444'),
(128,8,'A','444'),
(129,9,'A','444'),
(130,10,'A','444'),
(131,11,'B','444'),
(132,12,'C','444'),
(133,13,'D','444'),
(134,14,'E','444'),
(135,15,'A','444'),
(136,16,'B','444'),
(137,17,'B','444'),
(138,18,'B','444'),
(139,19,'B','444'),
(140,20,'B','444');

/*Table structure for table `sluzbenik` */

DROP TABLE IF EXISTS `sluzbenik`;

CREATE TABLE `sluzbenik` (
  `sluzbenikID` int(11) NOT NULL AUTO_INCREMENT,
  `imePrezime` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`sluzbenikID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `sluzbenik` */

insert  into `sluzbenik`(`sluzbenikID`,`imePrezime`,`username`,`password`) values 
(1,'Marija Maka','maki','maki'),
(2,'Jovana Joka','joki','joki');

/*Table structure for table `spajanje` */

DROP TABLE IF EXISTS `spajanje`;

CREATE TABLE `spajanje` (
  `kartonskiBroj1` int(11) NOT NULL,
  `kartonskiBroj2` int(11) NOT NULL,
  `podudarnost` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`kartonskiBroj1`,`kartonskiBroj2`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `spajanje` */

/*Table structure for table `srednjaskola` */

DROP TABLE IF EXISTS `srednjaskola`;

CREATE TABLE `srednjaskola` (
  `sifraSrednjeSkole` int(11) NOT NULL,
  `naziv` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sifraSrednjeSkole`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `srednjaskola` */

insert  into `srednjaskola`(`sifraSrednjeSkole`,`naziv`) values 
(200,'Gimnazija Bora Stankovic, Vranje'),
(201,'Prva kragujevacka gimnazija'),
(202,'Ekonomska skola, Vranje');

/*Table structure for table `stavka_rang-liste` */

DROP TABLE IF EXISTS `stavka_rang-liste`;

CREATE TABLE `stavka_rang-liste` (
  `sifraRL` varchar(25) DEFAULT NULL,
  `redniBroj` int(3) DEFAULT NULL,
  `jmbg` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stavka_rang-liste` */

/*Table structure for table `stavka_rang_liste` */

DROP TABLE IF EXISTS `stavka_rang_liste`;

CREATE TABLE `stavka_rang_liste` (
  `sifraRL` varchar(25) DEFAULT NULL,
  `redniBroj` int(3) DEFAULT NULL,
  `jmbg` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stavka_rang_liste` */

insert  into `stavka_rang_liste`(`sifraRL`,`redniBroj`,`jmbg`) values 
('sifra',1,'3008994767032'),
('sifra',2,'2011992767032'),
('sifra',3,'1210121222122'),
('sifra',4,'3112215648886');

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `testID` int(11) NOT NULL,
  `nazivTesta` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`testID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `test` */

insert  into `test`(`testID`,`nazivTesta`) values 
(1,'Matematika'),
(2,'Test opste informisanosti');

/*Table structure for table `zadatak` */

DROP TABLE IF EXISTS `zadatak`;

CREATE TABLE `zadatak` (
  `kartonID` int(11) NOT NULL,
  `rbZadatka` int(11) DEFAULT NULL,
  `odgovor` char(1) DEFAULT NULL,
  `zadatakID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`kartonID`,`zadatakID`),
  KEY `zadatakID` (`zadatakID`)
) ENGINE=InnoDB AUTO_INCREMENT=321 DEFAULT CHARSET=latin1;

/*Data for the table `zadatak` */

insert  into `zadatak`(`kartonID`,`rbZadatka`,`odgovor`,`zadatakID`) values 
(1,1,'A',141),
(1,2,'A',142),
(1,3,'A',143),
(1,4,'A',144),
(1,5,'A',145),
(1,6,'A',146),
(1,7,'A',147),
(1,8,'A',148),
(1,9,'A',149),
(1,10,'A',150),
(1,11,'A',151),
(1,12,'A',152),
(1,13,'A',153),
(1,14,'A',154),
(1,15,'N',155),
(1,16,'B',156),
(1,17,'C',157),
(1,18,'D',158),
(1,19,'A',159),
(1,20,'N',160),
(2,1,'A',161),
(2,2,'A',162),
(2,3,'A',163),
(2,4,'A',164),
(2,5,'A',165),
(2,6,'A',166),
(2,7,'A',167),
(2,8,'A',168),
(2,9,'A',169),
(2,10,'A',170),
(2,11,'A',171),
(2,12,'A',172),
(2,13,'A',173),
(2,14,'A',174),
(2,15,'N',175),
(2,16,'B',176),
(2,17,'C',177),
(2,18,'D',178),
(2,19,'A',179),
(2,20,'N',180),
(3,1,'N',181),
(3,2,'N',182),
(3,3,'N',183),
(3,4,'A',184),
(3,5,'A',185),
(3,6,'A',186),
(3,7,'A',187),
(3,8,'A',188),
(3,9,'A',189),
(3,10,'A',190),
(3,11,'A',191),
(3,12,'A',192),
(3,13,'A',193),
(3,14,'A',194),
(3,15,'A',195),
(3,16,'B',196),
(3,17,'B',197),
(3,18,'B',198),
(3,19,'B',199),
(3,20,'B',200),
(4,1,'N',201),
(4,2,'N',202),
(4,3,'N',203),
(4,4,'A',204),
(4,5,'A',205),
(4,6,'A',206),
(4,7,'A',207),
(4,8,'A',208),
(4,9,'A',209),
(4,10,'A',210),
(4,11,'A',211),
(4,12,'A',212),
(4,13,'A',213),
(4,14,'A',214),
(4,15,'A',215),
(4,16,'B',216),
(4,17,'B',217),
(4,18,'B',218),
(4,19,'B',219),
(4,20,'B',220),
(5,1,'D',221),
(5,2,'D',222),
(5,3,'D',223),
(5,4,'D',224),
(5,5,'D',225),
(5,6,'B',226),
(5,7,'B',227),
(5,8,'B',228),
(5,9,'B',229),
(5,10,'B',230),
(5,11,'B',231),
(5,12,'B',232),
(5,13,'B',233),
(5,14,'B',234),
(5,15,'B',235),
(5,16,'N',236),
(5,17,'N',237),
(5,18,'N',238),
(5,19,'N',239),
(5,20,'N',240),
(5,21,'B',241),
(5,22,'B',242),
(5,23,'B',243),
(5,24,'B',244),
(5,25,'B',245),
(5,26,'B',246),
(5,27,'B',247),
(5,28,'B',248),
(5,29,'B',249),
(5,30,'B',250),
(6,1,'D',251),
(6,2,'D',252),
(6,3,'D',253),
(6,4,'D',254),
(6,5,'D',255),
(6,6,'B',256),
(6,7,'B',257),
(6,8,'B',258),
(6,9,'B',259),
(6,10,'B',260),
(6,11,'B',261),
(6,12,'B',262),
(6,13,'B',263),
(6,14,'B',264),
(6,15,'B',265),
(6,16,'N',266),
(6,17,'N',267),
(6,18,'N',268),
(6,19,'N',269),
(6,20,'N',270),
(6,21,'B',271),
(6,22,'B',272),
(6,23,'B',273),
(6,24,'B',274),
(6,25,'B',275),
(6,26,'B',276),
(6,27,'B',277),
(6,28,'B',278),
(6,29,'B',279),
(6,30,'B',280),
(7,1,'B',281),
(7,2,'B',282),
(7,3,'B',283),
(7,4,'B',284),
(7,5,'B',285),
(7,6,'B',286),
(7,7,'B',287),
(7,8,'a',288),
(7,9,'B',289),
(7,10,'B',290),
(7,11,'B',291),
(7,12,'B',292),
(7,13,'B',293),
(7,14,'B',294),
(7,15,'B',295),
(7,16,'B',296),
(7,17,'B',297),
(7,18,'B',298),
(7,19,'B',299),
(7,20,'B',300),
(8,1,'B',301),
(8,2,'B',302),
(8,3,'B',303),
(8,4,'B',304),
(8,5,'B',305),
(8,6,'B',306),
(8,7,'B',307),
(8,8,'B',308),
(8,9,'B',309),
(8,10,'B',310),
(8,11,'B',311),
(8,12,'B',312),
(8,13,'B',313),
(8,14,'B',314),
(8,15,'B',315),
(8,16,'B',316),
(8,17,'B',317),
(8,18,'B',318),
(8,19,'B',319),
(8,20,'B',320);

/*Table structure for table `zanimanjeroditelja` */

DROP TABLE IF EXISTS `zanimanjeroditelja`;

CREATE TABLE `zanimanjeroditelja` (
  `sifraZanimanjaID` int(11) NOT NULL,
  `naziv` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sifraZanimanjaID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `zanimanjeroditelja` */

insert  into `zanimanjeroditelja`(`sifraZanimanjaID`,`naziv`) values 
(100,'penzioner'),
(101,'prosvetni radnik'),
(102,'zdravstveni radnik');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
