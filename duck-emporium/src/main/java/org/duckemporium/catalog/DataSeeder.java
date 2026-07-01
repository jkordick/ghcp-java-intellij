package org.duckemporium.catalog;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final CategoryRepository categoryRepository;
    private final DuckRepository duckRepository;

    public DataSeeder(CategoryRepository categoryRepository, DuckRepository duckRepository) {
        this.categoryRepository = categoryRepository;
        this.duckRepository = duckRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (duckRepository.count() > 0) {
            return;
        }

        Category classics = categoryRepository.save(new Category("Classics"));
        Category themed = categoryRepository.save(new Category("Themed"));
        Category luxury = categoryRepository.save(new Category("Luxury"));

        duckRepository.saveAll(List.of(
                new Duck("Classic Yellow", 499, "The one that started it all.", classics),
                new Duck("Mini Yellow", 299, "Small but mighty.", classics),
                new Duck("Big Yellow", 799, "For those who like it large.", classics),
                new Duck("Pirate Duck", 699, "Arrr, rubber me timbers!", themed),
                new Duck("Astronaut Duck", 899, "One small quack for duckkind.", themed),
                new Duck("Ninja Duck", 749, "Silent but deadly... cute.", themed),
                new Duck("Wizard Duck", 749, "You shall not splash!", themed),
                new Duck("Golden Duck", 2499, "24-karat splendor for your tub.", luxury),
                new Duck("Diamond Duck", 4999, "Ridiculously overpriced. Worth it.", luxury),
                new Duck("Velvet Duck", 1999, "Softer than your average rubber.", luxury)
        ));
    }
}
