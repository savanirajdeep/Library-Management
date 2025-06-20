package com.example.library.repository;

import com.example.library.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    
    Optional<Genre> findByName(String name);
    
    Page<Genre> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    @Query("SELECT g FROM Genre g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(g.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Genre> searchGenres(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    boolean existsByName(String name);
} 