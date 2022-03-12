package com.bookshop.services.impl;

import com.bookshop.constants.Common;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.helpers.FileHelper;
import com.bookshop.repositories.ProductImageRepository;
import com.bookshop.services.ProductImageService;
import com.bookshop.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public void deleteByProductId(Long productId) {
        productImageRepository.deleteByProductId(productId);
    }

    @Override
    public List<ProductImage> createOrUpdateMany(Product product, MultipartFile[] files) {
        this.deleteByProductId(product.getId());
        storageService.deleteFilesByPrefix(String.valueOf(product.getId()), Common.PRODUCT_IMAGE_UPLOAD_PATH);
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String randomUniqueFileName = FileHelper.randomUniqueFileName(product.getId() + "-" + file.getOriginalFilename());
            String imageUrl = storageService.store(
                    Common.PRODUCT_IMAGE_UPLOAD_PATH,
                    file,
                    randomUniqueFileName
            );
            ProductImage productImage = new ProductImage(null, imageUrl, randomUniqueFileName, product, null, null);
            ProductImage newProductImage = productImageRepository.save(productImage);
            productImages.add(newProductImage);
        }
        return productImages;
    }
}
