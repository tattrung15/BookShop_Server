package com.bookshop.helpers;

import com.bookshop.dao.Category;
import com.bookshop.dao.Product;
import com.bookshop.dao.User;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.UserDTO;

public class ConvertObject {

	public static String toSlug(String input) {
		return input
                .toLowerCase()
                .replaceAll("[á|à|ả|ạ|ã|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ|ä|å|æ|ą]", "a")
                .replaceAll("[ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ|ö|ô|œ|ø]", "o")
                .replaceAll("[é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ|ě|ė|ë|ę]", "e")
                .replaceAll("[ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự]", "u")
                .replaceAll("[i|í|ì|ỉ|ĩ|ị|ï|î|į]", "i")
                .replaceAll("[ù|ú|ü|û|ǘ|ů|ű|ū|ų]", "u")
                .replaceAll("[ß|ş|ś|š|ș]", "s")
                .replaceAll("[ź|ž|ż]", "z")
                .replaceAll("[ý|ỳ|ỷ|ỹ|ỵ|ÿ|ý]", "y")
                .replaceAll("[ǹ|ń|ň|ñ]", "n")
                .replaceAll("[ç|ć|č]", "c")
                .replaceAll("[ğ|ǵ]", "g")
                .replaceAll("[ŕ|ř]", "r")
                .replaceAll("[·|/|_|,|:|;]", "-")
                .replaceAll("[ť|ț]", "t")
                .replaceAll("ḧ", "h")
                .replaceAll("ẍ", "x")
                .replaceAll("ẃ", "w")
                .replaceAll("ḿ", "m")
                .replaceAll("ṕ", "p")
                .replaceAll("ł", "l")
                .replaceAll("đ", "d")
                .replaceAll("\\s+", "-")
                .replaceAll("&", "-and-")
                .replaceAll("[^\\w\\-]+", "")
                .replaceAll("\\-\\-+", "-")
                .replaceAll("^-+", "")
                .replaceAll("-+$", "");
	}

	public static User fromUserDTOToUserDAO(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.getFirstName().trim().replaceAll("\\s+", " "));
		user.setLastName(userDTO.getLastName().trim().replaceAll("\\s+", " "));
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setAddress(userDTO.getAddress().trim().replaceAll("\\s+", " "));
		user.setAmount(userDTO.getAmount());
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		user.setRole(userDTO.getRole());
		return user;
	}

	public static UserDTO fromUserDAOToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setUsername(user.getUsername());
		userDTO.setAddress(user.getAddress());
		userDTO.setAmount(user.getAmount());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhone(user.getPhone());
		userDTO.setRole(user.getRole());
		return userDTO;
	}

	public static Category fromCategoryDTOToCategoryDAO(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName().trim().replaceAll("\\s+", " "));
		category.setDescription(categoryDTO.getDescription().trim().replaceAll("\\s+", " "));
		category.setSlug(toSlug(categoryDTO.getName()));
		return category;
	}

	public static Product fromProductDTOToProductDAO(ProductDTO productDTO) {
		Product product = new Product();
		product.setTitle(productDTO.getTitle().trim().replaceAll("\\s+", " "));
		product.setLongDescription(productDTO.getLongDescription().trim());
		product.setShortDescription(product.getLongDescription().substring(0, 60));
		product.setPrice(productDTO.getPrice());
		product.setAuthor(productDTO.getAuthor().trim().replaceAll("\\s+", " "));
		product.setCurrentNumber(productDTO.getCurrentNumber());
		product.setNumberOfPage(productDTO.getNumberOfPage());
		product.setSlug(toSlug(productDTO.getTitle()));
		return product;
	}
}
