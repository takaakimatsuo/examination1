package jp.co.softbank.cxr.exam.integration.repository;

import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;

/**
 * レシピエンティティを取得するレポジトリのインターフェース
 *
 */
public interface RecipeRepository {

  /**
   * 指定された ID でレシピをデータベースから取得.
   *
   * @param id レシピの ID
   * @return レシピのエンティティ
   */
  RecipeEntity get(Integer id);
}
