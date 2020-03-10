package jp.co.softbank.cxr.exam.domain.mapper;

import static java.util.Objects.isNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;



/**
 * レシピのドメインモデル( {@link Recipe})とエンティティ ({@link RecipeEntity} )を相互に変換を行うマッパー.
 *
 */
public class RecipeEntityMapper {

  private RecipeEntityMapper() {
    // Do Nothing.
  }

  /**
   * レシピエンティティからレシピのドメインモデルに変換.
   *
   * @param recipeEntity レシピのエンティティ
   * @return レシピのドメインモデル
   */
  public static Recipe fromEntity(RecipeEntity recipeEntity) {
    return Recipe.builder()
      .id(recipeEntity.getId())
      .title(recipeEntity.getTitle())
      .serves(recipeEntity.getServes())
      .ingredients(recipeEntity.getIngredients())
      .makingTime(recipeEntity.getMakingTime())
      .cost(recipeEntity.getCost().toString())
      .build();
  }

  /**
   * レシピエンティティのリストからレシピのドメインモデルのリストに変換.
   *
   * @param recipeEntities レシピのエンティティのリスト
   * @return レシピのドメインモデルのリスト
   */
  public static List<Recipe> fromEntities(List<RecipeEntity> recipeEntities) {
    List<Recipe> recipes = new ArrayList<>();
    for (RecipeEntity recipeEntity : recipeEntities) {
      recipes.add(RecipeEntityMapper.fromEntity(recipeEntity));
    }
    return recipes;
  }

  /**
   * レシピのドメインモデルからレシピエンティティからに変換.
   *
   * @param recipe レシピのドメインモデル
   * @param currentTime 現在の日時
   * @return レシピのエンティティ
   */
  public static RecipeEntity toEntity(Recipe recipe, LocalDateTime currentTime) {
    return RecipeEntity.builder()
      .id(recipe.getId())
      .title(recipe.getTitle())
      .serves(recipe.getServes())
      .ingredients(recipe.getIngredients())
      .makingTime(recipe.getMakingTime())
      .cost(Integer.parseInt(recipe.getCost()))
      .updatedAt(Timestamp.valueOf(currentTime))
      .createdAt(Timestamp.valueOf(currentTime))
      .build();
  }

  /**
   * レシピのドメインモデルからレシピエンティティからに変換.
   *
   * @param recipe レシピのドメインモデル
   * @return レシピのエンティティ
   */
  public static RecipeEntity toEntity(Recipe recipe) {
    return RecipeEntity.builder()
      .id(isNull(recipe.getId()) ? null : recipe.getId())
      .title(isNull(recipe.getTitle()) ? null : recipe.getTitle())
      .serves(isNull(recipe.getServes()) ? null : recipe.getServes())
      .ingredients(isNull(recipe.getIngredients()) ? null : recipe.getIngredients())
      .makingTime(isNull(recipe.getMakingTime()) ? null : recipe.getMakingTime())
      .cost(isNull(recipe.getCost()) ? null : Integer.parseInt(recipe.getCost()))
      .build();
  }
}
