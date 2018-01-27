-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 27, 2018 at 01:48 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `take_order`
--

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `ORDER_ID` int(11) NOT NULL,
  `PRODUCT_ID` int(11) NOT NULL,
  `SL` int(11) NOT NULL,
  `AMOUNT` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `PRODUCT_ID` int(50) NOT NULL,
  `PRODUCT_NAME` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PRODUCT_PRICE` int(10) DEFAULT NULL,
  `PRODUCT_TYPE` int(11) DEFAULT NULL,
  `IMG` varchar(1000) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`PRODUCT_ID`, `PRODUCT_NAME`, `PRODUCT_PRICE`, `PRODUCT_TYPE`, `IMG`) VALUES
(1, '7Up lon', 10000, 1, 'https://storage.googleapis.com/zopnow-static/images/products/320/7-up-lemon-flavour-can-v-250-ml-2.png'),
(2, '7Up chai nhựa', 10000, 1, 'http://ttmart.tungphuong.net/wp-content/uploads/sites/133/2016/12/303aa81ebdb7c824ae63f00eaaf8cae7.jpg'),
(3, 'Redbull lon', 10000, 1, 'https://seller.lotte.vn/media/catalog/product/1/4/149_1505804817_redbull.jpg'),
(4, 'Cà phê đen', 10000, 1, 'http://www.baoxaydung.com.vn/stores/news_dataimages/hiep/072014/18/10/105548baoxaydung_b4.jpg'),
(5, 'Cà phê mang đi', 10000, 1, 'http://img.cdn2.vietnamnet.vn/Images/english/2013/08/06/11/20130806113000-coffee.jpg'),
(6, 'Cà phê sữa', 10000, 1, 'https://znews-photo-td.zadn.vn/w860/Uploaded/ngtmns/2016_05_23/3_3.jpg'),
(7, 'Coca chai thủy tinh', 10000, 1, 'https://upload.wikimedia.org/wikipedia/commons/8/87/CocaColaBottle_background_free.jpg'),
(8, 'Coca chai nhựa nhỏ', 10000, 1, 'https://cdn4.tgdd.vn/Products/Images/2443/76450/nuoc-ngot-coke-pet-390ml-700x467-1.jpg'),
(9, 'Coca chai nhựa lớn', 10000, 1, 'https://media.static-adayroi.com/sys_master/h70/h3c/12869284331550.jpg'),
(10, 'Coca lon', 10000, 1, 'https://cdn.tgdd.vn/Products/Images/2443/83757/nuoc-giai-khat-coca-cola-250ml-lon-do-1-1.jpg'),
(11, 'Dr.Thanh', 10000, 1, 'http://www.ori-mart.com/uploads/products/dr_thanh_khong_duong1.jpg'),
(12, 'Pepsi lon', 10000, 1, 'https://taphoahoanganh.com/wp-content/uploads/2017/04/pepsi-1.jpg'),
(13, 'Tăng lực No1 nhựa', 10000, 1, 'http://bysa.com.vn/ecp/upload/vi1.png'),
(14, 'Tăng lực No1 thủy tinh', 10000, 1, 'https://tradrthanh.com/wp-content/uploads/2016/11/number1.jpg'),
(15, 'Trà xanh 0', 10000, 1, 'https://coopmart.vn/Data/Sites/1/Product/6020/3100261_2.jpg'),
(16, 'Mì Trứng', 10000, 2, 'https://ameovat.com/wp-content/uploads/2016/05/7.jpg'),
(17, 'Mì bò', 10000, 2, 'https://cdn3.ivivu.com/2017/07/mi-bo-dai-loan-noi-am-anh-cua-thuc-khach-yeu-do-an-ivivu-1.jpg'),
(18, 'Hủ tíu', 10000, 2, 'https://media.foody.vn/res/g5/46076/prof/s576x330/foody-mobile-hu-tieu-nam-vang-bayon-khanh-hoa-131217101338.jpg'),
(19, '123', 123, 1, '123');

-- --------------------------------------------------------

--
-- Table structure for table `product_type`
--

CREATE TABLE `product_type` (
  `TYPE_ID` int(11) NOT NULL,
  `NAME` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IMG` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product_type`
--

INSERT INTO `product_type` (`TYPE_ID`, `NAME`, `IMG`) VALUES
(1, 'Thức uống', 'https://images-na.ssl-images-amazon.com/images/I/511rL-aYd9L._SL1000_.jpg'),
(2, 'Thức ăn', 'https://media.foody.vn/res/g5/46076/prof/s576x330/foody-mobile-hu-tieu-nam-vang-bayon-khanh-hoa-131217101338.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `sale_order`
--

CREATE TABLE `sale_order` (
  `ID` int(11) NOT NULL,
  `TABLE_ID` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `KHACHHANG` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `TONGCONG` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `NOIDUNG` varchar(2000) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `sale_order`
--

INSERT INTO `sale_order` (`ID`, `TABLE_ID`, `KHACHHANG`, `TONGCONG`, `NOIDUNG`) VALUES
(1, 'Table 1', 'Le Vo Quang Vinh', 'Tổng cộng: 40000 VND', '7Up lon. . . . .10000 VND *1\n7Up chai nhựa. . . . .10000 VND *1\nCà phê đen. . . . .10000 VND *1\nRedbull lon. . . . .10000 VND *1'),
(2, 'Table 2', 'Tran Minh Man', 'Tổng cộng: 40000 VND', '7Up lon. . . . .10000 VND *1\n7Up chai nhựa. . . . .10000 VND *1\nRedbull lon. . . . .10000 VND *1\nCà phê đen. . . . .10000 VND *1'),
(3, 'Table 5', 'Tran Duc Thuat', 'Tổng cộng: 40000 VND', '7Up lon. . . . .10000 VND *1\n7Up chai nhựa. . . . .10000 VND *1\nCà phê đen. . . . .10000 VND *1\nRedbull lon. . . . .10000 VND *1'),
(4, 'Table 4', 'Le Quoc Thong', 'Tổng cộng: 40000 VND', '7Up lon. . . . .10000 VND *1\nCà phê đen. . . . .10000 VND *1\nCà phê sữa. . . . .10000 VND *1\nCà phê mang đi. . . . .10000 VND *1'),
(5, 'Table 1', 'Anh Khoa', 'Tổng cộng: 110000 VND', '7Up lon. . . . .10000 VND *7\nRedbull lon. . . . .10000 VND *1\nCà phê mang đi. . . . .10000 VND *1\n7Up chai nhựa. . . . .10000 VND *1\nCà phê đen. . . . .10000 VND *1');

-- --------------------------------------------------------

--
-- Table structure for table `table_`
--

CREATE TABLE `table_` (
  `TABLE_ID` int(20) NOT NULL,
  `TABLE_NAME` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `table_`
--

INSERT INTO `table_` (`TABLE_ID`, `TABLE_NAME`) VALUES
(1, 'Table 1'),
(2, 'Table 2'),
(3, 'Table 3'),
(4, 'Table 4'),
(5, 'Table 5');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`PRODUCT_ID`),
  ADD KEY `fk_product_type` (`PRODUCT_TYPE`);

--
-- Indexes for table `product_type`
--
ALTER TABLE `product_type`
  ADD PRIMARY KEY (`TYPE_ID`);

--
-- Indexes for table `table_`
--
ALTER TABLE `table_`
  ADD PRIMARY KEY (`TABLE_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_product_type` FOREIGN KEY (`PRODUCT_TYPE`) REFERENCES `product_type` (`TYPE_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
