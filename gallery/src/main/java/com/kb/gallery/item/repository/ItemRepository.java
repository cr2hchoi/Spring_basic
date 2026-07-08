package com.kb.gallery.item.repository;

import com.kb.gallery.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Itme들을 관리하는 저장 공간. Integer타입인 id값을 사용해 구분
//JpaRepository에서 제공하는 기능들을 확장해서 Item 전용 저장 공간 생성
public interface ItemRepository extends JpaRepository<Item,Integer> {

    //jpa의 findAllById 메서드 오버라이딩
    List<Item> findAllByIdIn(List<Integer> ids);
}
