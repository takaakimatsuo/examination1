package jp.co.softbank.cxr.exam.integration.repository;

import java.util.List;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;


/**
 * レシピエンティティを取得するレポジトリのインターフェース.
 *
 */
public interface RecipeRepository {

  /**
   * 指定された ID で特定のレシピをデータベースから取得.
   *
   * @param id レシピの ID
   * @return レシピのエンティティのリスト
   */
  List<RecipeEntity> get(Integer id);

  /**
   * 全レシピをデータベースから取得.
   *
   * @return レシピのエンティティのリスト
   */
  List<RecipeEntity> getAll();
}
