package com.kb.gallery.item.service;

import com.kb.gallery.item.dto.ItemRead;
import java.util.List;

public interface ItemService {

    // 전체 상품 목록 반환 메서드
    List<ItemRead> findAll();

    //상품 목록 중 특정 id로 리스틑 조회 메서드
    List<ItemRead> findAll(List<Integer> ids);
}