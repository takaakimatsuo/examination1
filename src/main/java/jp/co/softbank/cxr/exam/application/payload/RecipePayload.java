package jp.co.softbank.cxr.exam.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipePayload {

  @JsonProperty(value = "id")
  private Integer id;

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
   * レシピのドメインモデルからペイロードに変換.
   * @param recipe 変換対象であるレシピのドメインモデル
   * @return レシピのペイロードモデル
   */
  static RecipePayload of(Recipe recipe) {
    return RecipePayload.builder()
      .id(recipe.getId())
      .title(recipe.getTitle())
      .makingTime(recipe.getMakingTime())
      .serves(recipe.getServes())
      .ingredients(recipe.getIngredients())
      .cost(recipe.getCost())
      .build();
  }

}
