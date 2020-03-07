package jp.co.softbank.cxr.exam.application.controller;

import static jp.co.softbank.cxr.exam.common.ErrorDetailsRequired.INVALID_RECIPE;
import static jp.co.softbank.cxr.exam.common.Utils.resolveCost;

import java.util.List;
import javax.validation.Valid;
import jp.co.softbank.cxr.exam.application.payload.CreateRecipeRequest;
import jp.co.softbank.cxr.exam.application.payload.CreateRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.GetRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.GetRecipesResponse;
import jp.co.softbank.cxr.exam.application.payload.UpdateRecipeRequest;
import jp.co.softbank.cxr.exam.application.payload.UpdateRecipeResponse;
import jp.co.softbank.cxr.exam.common.InvalidUserInputException;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.domain.service.RecipeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



/**
 * レシピ管理システムの REST コントローラー.
 *
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class RecipeController {


  @Autowired
  private final RecipeManager recipeManager;

  /**
   * entry のエンドポイント.
   *
   */
  @GetMapping(path = "/")
  @ResponseStatus(HttpStatus.OK)
  public void entry() {
    log.info("Request sent to entry endpoint.");
  }

  /**
   * 指定したIDで特定のレシピを取得するためのエンドポイント.
   *
   * @param id 取得するレシピの ID
   * @return 取得したレシピの情報とメッセージ
   */
  @GetMapping(path = "/recipes/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetRecipeResponse getRecipe(@PathVariable("id") Integer id) {
    log.info("Request sent to GET /recipes/{}", id);
    List<Recipe> recipes = recipeManager.getRecipe(id);
    return GetRecipeResponse.of(recipes);
  }

  /**
   * 全てのレシピを取得するためのエンドポイント.
   *
   * @return 取得したレシピの情報とメッセージ
   */
  @GetMapping(path = "/recipes")
  @ResponseStatus(HttpStatus.OK)
  public GetRecipesResponse getRecipes() {
    log.info("Request sent to GET /recipes");
    List<Recipe> recipes = recipeManager.getRecipes();
    return GetRecipesResponse.of(recipes);
  }

  /**
   * 新しいレシピを登録するためのエンドポイント.
   *
   * @param recipe 作成するレシピの情報
   * @param bindingResult 入力チェックのエラー情報
   * @return 作成されたレシピの情報
   */
  @PostMapping(path = "/recipes")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateRecipeResponse create(@RequestBody @Valid CreateRecipeRequest recipe,
                                     BindingResult bindingResult) {
    log.info("Request sent to POST /recipes");
    if (bindingResult.hasErrors()) {
      throw new InvalidUserInputException(INVALID_RECIPE);
    }
    resolveCost(recipe.getCost());
    List<Recipe> registeredRecipe = recipeManager.createRecipe(recipe.toModel());

    return CreateRecipeResponse.of(registeredRecipe);
  }

  /**
   * 既存のレシピを更新するためのエンドポイント.
   *
   * @param recipe レシピの更新情報
   * @param id 更新対象のレシピの ID
   * @param bindingResult 入力チェックのエラー情報
   * @return 更新後のレシピの情報
   */
  @PatchMapping(path = "/recipes/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UpdateRecipeResponse update(@RequestBody @Valid UpdateRecipeRequest recipe,
                                     BindingResult bindingResult,
                                     @PathVariable("id") Integer id) {
    if (bindingResult.hasErrors()) {
      throw new InvalidUserInputException(INVALID_RECIPE);
    }

    Recipe test = recipe.toModel(id);
    List<Recipe> recipes = recipeManager.updateRecipe(recipe.toModel(id));

    log.info("Request sent to PATCH /recipes/{}", id);
    return UpdateRecipeResponse.of(recipes);
  }


  /**
   * 既存のレシピを削除するためのエンドポイント.
   *
   * @param id 削除対象のレシピの ID
   */

  @DeleteMapping(path = "/recipes/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Integer id) {
    log.info("Request sent to DELETE /recipes/{}", id);
    List<Recipe> deletedRecipe = recipeManager.deleteRecipe(id);
  }

}
