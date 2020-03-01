package jp.co.softbank.cxr.exam.application.controller;

import static jp.co.softbank.cxr.exam.common.ErrorDetailsRequired.INVALID_RECIPE;
import static jp.co.softbank.cxr.exam.common.Utils.resolveCost;

import java.util.List;
import javax.validation.Valid;
import jp.co.softbank.cxr.exam.application.payload.CreateRecipeRequest;
import jp.co.softbank.cxr.exam.application.payload.CreateRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.GetRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.GetRecipesResponse;
import jp.co.softbank.cxr.exam.common.InvalidUserInputException;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.domain.service.RecipeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



/**
 * レシピ管理システムの REST コントローラー.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class RecipeController {


  private final RecipeManager recipeManager;

  /**
   * entry のエンドポイント.
   */
  @GetMapping(path = "/")
  @ResponseStatus(HttpStatus.OK)
  public void entry() {
    log.info("Request sent to entry endpoint.");
  }

  /**
   * 指定したIDでレシピを get するためのエンドポイント.
   *
   */
  @GetMapping(path = "/recipes/{id}")
  @ResponseStatus(HttpStatus.OK)
  public GetRecipeResponse getRecipe(@PathVariable("id") Integer id) {
    log.info("Request sent to /recipes/{}", id);
    List<Recipe> recipes = recipeManager.getRecipe(id);
    return GetRecipeResponse.of(recipes);
  }

  /**
   * 全てのレシピを get するためのエンドポイント.
   *
   */
  @GetMapping(path = "/recipes")
  @ResponseStatus(HttpStatus.OK)
  public GetRecipesResponse getRecipes() {
    log.info("Request sent to /recipes");
    List<Recipe> recipes = recipeManager.getRecipes();
    return GetRecipesResponse.of(recipes);
  }

  /**
   * 新しいレシピを登録するためのエンドポイント.
   *
   */
  @PostMapping(path = "/recipes")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateRecipeResponse create(@RequestBody @Valid CreateRecipeRequest recipe,
                                     BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new InvalidUserInputException(INVALID_RECIPE);
    }
    resolveCost(recipe.getCost());
    List<Recipe> registeredRecipe = recipeManager.createRecipe(recipe.toModel());

    return CreateRecipeResponse.of(registeredRecipe);
  }


}
