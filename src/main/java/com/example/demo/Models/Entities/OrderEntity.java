package com.example.demo.Models.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "status")
    private String status;
}
