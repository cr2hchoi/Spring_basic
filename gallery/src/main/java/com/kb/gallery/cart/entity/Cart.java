package com.kb.gallery.cart.entity;

import com.kb.gallery.cart.dto.CartRead;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter // ①
@Entity // ②
@Table(name = "carts") // ③
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ④

    @Column(nullable = false)
    private Integer memberId; // ⑤

    @Column(nullable = false)
    private Integer itemId; // ⑥

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created; // ⑦

    public Cart() { // ⑧
    }

    public Cart(Integer memberId, Integer itemId) { // ⑧
        this.memberId = memberId;
        this.itemId = itemId;
    }

    //엔티티 객체를 장바구니 조회 DTO로 변환하는 메서드
    //빌더를 활용해 필드값을 초기화하고 객체를 생성한다. 장바구니 서비스에서 데이터 조회시 사용
    public CartRead toRead(){
        return CartRead.builder()
                .id(id)
                .itemId(itemId)
                .build();
    }
}