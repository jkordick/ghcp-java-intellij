package org.duckemporium.catalog;

public record DuckDto(
        Long id,
        String name,
        String category,
        Integer priceInCents,
        String tagline
) {}
