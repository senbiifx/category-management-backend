package com.assessment.categorymanagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column
    private String name;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Category> children;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="parent_id")
    private Category parent;

    public Category(Integer categoryId){
        this.categoryId = categoryId;
    }

    public Category(Integer categoryId, String name, Category parent){
        this.categoryId = categoryId;
        this.name = name;
        this.parent = parent;
    }

}
