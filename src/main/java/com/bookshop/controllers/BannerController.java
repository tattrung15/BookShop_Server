package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.constants.Common;
import com.bookshop.dao.Banner;
import com.bookshop.dto.BannerDTO;
import com.bookshop.dto.BannerStatusDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.FileHelper;
import com.bookshop.services.BannerService;
import com.bookshop.specifications.GenericSpecification;
import com.bookshop.specifications.SearchCriteria;
import com.bookshop.specifications.SearchOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/banners")
public class BannerController extends BaseController<Banner> {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<?> getListBanners(@RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "perPage", required = false) Integer perPage,
                                            @RequestParam(name = "fetchType", required = false) Integer fetchType,
                                            HttpServletRequest request) {
        GenericSpecification<Banner> specification = new GenericSpecification<Banner>().getBasicQuery(request);
        if (fetchType != null) {
            if (fetchType.equals(Common.FETCH_TYPE_USER)) {
                specification.add(new SearchCriteria("isActive", true, SearchOperation.EQUAL));
                List<Banner> banners = bannerService.findAll(specification);
                return this.resListSuccess(banners);
            } else if (fetchType.equals(Common.FETCH_TYPE_ADMIN)) {
                PaginateDTO<Banner> paginateBanners = bannerService.getList(page, perPage, specification);
                return this.resPagination(paginateBanners);
            }
        }

        PaginateDTO<Banner> paginateBanners = bannerService.getList(page, perPage, specification);
        return this.resPagination(paginateBanners);
    }

    @PostMapping
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> createBanner(@RequestBody @Valid BannerDTO bannerDTO) {
        GenericSpecification<Banner> specification = new GenericSpecification<>();
        specification.add(new SearchCriteria("title", bannerDTO.getTitle(), SearchOperation.EQUAL));
        Banner oldBanner = bannerService.findOne(specification);

        if (oldBanner != null) {
            throw new AppException("Banner has already exists");
        }

        Banner banner = bannerService.create(bannerDTO);
        return this.resSuccess(banner);
    }

    @PatchMapping("/{bannerId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateBanner(@RequestBody @Valid BannerDTO bannerDTO,
                                          @PathVariable("bannerId") Long bannerId) {
        Banner banner = bannerService.findById(bannerId);

        if (banner == null) {
            throw new NotFoundException("Not found banner");
        }

        Banner savedBanner = bannerService.update(bannerDTO, banner);
        return this.resSuccess(savedBanner);
    }

    @PatchMapping("/{bannerId}/status")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateBannerStatus(@RequestBody @Valid BannerStatusDTO bannerStatusDTO,
                                                @PathVariable("bannerId") Long bannerId) {
        Banner banner = bannerService.findById(bannerId);

        if (banner == null) {
            throw new NotFoundException("Not found banner");
        }

        if (bannerStatusDTO.getIsActive()) {
            if (banner.getImageUrl() == null) {
                throw new AppException("Cannot activate this banner");
            }
        }

        Banner savedBanner = bannerService.changeStatus(bannerId, bannerStatusDTO.getIsActive());
        return this.resSuccess(savedBanner);
    }

    @PutMapping("/{bannerId}/images")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> uploadBannerImage(@PathVariable("bannerId") Long bannerId,
                                               @RequestParam("file") MultipartFile file) {
        if (!FileHelper.isAllowImageType(file.getOriginalFilename())) {
            throw new AppException("This file type is not allowed");
        }

        Banner updatedBanner = bannerService.uploadImage(bannerId, file);
        return this.resSuccess(updatedBanner);
    }

    @DeleteMapping("/{bannerId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> deleteBanner(@PathVariable("bannerId") Long bannerId) {
        Banner banner = bannerService.findById(bannerId);

        if (banner == null) {
            throw new NotFoundException("Not found banner");
        }

        bannerService.deleteById(bannerId);
        return this.resSuccess(banner);
    }
}
