-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 09 jan. 2024 à 15:21
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `planninghospitalier`
--

-- --------------------------------------------------------

--
-- Structure de la table `besoinenpersonnel`
--

CREATE TABLE `besoinenpersonnel` (
  `idBesoin` int(11) NOT NULL,
  `nbPersonnes` int(11) DEFAULT NULL,
  `idSpe` int(11) DEFAULT NULL,
  `idFonction` int(11) NOT NULL,
  `idCreneau` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `besoinenpersonnel`
--

INSERT INTO `besoinenpersonnel` (`idBesoin`, `nbPersonnes`, `idSpe`, `idFonction`, `idCreneau`) VALUES
(1, 2, 1, 2, 1),
(2, 6, 2, 1, 2),
(3, 2, 3, 6, 3),
(4, 3, 4, 3, 4),
(5, 2, 5, 5, 5),
(6, 2, 6, 4, 6),
(7, 3, 7, 7, 7),
(8, 2, 8, 8, 8),
(9, 4, 9, 9, 9),
(10, 4, 10, 10, 10),
(11, 2, 10, 10, 11),
(12, 5, 2, 2, 12),
(13, 2, 3, 3, 13),
(14, 4, 4, 4, 14),
(15, 2, 5, 5, 15),
(16, 1, 6, 6, 16),
(17, 2, 7, 7, 17),
(18, 3, 8, 8, 18),
(19, 3, 9, 9, 19),
(20, 4, 10, 10, 20),
(21, 2, 1, 1, 21),
(22, 4, 2, 2, 22),
(23, 3, 3, 3, 23),
(24, 6, 4, 4, 24),
(25, 1, 5, 5, 25),
(26, 2, 6, 6, 26),
(27, 1, 7, 7, 27),
(28, 2, 8, 8, 27),
(29, 2, 9, 9, 28),
(30, 3, 10, 10, 28),
(31, 3, 1, 1, 12);

-- --------------------------------------------------------

--
-- Structure de la table `contraintedespe`
--

CREATE TABLE `contraintedespe` (
  `idContrainteSpe` int(11) NOT NULL,
  `nomContrainteSpe` varchar(50) NOT NULL,
  `activation` tinyint(1) NOT NULL,
  `duree` decimal(15,2) DEFAULT NULL,
  `temporalite` varchar(30) NOT NULL,
  `idSpe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `contraintedespe`
--

INSERT INTO `contraintedespe` (`idContrainteSpe`, `nomContrainteSpe`, `activation`, `duree`, `temporalite`, `idSpe`) VALUES
(1, 'Reglementation Obstetrique', 1, 8.00, 'Quotidienne', 1),
(2, 'Protocole Oncologie', 1, 7.00, 'Hebdomadaire', 2),
(3, 'Suivi Cardiaque', 1, 6.00, 'Mensuelle', 3),
(4, 'Soins Pediatriques', 1, 8.00, 'Quotidienne', 4),
(5, 'Procedures Chirurgicales', 1, 9.00, 'Quotidienne', 5),
(6, 'Anesthesie Dermatologique', 1, 6.00, 'Mensuelle', 6),
(7, 'Analyses Neurologiques', 1, 7.00, 'Hebdomadaire', 7),
(8, 'Geriatrie Medicamenteuse', 1, 5.00, 'Quotidienne', 8),
(9, 'Prise en charge Psychiatrique', 1, 8.00, 'Quotidienne', 9),
(10, 'Examens Radiologiques', 1, 7.00, 'Hebdomadaire', 10);

-- --------------------------------------------------------

--
-- Structure de la table `contraintelegale`
--

CREATE TABLE `contraintelegale` (
  `idContrainteLegale` int(11) NOT NULL,
  `nomContrainteLegale` varchar(50) DEFAULT NULL,
  `duree` decimal(15,2) NOT NULL,
  `temporalite` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `contraintelegale`
--

INSERT INTO `contraintelegale` (`idContrainteLegale`, `nomContrainteLegale`, `duree`, `temporalite`) VALUES
(1, 'Pause legale', 1.50, 'Quotidienne'),
(2, 'Conges payes', 25.00, 'Annuelle'),
(3, 'Duree maximale hebdomadaire', 48.00, 'Hebdomadaire'),
(4, 'Seuil minimum de travail hebdomadaire', 20.00, 'Hebdomadaire'),
(5, 'Duree maximale journaliere de travail', 10.00, 'Quotidienne'),
(6, 'Repos entre deux journees de travail', 11.00, 'Quotidienne'),
(7, 'Temps de repos entre deux gardes', 12.00, 'Quotidienne'),
(8, 'Limite annuelle d\'heures supplementaires', 150.00, 'Annuelle'),
(9, 'Pause dejeuner minimale', 0.75, 'Quotidienne'),
(10, 'Surcharge de travail hebdomadaire', 45.00, 'Hebdomadaire');

-- --------------------------------------------------------

--
-- Structure de la table `creneau`
--

CREATE TABLE `creneau` (
  `idCreneau` int(11) NOT NULL,
  `dateCreneau` date NOT NULL,
  `horaireDeb` time NOT NULL,
  `horaireFin` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `creneau`
--

INSERT INTO `creneau` (`idCreneau`, `dateCreneau`, `horaireDeb`, `horaireFin`) VALUES
(1, '2023-12-10', '08:00:00', '12:00:00'),
(2, '2023-12-11', '13:00:00', '17:00:00'),
(3, '2023-12-12', '09:00:00', '11:00:00'),
(4, '2023-12-13', '14:00:00', '18:00:00'),
(5, '2023-12-14', '10:00:00', '12:30:00'),
(6, '2023-12-15', '07:30:00', '11:30:00'),
(7, '2023-12-16', '12:00:00', '16:00:00'),
(8, '2023-12-17', '08:30:00', '10:30:00'),
(9, '2023-12-18', '14:30:00', '18:30:00'),
(10, '2023-12-19', '11:00:00', '13:00:00'),
(11, '2024-01-12', '08:00:00', '12:00:00'),
(12, '2023-01-12', '13:00:00', '17:00:00'),
(13, '2023-01-11', '09:00:00', '11:00:00'),
(14, '2023-01-10', '14:00:00', '18:00:00'),
(15, '2024-01-12', '08:00:00', '12:00:00'),
(16, '2024-01-12', '13:00:00', '17:00:00'),
(17, '2024-01-11', '09:00:00', '11:00:00'),
(18, '2024-01-10', '14:00:00', '18:00:00'),
(19, '2024-01-12', '17:30:00', '19:00:00'),
(20, '2024-01-12', '08:00:00', '12:00:00'),
(21, '2024-01-12', '13:00:00', '17:00:00'),
(22, '2024-01-11', '09:00:00', '11:00:00'),
(23, '2024-01-10', '14:00:00', '18:00:00'),
(24, '2024-01-10', '10:00:00', '13:00:00'),
(25, '2024-01-09', '10:00:00', '13:00:00'),
(26, '2024-01-09', '08:00:00', '09:00:00'),
(27, '2024-01-08', '08:00:00', '12:00:00'),
(28, '2024-01-08', '14:00:00', '18:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `estaffecte`
--

CREATE TABLE `estaffecte` (
  `idPersonnel` int(11) NOT NULL,
  `idBesoin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `estaffecte`
--

INSERT INTO `estaffecte` (`idPersonnel`, `idBesoin`) VALUES
(1, 2),
(2, 1),
(3, 4),
(4, 6),
(5, 5),
(6, 3),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(16, 16),
(17, 17),
(18, 18),
(19, 19),
(20, 20),
(21, 21),
(22, 22),
(23, 23),
(24, 24),
(25, 25),
(26, 26),
(27, 27),
(28, 28),
(29, 29),
(30, 30),
(31, 31);

-- --------------------------------------------------------

--
-- Structure de la table `fonction`
--

CREATE TABLE `fonction` (
  `idFonction` int(11) NOT NULL,
  `nomFonction` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `fonction`
--

INSERT INTO `fonction` (`idFonction`, `nomFonction`) VALUES
(1, 'IDE'),
(2, 'Infirmier Stagiaire'),
(3, 'Aide-Soignant'),
(4, 'Medecin Resident'),
(5, 'Chirurgien'),
(6, 'Anesthesiste'),
(7, 'Technicien de Laboratoire'),
(8, 'Secretaire Medical'),
(9, 'ASH'),
(10, 'Radiologue');

-- --------------------------------------------------------

--
-- Structure de la table `nonrespectlegal`
--

CREATE TABLE `nonrespectlegal` (
  `idPersonnel` int(11) NOT NULL,
  `idContrainteLegale` int(11) NOT NULL,
  `idBesoin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `nonrespectlegal`
--

INSERT INTO `nonrespectlegal` (`idPersonnel`, `idContrainteLegale`, `idBesoin`) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 3, 4),
(4, 4, 6),
(5, 5, 5),
(6, 6, 3),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10);

-- --------------------------------------------------------

--
-- Structure de la table `nonrespectspe`
--

CREATE TABLE `nonrespectspe` (
  `idPersonnel` int(11) NOT NULL,
  `idContrainteSpe` int(11) NOT NULL,
  `idBesoin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `nonrespectspe`
--

INSERT INTO `nonrespectspe` (`idPersonnel`, `idContrainteSpe`, `idBesoin`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10);

-- --------------------------------------------------------

--
-- Structure de la table `personnel`
--

CREATE TABLE `personnel` (
  `idPersonnel` int(11) NOT NULL,
  `nomPersonnel` varchar(50) NOT NULL,
  `prenomPersonnel` varchar(50) NOT NULL,
  `dateNaissance` date NOT NULL,
  `tempsTravailMensuel` int(11) DEFAULT NULL,
  `idFonction` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `personnel`
--

INSERT INTO `personnel` (`idPersonnel`, `nomPersonnel`, `prenomPersonnel`, `dateNaissance`, `tempsTravailMensuel`, `idFonction`) VALUES
(1, 'Dubois', 'Sophia', '1988-06-20', 160, 1),
(2, 'Lefevre', 'Thomas', '1990-04-15', 150, 2),
(3, 'Martin', 'Julie', '1995-11-10', 170, 3),
(4, 'Garcia', 'Lucie', '1986-09-27', 155, 4),
(5, 'Roux', 'Antoine', '1992-03-18', 165, 5),
(6, 'Moreau', 'Emma', '1987-12-05', 145, 6),
(7, 'Laurent', 'Paul', '1989-08-30', 160, 7),
(8, 'Michel', 'Sophie', '1994-05-12', 155, 8),
(9, 'Simon', 'Luc', '1993-07-25', 160, 9),
(10, 'Duchene', 'lucas', '1990-01-01', 160, 1),
(11, 'lemant', 'jean', '1990-02-01', 100, 1),
(12, 'martinez', 'kilian', '1994-11-01', 60, 1),
(13, 'guillon', 'phillipe', '1999-09-11', 10, 2),
(14, 'smith', 'cathrine', '1995-12-01', 167, 2),
(15, 'johnson', 'mark', '1989-09-05', 200, 2),
(16, 'williams', 'hugo', '2000-01-01', 160, 3),
(17, 'johns', 'johana', '1995-09-04', 160, 3),
(18, 'brown', 'melissa', '1988-11-21', 150, 3),
(19, 'millier', 'andrea', '1996-12-23', 180, 4),
(20, 'wilson', 'ines', '1992-10-25', 160, 4),
(21, 'Moore', 'sarah', '1991-11-29', 190, 4),
(22, 'Taylor', 'johan', '1990-01-01', 100, 5),
(23, 'Anderson', 'Emma', '1993-07-21', 160, 5),
(24, 'Thomas', 'Liam', '1999-12-30', 160, 5),
(25, 'Jackson', 'Olivia', '2002-11-01', 10, 6),
(26, 'White', 'Noah', '1985-11-01', 200, 6),
(27, 'Harris', 'Ava', '1980-01-01', 160, 6),
(28, 'Martin', 'James', '1970-09-11', 160, 7),
(29, 'Thompson', 'Isabella', '1966-11-21', 180, 7),
(30, 'Garcias', 'William', '1970-01-01', 160, 7),
(31, 'Robinson', 'Sophia', '1950-01-01', 160, 8),
(32, 'Mason', 'Benjamin', '1980-01-01', 160, 8),
(33, 'Alexander', 'Amelia', '1967-01-01', 160, 8),
(34, 'Gerou', 'Oliver', '1999-01-01', 160, 9),
(35, 'Lewis', 'Charlotte', '1960-01-01', 160, 9),
(36, 'Clark', 'Elijah', '1999-01-01', 160, 9),
(37, 'Young', 'Mia', '1990-09-01', 160, 10),
(38, 'Lee', 'Harper', '1990-11-01', 160, 10),
(39, 'Walker', 'Evelyn', '1970-01-01', 160, 10),
(40, 'Gonzalez', 'Angel', '1993-08-20', 155, 10);

-- --------------------------------------------------------

--
-- Structure de la table `sespecialise`
--

CREATE TABLE `sespecialise` (
  `idPersonnel` int(11) NOT NULL,
  `idSpe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `sespecialise`
--

INSERT INTO `sespecialise` (`idPersonnel`, `idSpe`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);

-- --------------------------------------------------------

--
-- Structure de la table `specialite`
--

CREATE TABLE `specialite` (
  `idSpe` int(11) NOT NULL,
  `nomSpe` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `specialite`
--

INSERT INTO `specialite` (`idSpe`, `nomSpe`) VALUES
(1, 'Obstetrique'),
(2, 'Oncologie'),
(3, 'Cardiologie'),
(4, 'Pediatrie'),
(5, 'Chirurgie Generale'),
(6, 'Dermatologie'),
(7, 'Neurologie'),
(8, 'Geriatrie'),
(9, 'Psychiatrie'),
(10, 'Radiologie');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `besoinenpersonnel`
--
ALTER TABLE `besoinenpersonnel`
  ADD PRIMARY KEY (`idBesoin`),
  ADD KEY `idSpe` (`idSpe`),
  ADD KEY `idFonction` (`idFonction`),
  ADD KEY `idCreneau` (`idCreneau`);

--
-- Index pour la table `contraintedespe`
--
ALTER TABLE `contraintedespe`
  ADD PRIMARY KEY (`idContrainteSpe`),
  ADD KEY `idSpe` (`idSpe`);

--
-- Index pour la table `contraintelegale`
--
ALTER TABLE `contraintelegale`
  ADD PRIMARY KEY (`idContrainteLegale`);

--
-- Index pour la table `creneau`
--
ALTER TABLE `creneau`
  ADD PRIMARY KEY (`idCreneau`);

--
-- Index pour la table `estaffecte`
--
ALTER TABLE `estaffecte`
  ADD PRIMARY KEY (`idPersonnel`,`idBesoin`),
  ADD KEY `idBesoin` (`idBesoin`);

--
-- Index pour la table `fonction`
--
ALTER TABLE `fonction`
  ADD PRIMARY KEY (`idFonction`);

--
-- Index pour la table `nonrespectlegal`
--
ALTER TABLE `nonrespectlegal`
  ADD PRIMARY KEY (`idPersonnel`,`idContrainteLegale`,`idBesoin`),
  ADD KEY `idContrainteLegale` (`idContrainteLegale`),
  ADD KEY `idBesoin` (`idBesoin`);

--
-- Index pour la table `nonrespectspe`
--
ALTER TABLE `nonrespectspe`
  ADD PRIMARY KEY (`idPersonnel`,`idContrainteSpe`,`idBesoin`),
  ADD KEY `idContrainteSpe` (`idContrainteSpe`),
  ADD KEY `idBesoin` (`idBesoin`);

--
-- Index pour la table `personnel`
--
ALTER TABLE `personnel`
  ADD PRIMARY KEY (`idPersonnel`),
  ADD KEY `idFonction` (`idFonction`);

--
-- Index pour la table `sespecialise`
--
ALTER TABLE `sespecialise`
  ADD PRIMARY KEY (`idPersonnel`,`idSpe`),
  ADD KEY `idSpe` (`idSpe`);

--
-- Index pour la table `specialite`
--
ALTER TABLE `specialite`
  ADD PRIMARY KEY (`idSpe`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `besoinenpersonnel`
--
ALTER TABLE `besoinenpersonnel`
  MODIFY `idBesoin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT pour la table `contraintedespe`
--
ALTER TABLE `contraintedespe`
  MODIFY `idContrainteSpe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `contraintelegale`
--
ALTER TABLE `contraintelegale`
  MODIFY `idContrainteLegale` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `creneau`
--
ALTER TABLE `creneau`
  MODIFY `idCreneau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT pour la table `fonction`
--
ALTER TABLE `fonction`
  MODIFY `idFonction` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `personnel`
--
ALTER TABLE `personnel`
  MODIFY `idPersonnel` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=153;

--
-- AUTO_INCREMENT pour la table `specialite`
--
ALTER TABLE `specialite`
  MODIFY `idSpe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `besoinenpersonnel`
--
ALTER TABLE `besoinenpersonnel`
  ADD CONSTRAINT `besoinenpersonnel_ibfk_1` FOREIGN KEY (`idSpe`) REFERENCES `specialite` (`idSpe`),
  ADD CONSTRAINT `besoinenpersonnel_ibfk_2` FOREIGN KEY (`idFonction`) REFERENCES `fonction` (`idFonction`),
  ADD CONSTRAINT `besoinenpersonnel_ibfk_3` FOREIGN KEY (`idCreneau`) REFERENCES `creneau` (`idCreneau`);

--
-- Contraintes pour la table `contraintedespe`
--
ALTER TABLE `contraintedespe`
  ADD CONSTRAINT `contraintedespe_ibfk_1` FOREIGN KEY (`idSpe`) REFERENCES `specialite` (`idSpe`);

--
-- Contraintes pour la table `estaffecte`
--
ALTER TABLE `estaffecte`
  ADD CONSTRAINT `estaffecte_ibfk_1` FOREIGN KEY (`idPersonnel`) REFERENCES `personnel` (`idPersonnel`),
  ADD CONSTRAINT `estaffecte_ibfk_2` FOREIGN KEY (`idBesoin`) REFERENCES `besoinenpersonnel` (`idBesoin`);

--
-- Contraintes pour la table `nonrespectlegal`
--
ALTER TABLE `nonrespectlegal`
  ADD CONSTRAINT `nonrespectlegal_ibfk_1` FOREIGN KEY (`idPersonnel`) REFERENCES `personnel` (`idPersonnel`),
  ADD CONSTRAINT `nonrespectlegal_ibfk_2` FOREIGN KEY (`idContrainteLegale`) REFERENCES `contraintelegale` (`idContrainteLegale`),
  ADD CONSTRAINT `nonrespectlegal_ibfk_3` FOREIGN KEY (`idBesoin`) REFERENCES `besoinenpersonnel` (`idBesoin`);

--
-- Contraintes pour la table `nonrespectspe`
--
ALTER TABLE `nonrespectspe`
  ADD CONSTRAINT `nonrespectspe_ibfk_1` FOREIGN KEY (`idPersonnel`) REFERENCES `personnel` (`idPersonnel`),
  ADD CONSTRAINT `nonrespectspe_ibfk_2` FOREIGN KEY (`idContrainteSpe`) REFERENCES `contraintedespe` (`idContrainteSpe`),
  ADD CONSTRAINT `nonrespectspe_ibfk_3` FOREIGN KEY (`idBesoin`) REFERENCES `besoinenpersonnel` (`idBesoin`);

--
-- Contraintes pour la table `personnel`
--
ALTER TABLE `personnel`
  ADD CONSTRAINT `personnel_ibfk_1` FOREIGN KEY (`idFonction`) REFERENCES `fonction` (`idFonction`);

--
-- Contraintes pour la table `sespecialise`
--
ALTER TABLE `sespecialise`
  ADD CONSTRAINT `sespecialise_ibfk_1` FOREIGN KEY (`idPersonnel`) REFERENCES `personnel` (`idPersonnel`),
  ADD CONSTRAINT `sespecialise_ibfk_2` FOREIGN KEY (`idSpe`) REFERENCES `specialite` (`idSpe`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
