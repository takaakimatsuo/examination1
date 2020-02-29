package jp.co.softbank.cxr.exam.domain.mapper;

import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;

import java.util.ArrayList;
import java.util.List;

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
}
