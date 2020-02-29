package jp.co.softbank.cxr.exam.application.controller.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;


/**
 * 指定した ID で取得したレシピのレスポンス.
 *
 */

@Data
@Builder
public class GetRecipeResponse {
  private static final String GET_RECIPE_RESPONSE = "Recipe details by id";

  @JsonProperty(value = "message")
  private String message;

  @JsonProperty(value = "recipe")
  private List<RecipePayload> recipePayloadList;

  /**
   * ドメインモデルからレスポンスに変換.
   *
   * @param recipes 変換対象のレシピのドメインモデル
   * @return 指定したIDで取得できたレシピのレスポンス
   */
  public static GetRecipeResponse of(List<Recipe> recipes) {
    GetRecipeResponseBuilder response = GetRecipeResponse.builder().message(GET_RECIPE_RESPONSE);
    response.recipePayloadList(recipes.stream().map(RecipePayload::of).collect(Collectors.toList()));
    return response.build();
  }
}
