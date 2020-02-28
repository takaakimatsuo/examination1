package jp.co.softbank.cxr.exam.domain.model;

import jp.co.softbank.cxr.exam.application.controller.payload.RecipePayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レシピを表すドメインモデルのクラス.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {
  private Integer id;

  private String title;

  private String makingTime;

  private String serves;

  private String ingredients;

  private String cost;
}
