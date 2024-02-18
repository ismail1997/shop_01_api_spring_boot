package com.ismail.shop.dtos;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UserPageDTO {
    private int currentPage;
    private int totalPages;
    private int pageSize;

    private List<UserDTO> userDTOS;
}
