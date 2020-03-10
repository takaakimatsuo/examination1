package jp.co.softbank.cxr.exam.application.payload;

import static jp.co.softbank.cxr.exam.common.messages.ResponseMessages.GET_RECIPE_RESPONSE;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;



/**
 * 指定した ID で取得したレシピのレスポンスを表すクラス.
 *
 */

@Data
@Builder
public class GetRecipeResponse {

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
    response.recipePayloadList(recipes.stream()
                                      .map(RecipePayload::of)
                                      .collect(Collectors.toList()));
    return response.build();
  }
}
