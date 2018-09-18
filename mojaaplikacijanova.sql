/*
SQLyog Community v12.4.1 (64 bit)
MySQL - 10.1.30-MariaDB : Database - mojaaplikacijanovo
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
(6,'Dejan','Stojimirovic',3),
(7,'Sinisa','Vlajic',3);

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
('4545',1),
('876',1),
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
  `email` varchar(255) DEFAULT NULL,
  `brBodovaIzSkole` double DEFAULT NULL,
  `smer` int(11) DEFAULT NULL,
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

insert  into `kandidat`(`prezime`,`sifraPrijave`,`jmbg`,`imeRoditelja`,`ime`,`pol`,`mobilni`,`fiksni`,`drzavljanstvoID`,`sifraZanimanjaRoditelja`,`sifraSS`,`nacionalnostID`,`ukupanRezultat`,`email`,`brBodovaIzSkole`,`smer`) values 
('Anic','333','0303995767042','A','Ana','Zenski','+0667777777','+012888888',1,100,200,300,63.7,'ana@gmail.com',40,4),
('Zikic','222','0505995707632','Z','Zika','Muski','+0662222222','+012555555',1,100,200,300,49.4,'zika@gmail.com',35,1),
('Mikic','111','0909995767032','M','Mika','Muski','+0644444444','+012222222',1,100,200,300,98,'mika@gmail.com',38,1);

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
(1,1,555,'0909995767032',100,'111'),
(2,2,555,NULL,NULL,'111'),
(1,3,666,'0505995707632',23,'444'),
(2,4,666,NULL,NULL,'444'),
(1,5,777,'0505995707632',7.5,'222'),
(2,6,777,NULL,NULL,'222'),
(1,7,888,'0303995767042',39.5,'876'),
(2,8,888,NULL,NULL,'876');

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
(2,'kom2','k2'),
(3,'kom5','komisija5');

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

/*Table structure for table `rang_lista` */

DROP TABLE IF EXISTS `rang_lista`;

CREATE TABLE `rang_lista` (
  `rlID` int(11) NOT NULL AUTO_INCREMENT,
  `godina` int(4) DEFAULT NULL,
  `smer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rlID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `rang_lista` */

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
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=latin1;

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
(140,20,'B','444'),
(141,1,'A','4545'),
(142,2,'B','4545'),
(143,3,'B','4545'),
(144,4,'B','4545'),
(145,5,'B','4545'),
(146,6,'B','4545'),
(147,7,'B','4545'),
(148,8,'B','4545'),
(149,9,'E','4545'),
(150,10,'C','4545'),
(151,11,'D','4545'),
(152,12,'E','4545'),
(153,13,'A','4545'),
(154,14,'A','4545'),
(155,15,'A','4545'),
(156,16,'A','4545'),
(157,17,'A','4545'),
(158,18,'A','4545'),
(159,19,'A','4545'),
(160,20,'A','4545'),
(161,1,'A','876'),
(162,2,'B','876'),
(163,3,'B','876'),
(164,4,'B','876'),
(165,5,'B','876'),
(166,6,'B','876'),
(167,7,'B','876'),
(168,8,'B','876'),
(169,9,'E','876'),
(170,10,'C','876'),
(171,11,'D','876'),
(172,12,'E','876'),
(173,13,'A','876'),
(174,14,'A','876'),
(175,15,'A','876'),
(176,16,'A','876'),
(177,17,'A','876'),
(178,18,'A','876'),
(179,19,'A','876'),
(180,20,'A','876');

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

/*Table structure for table `stavka_rang_liste` */

DROP TABLE IF EXISTS `stavka_rang_liste`;

CREATE TABLE `stavka_rang_liste` (
  `rlID` int(11) DEFAULT NULL,
  `redniBroj` int(3) DEFAULT NULL,
  `jmbg` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `brojPoena` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `stavka_rang_liste` */

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
) ENGINE=InnoDB AUTO_INCREMENT=521 DEFAULT CHARSET=latin1;

/*Data for the table `zadatak` */

insert  into `zadatak`(`kartonID`,`rbZadatka`,`odgovor`,`zadatakID`) values 
(1,1,'A',341),
(1,2,'A',342),
(1,3,'A',343),
(1,4,'A',344),
(1,5,'A',345),
(1,6,'A',346),
(1,7,'A',347),
(1,8,'A',348),
(1,9,'A',349),
(1,10,'A',350),
(1,11,'A',351),
(1,12,'A',352),
(1,13,'A',353),
(1,14,'A',354),
(1,15,'A',355),
(1,16,'A',356),
(1,17,'A',357),
(1,18,'A',358),
(1,19,'A',359),
(1,20,'A',360),
(2,1,'A',361),
(2,2,'A',362),
(2,3,'A',363),
(2,4,'A',364),
(2,5,'A',365),
(2,6,'A',366),
(2,7,'A',367),
(2,8,'A',368),
(2,9,'A',369),
(2,10,'A',370),
(2,11,'A',371),
(2,12,'A',372),
(2,13,'A',373),
(2,14,'A',374),
(2,15,'A',375),
(2,16,'A',376),
(2,17,'A',377),
(2,18,'A',378),
(2,19,'A',379),
(2,20,'A',380),
(3,1,'B',381),
(3,2,'B',382),
(3,3,'B',383),
(3,4,'B',384),
(3,5,'B',385),
(3,6,'B',386),
(3,7,'B',387),
(3,8,'B',388),
(3,9,'B',389),
(3,10,'B',390),
(3,11,'B',391),
(3,12,'B',392),
(3,13,'B',393),
(3,14,'B',394),
(3,15,'B',395),
(3,16,'B',396),
(3,17,'B',397),
(3,18,'B',398),
(3,19,'B',399),
(3,20,'B',400),
(4,1,'B',401),
(4,2,'B',402),
(4,3,'B',403),
(4,4,'B',404),
(4,5,'B',405),
(4,6,'B',406),
(4,7,'B',407),
(4,8,'B',408),
(4,9,'B',409),
(4,10,'B',410),
(4,11,'B',411),
(4,12,'B',412),
(4,13,'B',413),
(4,14,'B',414),
(4,15,'B',415),
(4,16,'B',416),
(4,17,'B',417),
(4,18,'B',418),
(4,19,'B',419),
(4,20,'B',420),
(5,1,'B',421),
(5,2,'B',422),
(5,3,'B',423),
(5,4,'B',424),
(5,5,'B',425),
(5,6,'B',426),
(5,7,'B',427),
(5,8,'B',428),
(5,9,'B',429),
(5,10,'B',430),
(5,11,'B',431),
(5,12,'B',432),
(5,13,'B',433),
(5,14,'B',434),
(5,15,'B',435),
(5,16,'C',436),
(5,17,'C',437),
(5,18,'C',438),
(5,19,'C',439),
(5,20,'C',440),
(5,21,'C',441),
(5,22,'C',442),
(5,23,'C',443),
(5,24,'C',444),
(5,25,'C',445),
(5,26,'C',446),
(5,27,'C',447),
(5,28,'C',448),
(5,29,'C',449),
(5,30,'C',450),
(6,1,'B',451),
(6,2,'B',452),
(6,3,'B',453),
(6,4,'B',454),
(6,5,'B',455),
(6,6,'B',456),
(6,7,'B',457),
(6,8,'B',458),
(6,9,'B',459),
(6,10,'B',460),
(6,11,'B',461),
(6,12,'B',462),
(6,13,'B',463),
(6,14,'B',464),
(6,15,'B',465),
(6,16,'C',466),
(6,17,'C',467),
(6,18,'C',468),
(6,19,'C',469),
(6,20,'C',470),
(6,21,'C',471),
(6,22,'C',472),
(6,23,'C',473),
(6,24,'C',474),
(6,25,'C',475),
(6,26,'C',476),
(6,27,'C',477),
(6,28,'C',478),
(6,29,'C',479),
(6,30,'C',480),
(7,1,'A',481),
(7,2,'A',482),
(7,3,'A',483),
(7,4,'A',484),
(7,5,'A',485),
(7,6,'A',486),
(7,7,'A',487),
(7,8,'A',488),
(7,9,'A',489),
(7,10,'A',490),
(7,11,'A',491),
(7,12,'A',492),
(7,13,'A',493),
(7,14,'A',494),
(7,15,'A',495),
(7,16,'A',496),
(7,17,'A',497),
(7,18,'A',498),
(7,19,'A',499),
(7,20,'A',500),
(8,1,'A',501),
(8,2,'A',502),
(8,3,'A',503),
(8,4,'A',504),
(8,5,'A',505),
(8,6,'A',506),
(8,7,'A',507),
(8,8,'A',508),
(8,9,'A',509),
(8,10,'A',510),
(8,11,'A',511),
(8,12,'A',512),
(8,13,'A',513),
(8,14,'A',514),
(8,15,'A',515),
(8,16,'A',516),
(8,17,'A',517),
(8,18,'A',518),
(8,19,'A',519),
(8,20,'A',520);

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
