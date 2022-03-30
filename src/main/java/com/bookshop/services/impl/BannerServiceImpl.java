package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.constants.Common;
import com.bookshop.dao.Banner;
import com.bookshop.dto.BannerDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.FileHelper;
import com.bookshop.repositories.BannerRepository;
import com.bookshop.services.BannerService;
import com.bookshop.services.StorageService;
import com.bookshop.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BannerServiceImpl extends BasePagination<Banner, BannerRepository> implements BannerService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StorageService storageService;

    @Autowired
    private BannerRepository bannerRepository;

    public BannerServiceImpl(BannerRepository bannerRepository) {
        super(bannerRepository);
    }

    @Override
    public List<Banner> findAll(GenericSpecification<Banner> specification) {
        return bannerRepository.findAll(specification);
    }

    @Override
    public Banner findById(Long bannerId) {
        return bannerRepository.findById(bannerId).orElse(null);
    }

    @Override
    public Banner findOne(GenericSpecification<Banner> specification) {
        return bannerRepository.findOne(specification).orElse(null);
    }

    @Override
    public Banner create(BannerDTO bannerDTO) {
        Banner banner = mapper.map(bannerDTO, Banner.class);
        return bannerRepository.save(banner);
    }

    @Override
    public Banner update(BannerDTO bannerDTO, Banner currentBanner) {
        Banner updated = mapper.map(bannerDTO, Banner.class);
        mapper.map(updated, currentBanner);
        return bannerRepository.save(currentBanner);
    }

    @Override
    public Banner uploadImage(Long bannerId, MultipartFile file) {
        Banner banner = this.findById(bannerId);
        if (banner == null) {
            throw new NotFoundException("Not found banner");
        }
        storageService.deleteFilesByPrefix(String.valueOf(bannerId), Common.BANNER_IMAGE_UPLOAD_PATH);
        String randomUniqueFileName = FileHelper.randomUniqueFileName(bannerId + "-" + file.getOriginalFilename());
        String imageUrl = storageService.store(Common.BANNER_IMAGE_UPLOAD_PATH, file, randomUniqueFileName);
        banner.setImageUrl(imageUrl);
        return bannerRepository.save(banner);
    }

    @Override
    public Banner changeStatus(Long bannerId, Boolean isActive) {
        Banner banner = this.findById(bannerId);
        if (banner == null) {
            throw new NotFoundException("Not found banner");
        }
        banner.setIsActive(isActive);
        return bannerRepository.save(banner);
    }

    @Override
    public void deleteById(Long bannerId) {
        bannerRepository.deleteById(bannerId);
        storageService.deleteFilesByPrefix(String.valueOf(bannerId), Common.BANNER_IMAGE_UPLOAD_PATH);
    }

    @Override
    public PaginateDTO<Banner> getList(Integer page, Integer perPage, GenericSpecification<Banner> specification) {
        return this.paginate(page, perPage, specification);
    }
}
