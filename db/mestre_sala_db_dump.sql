-- MariaDB dump 10.19  Distrib 10.4.28-MariaDB, for Win64 (AMD64)
--
-- Host: aiven_aws_db    Database: mestre_sala_db
-- ------------------------------------------------------
-- Server version	10.4.28-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `description` varchar(255) NOT NULL,
                        `location` varchar(255) NOT NULL,
                        `title` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'A sala possui ar-condicionado, o controle se encontra na gaveta da mesa mais ao fundo. Possui 5 mesas de escritório com tomadas.','Piso C, ao lado da cantina.','Sala Roma'),(2,'A sala possui uma mesa de jantar relativamente grande, com cadeiras empilhadas nas paredes para uso. Além de ar-condicionado.','Piso A ao fundo do corredor.','Sala Paris'),(3,'A sala possui uma mesa circular para reuniões, com projetor e telão pronto para uso. Também possui ar-condicionado.','Piso B, ao fim do corredor.','Sala Tokyo'),(4,'Sala de descanso, com puffs, computadores com jogos e um PlayStation 5 com 4 controles e jogos instalados.','Piso B, terceira sala na direita do corredor.','Sala Moscow');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `email` varchar(255) NOT NULL,
                        `full_name` varchar(255) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `user_role` tinyint(4) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'josias@gmail.com','José Henrique Andrade','$2a$10$WeSeN7QEAKUPEjVaPeoUDO97gFkdt6RP2AbhNxP/izO0VcF7bHXSW',1),(2,'teste@gmail.com','teste','$2a$10$qr6cl1y9C4g0gvcBpG2du.hKhwRVxRMBg3XEzTIKEUySLqgUl.iFi',1),(3,'teste2@gmail.com','teste2','$2a$10$tFIw/QZb6KUS5Ef.vl8VnuOdH6ftG/jmLBkWjRdlIu4WL2KjsI7bO',1),(4,'lucao@gmail.com','Lucas Santiago Cardoso','$2a$10$BFHQB3UVX7owJvLjWXbnpu11T5uyFbiL9G527.LGDtycgTSyg6ie6',1),(5,'anoca@gmail.com','Ana Clara Borges','$2a$10$KT3fUOfIzI1CXxs04d5nNOKL10.re4AyTH5q8FELLsflJCtBm2xN.',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `begin` time(6) DEFAULT NULL,
                               `date` datetime(6) DEFAULT NULL,
                               `description` varchar(255) DEFAULT NULL,
                               `end` time(6) DEFAULT NULL,
                               `title` varchar(255) DEFAULT NULL,
                               `room` bigint(20) NOT NULL,
                               `owner` bigint(20) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKpdv4c8ailn2iaw1rexwfsjmcd` (`room`),
                               KEY `FKs0o6t18m7myes1892guak7075` (`owner`),
                               CONSTRAINT `FKpdv4c8ailn2iaw1rexwfsjmcd` FOREIGN KEY (`room`) REFERENCES `room` (`id`),
                               CONSTRAINT `FKs0o6t18m7myes1892guak7075` FOREIGN KEY (`owner`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (3,'10:00:00.000000','2023-09-04 00:00:00.000000','teste','10:30:00.000000','teste',1,1),(4,'11:30:00.000000','2023-09-04 00:00:00.000000','teste','12:30:00.000000','teste',1,1),(5,'12:31:00.000000','2023-09-04 00:00:00.000000','teste','13:00:00.000000','teste',1,1),(6,'13:00:01.000000','2023-09-04 00:00:00.000000','teste','14:00:00.000000','teste',1,1),(7,'16:00:00.000000','2023-09-04 00:00:00.000000','teste','17:00:00.000000','teste',1,1),(8,'17:00:00.000000','2023-09-04 00:00:00.000000','teste','18:00:00.000000','teste',1,1),(9,'18:30:00.000000','2023-09-04 00:00:00.000000','teste','19:00:00.000000','teste',1,1),(10,'19:30:00.000000','2023-09-04 00:00:00.000000','teste','20:00:00.000000','teste',1,1),(11,'20:00:00.000000','2023-10-16 00:00:00.000000','Testando pelo front','21:00:00.000000','Testandooo',2,1),(12,'21:10:00.000000','2023-10-17 00:00:00.000000','teste 2 pelo front','22:09:00.000000','testeee 2',2,1),(13,'17:00:00.000000','2023-10-17 21:00:00.000000','Festa Surpresa para o Lucas. Ele não pode saber de nada!','23:00:00.000000','Festa Surpresa do Lucas',2,5),(14,'19:30:00.000000','2023-09-04 21:00:00.000000','teste','20:00:00.000000','teste',1,1),(15,'16:20:00.000000','2023-10-17 21:00:00.000000','só pra testar como a data ta chegando pro font','17:20:00.000000','Teste de Data',4,5),(16,'16:20:00.000000','2023-10-21 21:00:00.000000','teste de data 3','17:20:00.000000','Teste de Data 3',1,5),(17,'19:30:00.000000','2023-09-11 21:00:00.000000','teste','20:00:00.000000','teste',1,1),(18,'19:30:00.000000','2023-08-11 21:00:00.000000','teste','20:00:00.000000','teste',1,1),(19,'19:30:00.000000','2023-06-11 21:00:00.000000','teste','20:00:00.000000','teste',1,1),(20,'19:30:00.000000','2023-06-12 21:00:00.000000','teste','20:00:00.000000','teste',1,1),(21,'19:00:00.000000','2023-10-18 21:00:00.000000','teste, só isso','20:00:00.000000','teste de reserva na data',1,5),(22,'19:05:00.000000','2023-10-19 21:00:00.000000','teste 2','20:05:00.000000','teste de data 2',3,5),(23,'22:10:00.000000','2023-10-19 21:00:00.000000','teste 3','23:10:00.000000','Teste de data 3',3,5),(24,'16:10:00.000000','2023-10-19 21:00:00.000000','teste 4','17:10:00.000000','Teste de data 4',1,5),(25,'19:20:00.000000','2023-10-18 21:00:00.000000','testeeeee','20:20:00.000000','teste sei la qual num',2,5),(26,'14:30:00.000000','2023-10-18 21:00:00.000000','testando testando testando tetetetetetestando','15:30:00.000000','Testando Testando',3,5),(27,'16:30:00.000000','2023-10-18 21:00:00.000000','não aguento mais mano meu deus','15:24:00.000000','Não aguento mais',1,5),(28,'23:38:00.000000','2023-10-18 21:00:00.000000','chega','23:42:00.000000','Para, por favor',2,5);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-19 19:07:55