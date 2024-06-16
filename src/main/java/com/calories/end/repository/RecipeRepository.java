package com.calories.end.repository;

import com.calories.end.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "SELECT r.RECIPE_ID, r.NAME, r.DESCRIPTION, r.TOTAL_CALORIES, r.USER_ID, " +
            "i.INGREDIENT_ID, i.NAME AS INGREDIENT_NAME, i.QUANTITY, i.CALORIES " +
            "FROM RECIPES r " +
            "LEFT JOIN RECIPE_INGREDIENT ri ON r.RECIPE_ID = ri.RECIPE_ID " +
            "LEFT JOIN INGREDIENTS i ON ri.INGREDIENT_ID = i.INGREDIENT_ID " +
            "WHERE r.RECIPE_ID = :id",
            nativeQuery = true)
    List<Object[]> findByIdWithIngredients(@Param("id") Long id);

    @Query(value = "SELECT r.RECIPE_ID, r.NAME, r.DESCRIPTION, r.TOTAL_CALORIES, r.USER_ID, " +
            "i.INGREDIENT_ID, i.NAME AS INGREDIENT_NAME, i.QUANTITY, i.CALORIES " +
            "FROM RECIPES r " +
            "LEFT JOIN RECIPE_INGREDIENT ri ON r.RECIPE_ID = ri.RECIPE_ID " +
            "LEFT JOIN INGREDIENTS i ON ri.INGREDIENT_ID = i.INGREDIENT_ID",
            nativeQuery = true)
    List<Object[]> findAllWithIngredients();

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM RECIPE_INGREDIENT " +
            "WHERE RECIPE_ID = :recipeId AND INGREDIENT_ID = :ingredientId",
            nativeQuery = true)
    boolean existsByRecipeIdAndIngredientId(@Param("recipeId") Long recipeId, @Param("ingredientId") Long ingredientId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM RECIPE_INGREDIENT WHERE RECIPE_ID = :recipeId", nativeQuery = true)
    void deleteIngredientsByRecipeId(@Param("recipeId") Long recipeId);

    boolean existsByIngredients_Id(Long ingredientId);
}