package com.example.project1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column
    private Integer sold;

    public String  toXML() {
        return product.getId()+","+product.getName()+","+product.getDescription()+","+product.getPrice()+","+product.getStock()+","+sold+"\n";
    }
}
