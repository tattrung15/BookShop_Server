package com.bookshop.helpers;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

import com.bookshop.dao.Category;
import com.bookshop.dao.Product;
import com.bookshop.dao.User;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.UserDTO;

public class ConvertObject {

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");

    private static final Pattern WHITE_SPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        input = input.trim().replaceAll("\\s+", " ");
        String noWhiteSpace = WHITE_SPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
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
