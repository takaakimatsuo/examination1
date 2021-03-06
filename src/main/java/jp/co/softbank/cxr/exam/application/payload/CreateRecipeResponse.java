package jp.co.softbank.cxr.exam.application.payload;

import static jp.co.softbank.cxr.exam.common.messages.ResponseMessages.POST_RECIPE_RESPONSE;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;



/**
 * レシピの登録に使うレスポンスを表すクラス.
 *
 */

@Data
@Builder
public class CreateRecipeResponse {


  @JsonProperty(value = "message")
  private String message;

  @JsonProperty(value = "recipe")
  private List<RecipePayload> recipePayloadList;


  /**
   * レシピのドメインモデルのリストからレシピ登録レスポンスに変換.
   *
   * @param recipes 登録されたレシピのリスト
   * @return レシピ登録レスポンス
   */
  public static CreateRecipeResponse of(List<Recipe> recipes) {
    return CreateRecipeResponse.builder()
                               .message(POST_RECIPE_RESPONSE)
                               .recipePayloadList(recipes.stream()
                                                         .map(RecipePayload::of)
                                                         .collect(Collectors.toList()))
                               .build();
  }

}
