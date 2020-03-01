package jp.co.softbank.cxr.exam.domain.mapper;

import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;

import java.util.ArrayList;
import java.util.List;

import static jp.co.softbank.cxr.exam.common.Utils.getCurrentTime;
import static jp.co.softbank.cxr.exam.common.Utils.toSqlTimestamp;

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
  public static RecipeEntity toEntity(Recipe recipe) {
    return RecipeEntity.builder()
      .id(recipe.getId())
      .title(recipe.getTitle())
      .serves(recipe.getServes())
      .ingredients(recipe.getIngredients())
      .makingTime(recipe.getMakingTime())
      .cost(Integer.parseInt(recipe.getCost()))
      .updatedAt(toSqlTimestamp(getCurrentTime().toString()))
      .createdAt(toSqlTimestamp(getCurrentTime().toString()))
      .build();
  }
}
