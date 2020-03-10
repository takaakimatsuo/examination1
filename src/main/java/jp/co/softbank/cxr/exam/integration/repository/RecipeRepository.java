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
   * @return 取得されたレシピのエンティティを含むリスト
   */
  List<RecipeEntity> get(Integer id);

  /**
   * 全レシピをデータベースから取得.
   *
   * @return 取得されたレシピのエンティティを含むリスト
   */
  List<RecipeEntity> getAll();

  /**
   * 新しいレシピをデータベースに登録.
   *
   * @param recipe 登録対象のレシピ
   * @return 登録されたレシピのエンティティを含むリスト
   */
  List<RecipeEntity> create(Recipe recipe);

  /**
   * 既存のレシピをデータベースから削除.
   *
   * @param id 削除対象のレシピの ID
   * @return 削除されたレシピのエンティティを含むリスト
   */
  List<RecipeEntity> delete(Integer id);

  /**
   * データベースに存在する特定のレシピを更新.
   *
   * @param recipe 更新されるのレシピ
   * @return 更新されたレシピのエンティティを含むリスト
   */
  List<RecipeEntity> update(Recipe recipe);
}
