package com.kb.gallery.item.entity;

import com.kb.gallery.item.dto.ItemRead;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;



@Getter
@Entity //JPA에 의해 관리되는 엔티티임을 나타내는 어노테이션
@Table(name="items") // 매핑된 테이블의 정보를 나타냄 데이터 테이블을 자동으로 관리 해줌
//item VO 객체
public class Item {

    @Id //데이터 베이스의 고유값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터테이블의 설정 기준을 따르겠다 (오토 increment)
    private Integer id;
    @Column(length = 50, nullable = false) //열 속성 지정
    private String name;
    @Column(length = 50, nullable = false)
    private String img_path;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer discount_per;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp // 데이터 삽입 시 값이 없다면 현재 시간을 적용하여저장
    private LocalDateTime created;


    // 상품조회 DTO 변환
    public ItemRead toRead(){
        return ItemRead.builder()
                .id(id)
                .name(name)
                .img_path(img_path)
                .price(price)
                .discount_per(discount_per)
                .build();
    }
}
