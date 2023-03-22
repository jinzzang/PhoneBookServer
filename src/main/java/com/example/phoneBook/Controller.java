package com.example.phoneBook;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private ArrayList<Contact> pn = new ArrayList<>();

    @GetMapping("/")
    public String view() {
        return pn.toString();
    }

    @GetMapping("/add")
    public String add(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "number") String number,
        @RequestParam(name = "address", required = false) String address,
        @RequestParam(name = "nickName", required = false) String nickName
    ) {
        final var num = new Contact(name, number, Optional.ofNullable(address), Optional.ofNullable(nickName)); 
        pn.add(num);
        return num + " 이(가) 저장되었습니다.";
    }

    @GetMapping("/search")
    public String search(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "number", required = false) String number
    ) {
        final var n = Optional.ofNullable(name);
        final var num = Optional.ofNullable(number);

        final var p = 2 * (n.isPresent() ? 1 : 0) + (num.isPresent() ? 1 : 0);

        switch (p) {
            case 0b00: throw new IllegalArgumentException("검색할 이름 또는 전화번호를 입력하지 않았습니다.");
            case 0b01: return filter(c -> c.getNumber().equals(number), pn).toString();
            case 0b10: return filter(c -> c.getName().equals(name), pn).toString();
            case 0b11: 
                var n_ = filter(c -> c.getName().equals(name) && c.getNumber().equals(number), pn);
                return n_.toString();
            default: throw new UnsupportedOperationException("있을 수 없는 경우입니다.");
        }
    }

    @GetMapping("/deleteAll")
    public String del_(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "number", required = false) String number
    ) {
        final var n = Optional.ofNullable(name);
        final var num = Optional.ofNullable(number);

        final var p = 2 * (n.isPresent() ? 1 : 0 ) + (num.isPresent() ? 1 : 0);

        switch (p) {
            case 0b00: throw new IllegalArgumentException("삭제할 이름 또는 번호를 입력하지 않았습니다.");
            case 0b01: 
                final var n_ = filter(c -> c.getNumber().equals(number), pn);
                pn = filter(c -> !c.getNumber().equals(number), pn);
                return n_.get(0) + " 이(가) 삭제되었습니다.";
            case 0b10: 
                final var n__ = filter(c -> c.getName().equals(name), pn);
                pn = filter(c -> !c.getName().equals(name), pn);
                return n__.get(0) + " 이(가) 삭제되었습니다.";
            case 0b11: 
                pn = filter(c -> !c.getName().equals(name) && !c.getNumber().equals(number), pn);
                return name + " : " + number + " 이(가) 삭제되었습니다.";
        
            default: throw new UnsupportedOperationException();
        }
    }

    private static <T> ArrayList<T> filter(Function<T, Boolean> p, ArrayList<T> list) {
        if(list.isEmpty()) return new ArrayList<T>();
        else {
            var n = list.get(0);
            var l = new ArrayList<>(list.subList(1, list.size()));
            
            var result = filter(p, l);
            if(p.apply(n)) {
                result.add(0, n);
            } return result;
        } 
    }
}

/*
전화번호 등록
검색
삭제
 */