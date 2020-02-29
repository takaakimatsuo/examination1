package jp.co.softbank.cxr.exam.domain.mapper;

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
    return null;
  }
}
