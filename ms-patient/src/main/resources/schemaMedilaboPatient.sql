CREATE DATABASE IF NOT EXISTS `medilabopatient` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `medilabopatient`;

CREATE TABLE IF NOT EXISTS `patient` (
  `patient_id` bigint NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `is_active` boolean NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `patient` (`patient_id`, `firstname`, `lastname`, `birthdate`, `gender`, `address`, `phone`, `is_active`) VALUES
	(1, 'Test', 'TestNone', '1966-12-31', 'F', '1 Brookside St', '100-222-3333', 1),
	(2, 'Test', 'TestBorderline', '1945-06-24', 'M', '2 High St', '200-333-4444',1),
	(3, 'Test', 'TestInDanger', '2004-06-18', 'M', '3 Club Road', '300-444-5555',1),
	(4, 'Test', 'TestEarlyOnset', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666',1);