package org.duckemporium.catalog;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuckService {

    private final DuckRepository duckRepository;

    public DuckService(DuckRepository duckRepository) {
        this.duckRepository = duckRepository;
    }

    public List<DuckDto> findAll() {
        return duckRepository.findAllWithCategory().stream()
                .map(duck -> new DuckDto(
                        duck.getId(),
                        duck.getName(),
                        duck.getCategory().getName(),
                        duck.getPriceInCents(),
                        duck.getTagline()
                ))
                .toList();
    }
}
