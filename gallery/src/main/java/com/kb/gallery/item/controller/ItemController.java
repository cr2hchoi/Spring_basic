package com.kb.gallery.item.controller;

import com.kb.gallery.item.dto.ItemRead;
import com.kb.gallery.item.service.ItemService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService; //아이템 목록 전달 받기 위한 생성자 주입

    @GetMapping("/api/items")
    //ResponsEntity<?> 타입으로 받아야 json객체로 데이터 받을 수 이뜸
    public ResponseEntity<?> readAll(){
         List<ItemRead> items = itemService.findAll();
         return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
