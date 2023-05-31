package com.example.project1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToMany
    private List<Product> products;

    @Column
    private  Double total;

    @JsonIgnore
    @OneToOne(mappedBy = "sale")
    private Invoice invoice;

    @Override
    public String toString() {
        String productString="";
        for (Product p: products) {
            productString+=p.toString();
        }
        return "Sale{" +
                "id=" + id +
                ", products=[" + productString +
                "], total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id) && products.equals(sale.products) && Objects.equals(total, sale.total) && Objects.equals(invoice, sale.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, total, invoice);
    }
}
