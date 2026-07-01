package org.duckemporium.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DuckRepository extends JpaRepository<Duck, Long> {

    @Query("SELECT d FROM Duck d JOIN FETCH d.category")
    List<Duck> findAllWithCategory();
}
