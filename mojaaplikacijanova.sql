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
  PRIMARY KEY (`clanID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clan` */

insert  into `clan`(`clanID`,`ime`,`prezime`) values 
(1,'Dusan','Savic'),
(2,'Sladjana','Benkovic'),
(3,'Veljko','Jeremic'),
(4,'Mladen','Cudanov'),
(5,'Dusan','Barac'),
(6,'Dejan','Stojimirovic');

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
('1225',1),
('555',1),
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
  `drzevljanstvoID` int(11) DEFAULT NULL,
  `sifraZanimanjaRoditelja` int(11) DEFAULT NULL,
  `sifraSS` int(11) DEFAULT NULL,
  `nacionalnostID` int(11) DEFAULT NULL,
  PRIMARY KEY (`jmbg`),
  KEY `drzevljanstvoID` (`drzevljanstvoID`),
  KEY `sifraZanimanjaRoditelja` (`sifraZanimanjaRoditelja`),
  KEY `sifraSS` (`sifraSS`),
  KEY `nacionalnostID` (`nacionalnostID`),
  CONSTRAINT `kandidat_ibfk_1` FOREIGN KEY (`drzevljanstvoID`) REFERENCES `drzevljanstvo` (`drzevljanstvoID`),
  CONSTRAINT `kandidat_ibfk_2` FOREIGN KEY (`sifraZanimanjaRoditelja`) REFERENCES `zanimanjeroditelja` (`sifraZanimanjaID`),
  CONSTRAINT `kandidat_ibfk_3` FOREIGN KEY (`sifraSS`) REFERENCES `srednjaskola` (`sifraSrednjeSkole`),
  CONSTRAINT `kandidat_ibfk_4` FOREIGN KEY (`nacionalnostID`) REFERENCES `nacionalnost` (`nacionalnostID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `kandidat` */

insert  into `kandidat`(`prezime`,`sifraPrijave`,`jmbg`,`imeRoditelja`,`ime`,`pol`,`mobilni`,`fiksni`,`drzevljanstvoID`,`sifraZanimanjaRoditelja`,`sifraSS`,`nacionalnostID`) values 
('Nerandzic','254','1512356565852','Jovan','Jovana','Zenski','065-4444444','017/5485625',2,101,201,301),
('Mladenovic','1','1705995887796','Bora','Marija','Zenski','064-77845230','018/55426',1,100,200,300);

/*Table structure for table `karton` */

DROP TABLE IF EXISTS `karton`;

CREATE TABLE `karton` (
  `brojUnosa` int(11) DEFAULT NULL,
  `kartonID` int(11) NOT NULL,
  `brojKartona` int(11) DEFAULT NULL,
  `kandidatID` varchar(255) DEFAULT NULL,
  `sifraPrijave` varchar(255) DEFAULT NULL,
  `rezultatTesta` double DEFAULT NULL,
  `brojGrupe` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`kartonID`),
  KEY `brojGrupe` (`brojGrupe`),
  KEY `kandidatID` (`kandidatID`),
  CONSTRAINT `karton_ibfk_1` FOREIGN KEY (`brojGrupe`) REFERENCES `grupazadatka` (`brojGrupe`),
  CONSTRAINT `karton_ibfk_2` FOREIGN KEY (`kandidatID`) REFERENCES `kandidat` (`jmbg`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `karton` */

insert  into `karton`(`brojUnosa`,`kartonID`,`brojKartona`,`kandidatID`,`sifraPrijave`,`rezultatTesta`,`brojGrupe`) values 
(1,1,12345,'1705995887796','1',57,'1225'),
(2,22,12345,'1705995887796','1',57,'1225');

/*Table structure for table `komisija` */

DROP TABLE IF EXISTS `komisija`;

CREATE TABLE `komisija` (
  `komisijaID` int(11) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `clan1` int(11) DEFAULT NULL,
  `clan2` int(11) DEFAULT NULL,
  `clan3` int(11) DEFAULT NULL,
  PRIMARY KEY (`komisijaID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `komisija` */

insert  into `komisija`(`komisijaID`,`user`,`password`,`clan1`,`clan2`,`clan3`) values 
(111,'komisija1','prvakomisija',1,5,3),
(112,'komisija2','drugakomisija',6,1,2),
(113,'komisija3','trecakomisija',4,6,3);

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
  `smer` varchar(255) DEFAULT NULL,
  `godina` int(4) DEFAULT NULL,
  PRIMARY KEY (`sifraRL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rang-lista` */

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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

/*Data for the table `resenje` */

insert  into `resenje`(`resenjeID`,`rbZadatka`,`odgovor`,`brojGrupe`) values 
(1,1,'A','1225'),
(2,2,'B','1225'),
(3,3,'C','1225'),
(4,4,'D','1225'),
(5,5,'E','1225'),
(6,6,'A','1225'),
(7,7,'b','1225'),
(8,8,'c','1225'),
(9,9,'d','1225'),
(10,10,'e','1225'),
(11,11,'b','1225'),
(12,12,'b','1225'),
(13,13,'a','1225'),
(14,14,'c','1225'),
(15,15,'d','1225'),
(16,16,'b','1225'),
(17,17,'e','1225'),
(18,18,'a','1225'),
(19,19,'a','1225'),
(20,20,'a','1225'),
(21,1,'a','222'),
(22,2,'b','222'),
(23,3,'c','222'),
(24,4,'d','222'),
(25,5,'e','222'),
(26,6,'e','222'),
(27,7,'a','222'),
(28,8,'b','222'),
(29,9,'c','222'),
(30,10,'d','222'),
(31,11,'e','222'),
(32,12,'n','222'),
(33,13,'a','222'),
(34,14,'b','222'),
(35,15,'c','222'),
(36,16,'d','222'),
(37,17,'e','222'),
(38,18,'e','222'),
(39,19,'a','222'),
(40,20,'b','222'),
(41,21,'c','222'),
(42,22,'d','222'),
(43,23,'d','222'),
(44,24,'a','222'),
(45,25,'a','222'),
(46,26,'a','222'),
(47,27,'b','222'),
(48,28,'b','222'),
(49,29,'b','222'),
(50,30,'d','222'),
(51,1,'a','555'),
(52,2,'a','555'),
(53,3,'a','555'),
(54,4,'a','555'),
(55,5,'a','555'),
(56,6,'b','555'),
(57,7,'b','555'),
(58,8,'b','555'),
(59,9,'b','555'),
(60,10,'c','555'),
(61,11,'c','555'),
(62,12,'c','555'),
(63,13,'c','555'),
(64,14,'d','555'),
(65,15,'d','555'),
(66,16,'d','555'),
(67,17,'d','555'),
(68,18,'e','555'),
(69,19,'e','555'),
(70,20,'e','555');

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
  `redniBroj` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stavka_rang-liste` */

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
  PRIMARY KEY (`kartonID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `zadatak` */

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
