package com.example.CouponSystem.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "company")

public class Company{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", updatable = false)
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password", updatable = false)
    private String password;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    @JsonIgnore
    private List<Coupon> coupons;
}
