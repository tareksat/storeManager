-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 16, 2016 at 12:15 PM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `store`
--

-- --------------------------------------------------------

--
-- Stand-in structure for view `advancedsearch`
--
DROP VIEW IF EXISTS `advancedsearch`;
CREATE TABLE IF NOT EXISTS `advancedsearch` (
`customers_id` int(11)
,`name` varchar(100)
,`item_id` int(11)
,`item_name` varchar(100)
,`qty` int(11)
,`bill_id` int(11)
,`number` varchar(100)
,`date` date
);

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE IF NOT EXISTS `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `customer_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `total_price` float NOT NULL DEFAULT '0',
  `discount` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`id`, `number`, `customer_id`, `date`, `total_price`, `discount`) VALUES
(12, '1', 9, '2016-12-14', 2499, 0),
(13, '11qq', 8, '2016-12-16', 2499, 0);

-- --------------------------------------------------------

--
-- Table structure for table `bill_details`
--

DROP TABLE IF EXISTS `bill_details`;
CREATE TABLE IF NOT EXISTS `bill_details` (
  `bill_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `bill_details`
--

INSERT INTO `bill_details` (`bill_id`, `item_id`, `qty`) VALUES
(12, 5, 1),
(13, 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `name`, `phone`, `email`, `address`) VALUES
(8, 'Tarek Morshed', '01001860957', 'Tarek.morshid@gmail.com', '8 Abdurrahman elsayed street Elmaai , Cairo , Egypt'),
(9, 'Omar Tarek', '01275036660', 'Omar.Tarek@gmail.com', 'Cairo, Egypt');

-- --------------------------------------------------------

--
-- Stand-in structure for view `customerssearch`
--
DROP VIEW IF EXISTS `customerssearch`;
CREATE TABLE IF NOT EXISTS `customerssearch` (
`customer_id` int(11)
,`name` varchar(100)
,`bill_id` int(11)
,`number` varchar(100)
,`date` date
,`total_price` float
,`discount` float
);

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
CREATE TABLE IF NOT EXISTS `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `price` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`id`, `name`, `price`) VALUES
(5, 'Mac book pro', 2499),
(6, 'HP', 999);

-- --------------------------------------------------------

--
-- Structure for view `advancedsearch`
--
DROP TABLE IF EXISTS `advancedsearch`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `advancedsearch`  AS  select `customerssearch`.`customer_id` AS `customers_id`,`customerssearch`.`name` AS `name`,`items`.`id` AS `item_id`,`items`.`name` AS `item_name`,`bill_details`.`qty` AS `qty`,`customerssearch`.`bill_id` AS `bill_id`,`customerssearch`.`number` AS `number`,`customerssearch`.`date` AS `date` from ((`customerssearch` join `bill_details` on((`customerssearch`.`bill_id` = `bill_details`.`bill_id`))) join `items` on((`bill_details`.`item_id` = `items`.`id`))) ;

-- --------------------------------------------------------

--
-- Structure for view `customerssearch`
--
DROP TABLE IF EXISTS `customerssearch`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `customerssearch`  AS  select `customers`.`id` AS `customer_id`,`customers`.`name` AS `name`,`bill`.`id` AS `bill_id`,`bill`.`number` AS `number`,`bill`.`date` AS `date`,`bill`.`total_price` AS `total_price`,`bill`.`discount` AS `discount` from (`customers` join `bill` on((`customers`.`id` = `bill`.`customer_id`))) ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
