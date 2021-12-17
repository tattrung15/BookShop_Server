package com.bookshop.base;

import com.bookshop.constants.Common;
import com.bookshop.dto.ResponseDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.dto.pagination.PaginationDTO;
import com.bookshop.dto.pagination.PaginationResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController<T> {
    public ResponseEntity<?> resSuccess(T data) {
        Map<String, T> map = new HashMap<>();
        map.put("data", data);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO<>(HttpStatus.OK.value(), Common.RESPONSE_MESSAGE_SUCCESS, map)
        );
    }

    public ResponseEntity<?> resListSuccess(List<?> data) {
        Map<String, List<?>> map = new HashMap<>();
        map.put("data", data);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO<>(HttpStatus.OK.value(), Common.RESPONSE_MESSAGE_SUCCESS, map)
        );
    }

    public ResponseEntity<?> resPagination(PaginateDTO<?> paginateDTO) {
        PaginationDTO<List<?>> paginationDTO = new PaginationDTO<>(
                paginateDTO.getPageData().getContent(),
                paginateDTO.getPagination()
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new PaginationResponseDTO<>(HttpStatus.OK.value(), Common.RESPONSE_MESSAGE_SUCCESS, paginationDTO)
        );
    }
}
