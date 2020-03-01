package jp.co.softbank.cxr.exam.integration.repository;

import java.util.List;

import jp.co.softbank.cxr.exam.domain.model.Recipe;
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

  /**
   * レシピをデータベースに登録.
   *
   * @param recipe 登録対象のレシピ
   * @return 登録されたレシピのエンティティのリスト
   */
  List<RecipeEntity> create(Recipe recipe);

  /**
   * レシピをデータベースから削除.
   *
   * @param id 削除対象のレシピの ID
   * @return 削除されたレシピのエンティティのリスト
   */
  List<RecipeEntity> delete(Integer id);
}
