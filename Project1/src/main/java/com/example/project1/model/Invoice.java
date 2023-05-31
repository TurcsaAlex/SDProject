package com.example.project1.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="costumer_id")
    private Costumer costumer;

    @OneToOne
    @JoinColumn(name="sale_id")
    private Sale sale;

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", costumer=" + costumer.toString() +
                ", sale=" + sale.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) && Objects.equals(costumer, invoice.costumer) && Objects.equals(sale, invoice.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costumer, sale);
    }
}
