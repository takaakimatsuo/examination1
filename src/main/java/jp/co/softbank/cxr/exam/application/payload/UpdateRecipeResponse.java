package jp.co.softbank.cxr.exam.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;



/**
 * レシピの更新に使うレスポンスを表すクラス.
 *
 */

@Data
@Builder
public class UpdateRecipeResponse {

  private static final String PATCH_RECIPE_RESPONSE = "Recipe successfully updated!";

  @JsonProperty(value = "message")
  private String message;

  @JsonProperty(value = "recipe")
  private List<RecipePayload> recipePayloadList;

  /**
   * レシピのドメインモデルのリストからレシピ更新レスポンスに変換.
   *
   * @param recipes 更新されたレシピのリスト
   * @return レシピ更新レスポンス
   */
  public static UpdateRecipeResponse of(List<Recipe> recipes) {
    return UpdateRecipeResponse.builder()
                               .message(PATCH_RECIPE_RESPONSE)
                               .recipePayloadList(recipes.stream()
                                 .map(RecipePayload::of)
                                 .collect(Collectors.toList()))
                               .build();
  }


}
