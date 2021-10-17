package com.bookshop.base;

import com.bookshop.constants.ResponseMessageEnum;
import com.bookshop.dto.ResponseDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.dto.pagination.PaginationDTO;
import com.bookshop.dto.pagination.PaginationResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BaseController<T> {
    public ResponseEntity<?> resSuccess(T data) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO<>(HttpStatus.OK.value(), ResponseMessageEnum.SUCCESS, data)
        );
    }

    public ResponseEntity<?> resListSuccess(List<T> data) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO<>(HttpStatus.OK.value(), ResponseMessageEnum.SUCCESS, data)
        );
    }

    public ResponseEntity<?> resPagination(PaginateDTO<T> paginateDTO) {
        PaginationDTO<List<T>> paginationDTO = new PaginationDTO<>(
                paginateDTO.getPageData().getContent(),
                paginateDTO.getPagination()
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new PaginationResponseDTO<>(HttpStatus.OK.value(), ResponseMessageEnum.SUCCESS, paginationDTO)
        );
    }
}
