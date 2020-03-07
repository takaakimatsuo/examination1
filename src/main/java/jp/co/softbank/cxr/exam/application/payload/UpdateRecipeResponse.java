package jp.co.softbank.cxr.exam.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


/**
 * レシピの更新に使うレスポンス.
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


}
