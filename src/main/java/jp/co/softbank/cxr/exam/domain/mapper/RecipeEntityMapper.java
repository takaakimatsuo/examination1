package jp.co.softbank.cxr.exam.domain.mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;



/**
 * レシピのドメインモデルとエンティティを相互に変換を行うマッパー.
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
}
