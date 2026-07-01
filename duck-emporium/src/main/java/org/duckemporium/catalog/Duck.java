package org.duckemporium.catalog;

import jakarta.persistence.*;

@Entity
@Table(name = "duck")
public class Duck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer priceInCents;

    @Column(nullable = false)
    private String tagline;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    protected Duck() {}

    public Duck(String name, Integer priceInCents, String tagline, Category category) {
        this.name = name;
        this.priceInCents = priceInCents;
        this.tagline = tagline;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPriceInCents() {
        return priceInCents;
    }

    public String getTagline() {
        return tagline;
    }

    public Category getCategory() {
        return category;
    }
}
