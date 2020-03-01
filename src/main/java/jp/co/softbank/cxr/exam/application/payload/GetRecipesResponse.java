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

  @JsonProperty(value = "recipe")
  private List<RecipePayload> recipePayloadList;

  public static GetRecipesResponse of(List<Recipe> recipes) {
    return null;
  }

}
