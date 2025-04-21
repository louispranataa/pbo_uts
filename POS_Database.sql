-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for pos_db
-- CREATE DATABASE IF NOT EXISTS `pos_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pos_db`;


-- Dumping structure for table pos_db.produk
CREATE TABLE IF NOT EXISTS `produk` (
  `id` char(7) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `harga` double NOT NULL,
  `jenis_produk` enum('Tidak Kedaluwarsa','Kedaluwarsa','Paket','Digital') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tanggal_kedaluwarsa` date DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `nama_vendor` varchar(100) DEFAULT NULL,
  `paket` tinyint(1) DEFAULT '0',
  `diskon` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Dumping structure for table pos_db.transaksi
CREATE TABLE IF NOT EXISTS `transaksi` (
  `id` char(8) NOT NULL,
  `tanggal` datetime NOT NULL,
  `jenis_transaksi` enum('pembelian','retur') NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping structure for table pos_db.detail_transaksi
CREATE TABLE IF NOT EXISTS `detail_transaksi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_transaksi` char(8) NOT NULL,
  `id_produk` char(7) NOT NULL,
  `jumlah` int NOT NULL,
  `sub_total` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_detail_transaksi_transaksi` (`id_transaksi`),
  KEY `FK_detail_transaksi_produk` (`id_produk`),
  CONSTRAINT `FK_detail_transaksi_produk` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_detail_transaksi_transaksi` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping structure for table pos_db.isi_paket
CREATE TABLE IF NOT EXISTS `isi_paket` (
  `id_paket` char(7) NOT NULL,
  `id_produk` char(7) NOT NULL,
  KEY `FK__produk` (`id_paket`),
  KEY `FK__produk_2` (`id_produk`),
  CONSTRAINT `FK__produk` FOREIGN KEY (`id_paket`) REFERENCES `produk` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK__produk_2` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping structure for table pos_db.log_aktivitas
CREATE TABLE IF NOT EXISTS `log_aktivitas` (
  `id_log` int NOT NULL AUTO_INCREMENT,
  `waktu` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `aksi` varchar(100) DEFAULT NULL,
  `keterangan` text,
  PRIMARY KEY (`id_log`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping structure for table pos_db.pengguna
CREATE TABLE IF NOT EXISTS `pengguna` (
  `id` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(64) NOT NULL,
  `nama_pengguna` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table pos_db.pengguna: ~4 rows (approximately)
INSERT INTO `pengguna` (`id`, `username`, `password`, `nama_pengguna`) VALUES
	('P-001', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Admin POS');


/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
