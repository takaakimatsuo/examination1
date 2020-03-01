package jp.co.softbank.cxr.exam.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 全取得したレシピのレスポンス.
 *
 */

@Data
@Builder
public class GetRecipesResponse {

  @JsonProperty(value = "recipes")
  private List<RecipePayload> recipePayloadList;

  /**
   * ドメインモデルからレスポンスに変換.
   *
   * @param recipes 変換対象のレシピのドメインモデルのリスト
   * @return 指定したIDで取得できたレシピのレスポンス
   */
  public static GetRecipesResponse of(List<Recipe> recipes) {
    return GetRecipesResponse.builder()
                             .recipePayloadList(recipes.stream()
                                                       .map(RecipePayload::of)
                                                       .collect(Collectors.toList()))
                             .build();
  }

}
