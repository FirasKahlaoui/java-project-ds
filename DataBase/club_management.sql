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
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `ID_Admin` int(11) NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Email_Address` varchar(50) NOT NULL,
  `Password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`ID_Admin`, `FirstName`, `LastName`, `Email_Address`, `Password`) VALUES
(1, 'Firas', 'Kahlaoui', 'kahlaouifiras2017@gmail.com', 'ExpertHack89'),
(2, 'Skander', 'Abidi', 'skander.abidi88@gmail.com', 'UseVSCode23'),
(3, 'Mohamed', 'Hassan', 'hassanmohamed@gmail.com', 'info2580');

-- --------------------------------------------------------

--
-- Structure de la table `club`
--

CREATE TABLE `club` (
  `NumClub` int(2) NOT NULL,
  `NameClub` varchar(20) NOT NULL,
  `Type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `club`
--

INSERT INTO `club` (`NumClub`, `NameClub`, `Type`) VALUES
(1, 'Coding Wizards', 'Tech'),
(2, 'Green Earth Society', 'Environment'),
(3, 'Musical Harmony', 'Arts'),
(4, 'Fitness Fanatics', 'Sports'),
(5, 'Bookworms Club', 'Literature'),
(6, 'Innovation Hub', 'Tech'),
(7, 'Dance Fusion', 'Arts'),
(8, 'Sports Unlimited', 'Sports'),
(9, 'Nature Explorers', 'Environment'),
(10, 'Film Buffs Society', 'Arts');

-- --------------------------------------------------------

--
-- Structure de la table `participate`
--

CREATE TABLE `participate` (
  `NumClub` int(2) NOT NULL,
  `CIN` varchar(8) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `participate`
--

INSERT INTO `participate` (`NumClub`, `CIN`, `Date`) VALUES
(1, '00112233', '2023-12-17'),
(1, '01234561', '2023-12-17'),
(1, '10101010', '2023-12-17'),
(2, '01122334', '2023-12-17'),
(2, '01234561', '2023-12-17'),
(2, '11002233', '2023-12-17'),
(3, '01010101', '2023-12-17'),
(3, '01234567', '2023-12-17'),
(3, '11002233', '2023-12-17'),
(3, '16987458', '2023-12-17'),
(4, '01010101', '2023-12-17'),
(4, '01234567', '2023-12-17'),
(4, '12170016', '2023-12-17'),
(5, '01234568', '2023-12-17'),
(5, '10345678', '2023-12-17'),
(5, '12345678', '2023-12-17'),
(5, '14079066', '2023-12-17'),
(5, '16987458', '2023-12-17'),
(6, '00112233', '2023-12-17'),
(6, '04057485', '2023-12-17'),
(6, '10345678', '2023-12-17'),
(6, '14079066', '2023-12-17'),
(7, '01122334', '2023-12-17'),
(7, '03568744', '2023-12-17'),
(7, '12170016', '2023-12-17'),
(8, '01234568', '2023-12-17'),
(8, '10101010', '2023-12-17'),
(8, '10293847', '2023-12-17'),
(8, '11023070', '2023-12-17'),
(9, '03568744', '2023-12-17'),
(9, '10293847', '2023-12-17'),
(9, '12345678', '2023-12-17'),
(10, '03568744', '2023-12-17'),
(10, '04057485', '2023-12-17'),
(10, '11023070', '2023-12-17');

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
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`ID_Admin`),
  ADD UNIQUE KEY `Email_Address` (`Email_Address`);

--
-- Index pour la table `club`
--
ALTER TABLE `club`
  ADD PRIMARY KEY (`NumClub`);

--
-- Index pour la table `participate`
--
ALTER TABLE `participate`
  ADD PRIMARY KEY (`NumClub`,`CIN`),
  ADD KEY `UserIndex` (`CIN`),
  ADD KEY `ClubIndex` (`NumClub`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`CIN`),
  ADD UNIQUE KEY `Mail_Address` (`Mail_Address`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `admin`
--
ALTER TABLE `admin`
  MODIFY `ID_Admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `participate`
--
ALTER TABLE `participate`
  ADD CONSTRAINT `ClubIndex` FOREIGN KEY (`NumClub`) REFERENCES `club` (`NumClub`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `UserIndex` FOREIGN KEY (`CIN`) REFERENCES `user` (`CIN`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
