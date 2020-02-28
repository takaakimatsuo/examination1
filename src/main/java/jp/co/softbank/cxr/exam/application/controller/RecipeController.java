package jp.co.softbank.cxr.exam.application.controller;

import java.util.List;
import jp.co.softbank.cxr.exam.application.controller.payload.GetRecipeResponse;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.domain.service.RecipeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    System.out.println("Request sent to /recipes/{" + id + "}");
    List<Recipe> recipes = recipeManager.getRecipe(id);
    return null;
  }


}
