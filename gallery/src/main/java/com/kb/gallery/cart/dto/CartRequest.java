package com.kb.gallery.cart.dto;

import com.kb.gallery.cart.entity.Cart;
import lombok.Getter;

//CartRequest : 장바구니에 데이터 입력을 요청시 사용
@Getter // ①
public class CartRequest {

    private Integer itemId; // ②

    // 엔티티 객체로 변환 : 장바구니 담기 요청 DTO를 엔티티 객체로 변환하는 메서드
    // 회원아이디를 받아 이 회원 아이디와 상품 아이디 필드 값을 인수로 사용하여 장바구니
    // 엔티티 객체를 생성하고 이를 리턴한다. 장바구니 서비스에서 데이터를 저장 시 사용
    public Cart toEntity(Integer memberId) { // ③
        return new Cart(memberId, itemId);
    }
}
