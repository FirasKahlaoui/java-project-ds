-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 17 déc. 2023 à 11:35
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `club_management`
--

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `CIN` varchar(8) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Mail_Address` varchar(50) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Age` int(2) DEFAULT NULL,
  `Speciality` enum('Big Data Analytics','Full Stack Development','Machine Learning and Artificial Intelligence','Cybersecurity','Cloud Computing','Blockchain Technology') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`CIN`, `Name`, `LastName`, `Mail_Address`, `Password`, `Age`, `Speciality`) VALUES
('00112233', 'Liam', 'Smith', 'liam.smith@outlook.com', 'liampassword', 31, 'Cybersecurity'),
('01010101', 'Emily', 'Anderson', 'emily.anderson@outlook.com', 'emilypwd', 30, 'Big Data Analytics'),
('01122334', 'Michael', 'Williams', 'michael.williams@gmail.com', 'mikepass', 24, 'Full Stack Development'),
('01234561', 'Sophie', 'Clark', 'sophie.clark@outlook.com', 'sophiepass', 21, 'Blockchain Technology'),
('01234567', 'Jane', 'Smith', 'jane.smith@outlook.com', 'securepass', 25, 'Machine Learning and Artificial Intelligence'),
('01234568', 'Bob', 'Johnson', 'bob.johnson@hotmail.com', 'strongpass', 30, 'Cloud Computing'),
('03568744', 'Hiba', 'Masoudi', 'hibamasoudi@gmail.com', 'Hiba@123', 24, 'Blockchain Technology'),
('04057485', 'Aziz', 'Arfaoui', 'arfaouiaziz2022@gmail.com', 'CodeMaster42!', 20, 'Big Data Analytics'),
('10101010', 'Olivia', 'Brown', 'olivia.brown@gmail.com', 'oliviapass', 27, 'Machine Learning and Artificial Intelligence'),
('10293847', 'Alice', 'Johnson', 'alice.johnson@gmail.com', 'pass123', 22, 'Full Stack Development'),
('10345678', 'David', 'Miller', 'david.miller@gmail.com', 'davidpass', 32, 'Cybersecurity'),
('11002233', 'Ryan', 'Johnson', 'ryan.johnson@hotmail.com', 'ryanpass', 29, 'Cloud Computing'),
('11023070', 'Farah', 'Ben Mahmoud', 'farahbenmahmoud@hotmail.com', 'FarahPass123', 23, 'Cloud Computing'),
('12170016', 'Nizar', 'Zamel', 'nizarzamel@gmail.com', 'NizarPass789', 28, 'Machine Learning and Artificial Intelligence'),
('12345678', 'John', 'Doe', 'john.doe@gmail.com', 'password123', 20, 'Full Stack Development'),
('14079066', 'Yasmine', 'Zaatour', 'yasminezaatour@outlook.com', 'YasminePwd456', 20, 'Big Data Analytics'),
('16987458', 'Adem', 'Driss', 'ademdriss@hotmail.com', 'AdemPwd321', 30, 'Full Stack Development'),
('76543210', 'Charlie', 'Brown', 'charlie.brown@outlook.com', 'securepwd', 28, 'Machine Learning and Artificial Intelligence'),
('98765432', 'Eva', 'Smith', 'eva.smith@hotmail.com', 'evapassword', 26, 'Big Data Analytics');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`CIN`),
  ADD UNIQUE KEY `Mail_Address` (`Mail_Address`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
