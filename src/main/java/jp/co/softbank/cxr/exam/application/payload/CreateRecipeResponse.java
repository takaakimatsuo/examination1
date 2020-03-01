package jp.co.softbank.cxr.exam.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


/**
 * レシピの登録に使うレスポンス.
 *
 */

@Data
@Builder
public class CreateRecipeResponse {

  private static final String POST_RECIPE_RESPONSE = "Recipe successfully created!";

  @JsonProperty(value = "message")
  private String message;

  @JsonProperty(value = "recipe")
  private List<RecipePayload> recipePayloadList;

}
