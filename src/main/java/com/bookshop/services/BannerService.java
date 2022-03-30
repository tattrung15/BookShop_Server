package com.bookshop.services;

import com.bookshop.dao.Banner;
import com.bookshop.dto.BannerDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {
    List<Banner> findAll(GenericSpecification<Banner> specification);

    Banner findById(Long bannerId);

    Banner findOne(GenericSpecification<Banner> specification);

    Banner create(BannerDTO bannerDTO);

    Banner update(BannerDTO bannerDTO, Banner currentBanner);

    Banner uploadImage(Long bannerId, MultipartFile file);

    Banner changeStatus(Long bannerId, Boolean isActive);

    void deleteById(Long bannerId);

    PaginateDTO<Banner> getList(Integer page, Integer perPage, GenericSpecification<Banner> specification);
}
