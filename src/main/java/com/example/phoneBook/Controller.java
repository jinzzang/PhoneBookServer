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

        if(n.isPresent() && num.isPresent()) {
            var n_ = filter(c -> c.getName().equals(name) && c.getNumber().equals(number), pn);
            return n_.toString();
        } else if(n.isPresent() && !num.isPresent()) {
            return filter(c -> c.getName().equals(name), pn).toString();
        } else if(!n.isPresent() && num.isPresent()) {
            return filter(c -> c.getNumber().equals(number), pn).toString();
        } else throw new IllegalArgumentException("검색할 이름 또는 전화번호를 입력하지 않았습니다.");
    }

    @GetMapping("/deleteAll")
    public String del_(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "number", required = false) String number
    ) {
        final var n = Optional.ofNullable(name);
        final var num = Optional.ofNullable(number);

        if(n.isPresent() && num.isPresent()) {
            pn = filter(c -> !c.getName().equals(name) && !c.getNumber().equals(number), pn);
            return name + " : " + number + " 이(가) 삭제되었습니다.";
        } else if(!n.isPresent() && num.isPresent()) {
            final var n_ = filter(c -> c.getNumber().equals(number), pn);
            pn = filter(c -> !c.getNumber().equals(number), pn);
            return n_.get(0) + " 이(가) 삭제되었습니다.";
        } else if(n.isPresent() && !num.isPresent()) {
            final var n_ = filter(c -> c.getName().equals(name), pn);
            pn = filter(c -> !c.getName().equals(name), pn);
            return n_.get(0) + " 이(가) 삭제되었습니다.";
        } else throw new IllegalArgumentException("삭제할 이름 또는 번호를 입력하지 않았습니다.");
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

    // @GetMapping("/deleteFirst")
    // public String del(
    //     @RequestParam(name = "name", required = false) String name,
    //     @RequestParam(name = "number", required = false) String number
    // ) {
    //     final var n = Optional.ofNullable(name);
    //     final var num = Optional.ofNullable(number);

    //     if(!deleteAll && n.isPresent() && num.isPresent()) { // bool def = false
    //         pn = filter(c -> !c.getName().equals(name) && !c.getNumber().equals(number), pn);
    //         return name + " : " + number + " 이(가) 삭제되었습니다.";
    //     } else if(!deleteAll && n.isPresent() && num.isEmpty()) {
    //         final var names = filter(n_ -> n_.getName().equals(name), pn);
    //         return names + " 검색된 연락처 중 삭제할 연락처의 번호를 입력하세요.";
    //     } else if(!deleteAll && n.isEmpty() && num.isPresent()) {
    //         pn = filter(c -> !c.getNumber().equals(number), pn); 
    //         return number + " 이(가) 삭제되었습니다.";
    //     } else if(deleteAll && n.isPresent() && num.isPresent()) {
    //         pn = filter(n_ -> !n_.getName().equals(name), pn);
    //         return name + " : " + number + "이(가) 삭제되었습니다.";
    //     } else if(deleteAll && n.isPresent() && num.isEmpty()) {
    //         pn = filter(n_ -> !n_.getName().equals(name), pn);
    //         return name + " 이(가) 삭제되었습니다.";
    //     } else if(deleteAll && n.isEmpty() && num.isPresent()) {
    //         final var ls = filter(n_ -> n_.getNumber().equals(number), pn);
    //         pn = filter(n_ -> !n_.getNumber().equals(number), pn);
    //         return ls + " 이(가) 삭제되었습니다.";
    //     } else throw new IllegalArgumentException("삭제할 이름 또는 번호를 입력하지 않았습니다.");
    // }
}

/*
전화번호 등록
검색
삭제
 */