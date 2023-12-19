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

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `participate`
--
ALTER TABLE `participate`
  ADD PRIMARY KEY (`NumClub`,`CIN`),
  ADD KEY `UserIndex` (`CIN`),
  ADD KEY `ClubIndex` (`NumClub`);

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
