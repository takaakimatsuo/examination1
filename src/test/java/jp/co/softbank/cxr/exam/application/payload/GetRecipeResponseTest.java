package jp.co.softbank.cxr.exam.application.payload;

import jp.co.softbank.cxr.exam.domain.model.Recipe;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;

class GetRecipeResponseTest {


  @Test
  void test_レシピのドメインモデルが正常にレスポンスにマッピングされる() {
    GetRecipeResponse expeceted = GetRecipeResponse.builder()
        .message("Recipe details by id")
        .recipePayloadList(Collections.singletonList(RecipePayload.builder()
                                                                  .id(1)
                                                                  .title("チキンカレー")
                                                                  .makingTime("45分")
                                                                  .serves("4人")
                                                                  .ingredients("玉ねぎ,肉,スパイス")
                                                                  .cost("1000")
                                                                  .build()))
        .build();

    GetRecipeResponse actual = GetRecipeResponse.of(Collections.singletonList(Recipe.builder()
                                                                                    .id(1)
                                                                                    .title("チキンカレー")
                                                                                    .makingTime("45分")
                                                                                    .serves("4人")
                                                                                    .ingredients("玉ねぎ,肉,スパイス")
                                                                                    .cost("1000")
                                                                                    .build()));
    assertThat(actual).isEqualTo(expeceted);
  }

}