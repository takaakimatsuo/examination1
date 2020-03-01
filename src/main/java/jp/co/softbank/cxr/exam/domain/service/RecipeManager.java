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
}
