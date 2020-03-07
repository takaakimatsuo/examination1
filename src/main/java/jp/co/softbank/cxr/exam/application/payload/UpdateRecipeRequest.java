package jp.co.softbank.cxr.exam.application.payload;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;



/**
 * レシピの更新時に使うリクエスト.
 *
 */

@Data
@Builder
public class UpdateRecipeRequest {

  @JsonProperty(value = "title")
  private String title;

  @JsonProperty(value = "making_time")
  private String makingTime;

  @JsonProperty(value = "serves")
  private String serves;

  @JsonProperty(value = "ingredients")
  private String ingredients;

  @JsonProperty(value = "cost")
  private String cost;

  /**
   * レシピ更新リクエストをレシピのドメインモデルに変換.
   * @param id 更新対象のレシピの ID
   * @return レシピのドメインモデル
   */
  public Recipe toModel(int id) {
    Recipe.RecipeBuilder recipe = Recipe.builder().id(id);

    if (nonNull(title)) {
      recipe.title(title);
    }

    if (nonNull(makingTime)) {
      recipe.makingTime(makingTime);
    }

    if (nonNull(serves)) {
      recipe.serves(serves);
    }

    if (nonNull(ingredients)) {
      recipe.ingredients(ingredients);
    }

    if (nonNull(cost)) {
      recipe.cost(cost);
    }

    return recipe.build();
  }

}
