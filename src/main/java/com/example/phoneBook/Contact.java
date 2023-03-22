package com.example.phoneBook;

import java.util.Optional;

public class Contact {
    private String name, number;
    private Optional<String> address, nickName;

    Contact(String name, String number, Optional<String> address, Optional<String> nickName) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.nickName = nickName;
    }
    
    public String getName() { return name; }
    public String getNumber() { return number; }
    public Optional<String> getAddress() { return address; }
    public Optional<String> getNickName() { return nickName; }

    @Override
    public String toString() {
        return "Contact { " +
        "name: " + name + 
        ", number: " + number + 
        ", address: " + address + 
        ", nickName: " + nickName +
        " }";
    }
}
