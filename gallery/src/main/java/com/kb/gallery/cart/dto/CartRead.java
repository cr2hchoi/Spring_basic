package com.kb.gallery.cart.dto;

//CartRead : 장바구니 조회 DTO

import lombok.Builder;
import lombok.Getter;

@Getter // ①
@Builder  // ②
public class CartRead {

    private Integer id; // ③
    private Integer itemId; // ④


}
