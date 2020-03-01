package jp.co.softbank.cxr.exam.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


/**
 * レシピの登録時に使うリクエスト.
 *
 */

@Data
@Builder
public class CreateRecipeRequest {

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

}
