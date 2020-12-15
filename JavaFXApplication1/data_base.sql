-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le :  lun. 14 déc. 2020 à 20:15
-- Version du serveur :  8.0.18
-- Version de PHP :  7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `movie`
--

-- --------------------------------------------------------

--
-- Structure de la table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL,
  `id_session` int(11) NOT NULL,
  `date` date NOT NULL,
  `mail` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_session` (`id_session`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `customer`
--

INSERT INTO `customer` (`id`, `id_session`, `date`, `mail`) VALUES
(0, 100, '2020-12-04', 'clemfages@gmail.com'),
(1, 10, '2020-11-26', 'pasAbo@gmail.com'),
(2, 4, '2020-11-18', 'thomas.grisez'),
(4, 1, '2020-11-22', 'auhasard@gmail.com'),
(5, 4, '2020-11-21', 'eys@ggg'),
(6, 4, '2020-12-02', 'clement.fages'),
(7, 2, '2020-11-21', 'thomas.grisez'),
(8, 1, '2020-11-26', 'noMailForThisUser@error'),
(9, 100, '2020-12-04', 'clemfages@gmail.com'),
(10, 100, '2020-12-04', 'clemfages@gmail.com'),
(11, 14, '2020-12-06', 'noMailForThisUser@error'),
(14, 9, '2020-12-04', 'romano.fages@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `login` varchar(30) NOT NULL,
  `motDePasse` varchar(30) NOT NULL,
  `firstName` varchar(15) NOT NULL,
  `lastName` varchar(15) NOT NULL,
  `cle_access` varchar(1) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `employee`
--

INSERT INTO `employee` (`login`, `motDePasse`, `firstName`, `lastName`, `cle_access`) VALUES
('adrien.herbourg', 'rgez', 'adrien', 'herbourg', 'B'),
('clement.fgs', 'clementfgs', 'clement', 'fages', 'A'),
('clement1.fgs', 'nouveau', 'clement', 'fgs', 'B'),
('julien.hu', 'mdp', 'julien', 'hu', 'C');

-- --------------------------------------------------------

--
-- Structure de la table `membre`
--

DROP TABLE IF EXISTS `membre`;
CREATE TABLE IF NOT EXISTS `membre` (
  `login` varchar(50) NOT NULL,
  `mot_de_passe` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `membre`
--

INSERT INTO `membre` (`login`, `mot_de_passe`, `first_name`, `last_name`, `age`) VALUES
('antoine.villier', 'azerty', 'antoine', 'villier', 78),
('clement.fages', 'mdp', 'clement', 'fages', 20),
('harry.potter', 'abcd', 'harry', 'potter', 12),
('mai.neff', 'tyty', 'mai', 'neff', 21),
('romain.fages', 'starship', 'romain', 'fages', 18),
('thomas.grisez', 'abc', 'thomas', 'grisez', 25),
('thomas1.grisez', 'azer', 'thomas', 'grisez', 12);

-- --------------------------------------------------------

--
-- Structure de la table `movie`
--

DROP TABLE IF EXISTS `movie`;
CREATE TABLE IF NOT EXISTS `movie` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `titre` varchar(50) NOT NULL,
  `auteur` varchar(30) NOT NULL,
  `genre` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `runningTime` int(11) NOT NULL,
  `note` int(11) NOT NULL,
  `description` varchar(800) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10004 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `movie`
--

INSERT INTO `movie` (`id`, `titre`, `auteur`, `genre`, `date`, `runningTime`, `note`, `description`) VALUES
(1, 'blanche neige', 'Disne', 'fantastic', '2020-11-01', 120, 3, 'Blanche Neige is a very pretty princess. Her mother-in-law, the Queen, feeling that one day the beauty of Snow White would surpass hers, ordered her hunting guard to kill her. Not having the courage to carry out his orders, he abandoned him in the forest.'),
(2, 'shrek', 'Andrew Adamson', 'comedia', '2000-10-10', 115, 1, 'Shrek, a greenish ogre, discovers annoying little creatures roaming his swamp. Shrek then travels to the castle of Lord Lord Farquaad, who supposedly expelled these beings from his kingdom. The latter wishes to marry Princess Fiona, but she is held prisoner by an abominable dragon. He needs a knight brave enough to rescue the beauty. Shrek agrees to accomplish this mission.'),
(3, 'inception', 'Christopher Nolan', 'action', '2020-11-26', 160, 4, 'Dom Cobb is an experienced thief in the perilous art of \'extraction\': his specialty is to appropriate an individual\'s most precious secrets, buried deep in his subconscious, while he dreams and that his mind is particularly vulnerable. Highly sought after for his talents in the murky world of industrial espionage, Cobb has also become a fugitive hunted around the world. However, a final mission could allow him to regain his previous life.'),
(4, 'forrest gump', 'Robert Zemeckis', 'comedia', '2020-09-08', 123, 9, 'Over the course of the various interlocutors who come to sit in turn next to him on a bench, Forrest Gump tells the fabulous story of his life. His life is like a feather that lets itself be carried by the wind, just as Forrest lets himself be carried by the events he goes through in America in the second half of the 20th century.'),
(5, 'le parrain', 'Francis Ford Coppola', 'action', '2020-11-24', 140, 7, 'In 1945, in New York, the Corleones were one of the five mafia families. Don Vito Corleone, \'godfather\' of this family, married his daughter to a bookmaker. Sollozzo, \'godfather\' of the Tattaglia family, offers Don Vito an association in drug trafficking, but he refuses. Sonny, one of his sons, is in favor of it. In order to deal with Sonny, Sollozzo tries to have Don Vito killed, but he escapes.'),
(6, 'your name', 'Makoto Shinkai', 'horror', '2020-01-08', 193, 4, 'Mitsuha, a teenager stuck in a traditional family, dreams of leaving her native mountains to discover the hectic life of Tokyo. She is far from imagining being able to live the urban adventure in the skin of ... Taki, a young high school student living in Tokyo. Through her dreams, Mitsuha literally sees herself propelled into the young boy\'s life. What mystery is hidden behind these strange dreams that unite two opposing destinies that have never met?'),
(8, 'the dark knight', 'Christopher Nolan', 'action', '2020-11-03', 135, 10, 'Batman is more determined than ever to eradicate the organized crime that is spreading terror in the city. Supported by Lieutenant Jim Gordon and the attorney of Gotham City, Harvey Dent, Batman sees his field of action widen. The collaboration of the three men proves to be very effective and does not take long to bear fruit until a formidable criminal comes to plunge the city of Gotham City into chaos.');

-- --------------------------------------------------------

--
-- Structure de la table `reduction`
--

DROP TABLE IF EXISTS `reduction`;
CREATE TABLE IF NOT EXISTS `reduction` (
  `id` varchar(60) NOT NULL,
  `promo` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `reduction`
--

INSERT INTO `reduction` (`id`, `promo`) VALUES
('regular', 0.21),
('senior', 0.12),
('children', 0.25);

-- --------------------------------------------------------

--
-- Structure de la table `session`
--

DROP TABLE IF EXISTS `session`;
CREATE TABLE IF NOT EXISTS `session` (
  `id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `max_place` int(11) NOT NULL,
  `heure` varchar(5) NOT NULL,
  `actual_place` int(11) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `movie_id` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `session`
--

INSERT INTO `session` (`id`, `movie_id`, `date`, `max_place`, `heure`, `actual_place`, `amount`) VALUES
(1, 1, '2020-12-15', 70, '17h10', 70, 498.8),
(2, 1, '2020-12-16', 130, '18h30', 64, 517.6),
(3, 1, '2020-12-16', 18, '15h15', 20, 95),
(4, 1, '2021-01-08', 180, '22h05', 107, 763),
(5, 2, '2020-12-19', 120, '10h43', 12, 48),
(9, 2, '2020-12-24', 98, '22h10', 5, 759.6),
(10, 3, '2020-12-23', 135, '21h15', 90, 740.16),
(11, 5, '2020-12-22', 200, '23h', 67, 1278),
(12, 6, '2020-12-21', 65, '22h20', 36, 489),
(13, 8, '2020-12-20', 38, '15h20', 30, 343),
(14, 6, '2020-12-19', 122, '18h15', 105, 1021.16),
(15, 5, '2020-12-18', 99, '9h10', 95, 760),
(16, 8, '2020-12-17', 105, '17h17', 54, 908),
(18, 4, '2020-12-16', 125, '17h18', 0, 0),
(100, 2, '2000-12-30', 12, '12h12', 4, 32);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`id_session`) REFERENCES `session` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
