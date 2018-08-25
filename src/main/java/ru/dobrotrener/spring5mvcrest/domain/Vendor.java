package ru.dobrotrener.spring5mvcrest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String vendorUrl;

    public Vendor() {

    }

    public Vendor(String name) {
        this.name = name;
    }

    public Vendor(Long id, String name) {
        this.name = name;
        this.id = id;
    }
}
