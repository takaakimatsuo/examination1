package jp.co.softbank.cxr.exam.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * レシピの登録時に使うリクエスト.
 *
 */

@Data
@Builder
public class CreateRecipeRequest {

  @NotNull
  @JsonProperty(value = "title")
  private String title;

  @NotNull
  @JsonProperty(value = "making_time")
  private String makingTime;

  @NotNull
  @JsonProperty(value = "serves")
  private String serves;

  @NotNull
  @JsonProperty(value = "ingredients")
  private String ingredients;

  @NotNull
  @JsonProperty(value = "cost")
  private String cost;

}
