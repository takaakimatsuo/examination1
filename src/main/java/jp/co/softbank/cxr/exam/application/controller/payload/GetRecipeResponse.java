package jp.co.softbank.cxr.exam.application.controller.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetRecipeResponse {
  @JsonProperty(value = "message")
  private String message;

  @JsonProperty(value = "recipe")
  private List<RecipePayload> recipePayloadList;
}
