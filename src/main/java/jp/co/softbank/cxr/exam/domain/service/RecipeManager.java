package jp.co.softbank.cxr.exam.domain.service;

import java.util.List;
import jp.co.softbank.cxr.exam.domain.model.Recipe;


public interface RecipeManager {
  /**
   * 指定した id をもつレシピを取得.
   *
   * @param id 指定するレシピの id
   * @return 実際に取得されたレシピ
   */
  List<Recipe> getRecipe(int id);

  /**
   * 全てのレシピを取得.
   *
   * @return 実際に取得されたレシピ
   */
  List<Recipe> getRecipes();

  /**
   * 新しいレシピの登録.
   *
   * @param recipe 登録したいレシピ
   * @return 実際に登録されたレシピ
   */
  List<Recipe> createRecipe(Recipe recipe);

  /**
   * 既存のレシピの削除.
   *
   * @param id 削除したいレシピのID
   * @return 実際に削除されたレシピ
   */
  List<Recipe> deleteRecipe(int id);

  /**
   * 既存のレシピの更新.
   *
   * @param recipe レシピの更新データ
   * @return 実際に更新に使用されたレシピ
   */
  List<Recipe> updateRecipe(Recipe recipe);
}
