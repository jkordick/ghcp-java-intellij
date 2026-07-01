package org.duckemporium.catalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DuckRepositoryTest {

    @Autowired
    private DuckRepository duckRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        duckRepository.deleteAll();
        categoryRepository.deleteAll();

        Category classics = categoryRepository.save(new Category("Classics"));
        Category themed = categoryRepository.save(new Category("Themed"));

        duckRepository.save(new Duck("Classic Yellow", 499, "The original.", classics));
        duckRepository.save(new Duck("Pirate Duck", 699, "Arrr!", themed));
    }

    @Test
    void findAllWithCategory_returnsAllDucks() {
        List<Duck> ducks = duckRepository.findAllWithCategory();
        assertThat(ducks).hasSize(2);
    }

    @Test
    void findAllWithCategory_categoryIsLoaded() {
        List<Duck> ducks = duckRepository.findAllWithCategory();
        assertThat(ducks).allSatisfy(duck -> {
            assertThat(duck.getCategory()).isNotNull();
            assertThat(duck.getCategory().getName()).isNotBlank();
        });
    }
}
