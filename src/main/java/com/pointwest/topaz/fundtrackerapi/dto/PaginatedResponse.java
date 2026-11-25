package com.pointwest.topaz.fundtrackerapi.dto;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> content,
        int currentPage,
        int totalPages,
        int elementsPerPage
) {
}
