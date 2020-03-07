package jp.co.softbank.cxr.exam.application.payload;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Collections;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



@DisplayName("ドメインモデルのレシピからPOSTレスポンスへのマッピングテスト")
class CreateRecipeResponseTest {

  @Test
  void test_レシピのドメインモデルが正常にCREATEレシピのレスポンスにマッピングされる() {
    CreateRecipeResponse expected = CreateRecipeResponse.builder()
                                                        .message("Recipe successfully created!")
                                                        .recipePayloadList(
                                                          Collections.singletonList(
                                                            RecipePayload.builder()
                                                                         .id(1)
                                                                         .title("チキンカレー")
                                                                         .makingTime("45分")
                                                                         .serves("4人")
                                                                         .ingredients("玉ねぎ,肉,スパイス")
                                                                         .cost("1000")
                                                                         .build()
                                                          )
                                                        )
                                                        .build();
    CreateRecipeResponse actual = CreateRecipeResponse.of(
        Collections.singletonList(Recipe.builder()
                                        .id(1)
                                        .title("チキンカレー")
                                        .makingTime("45分")
                                        .serves("4人")
                                        .ingredients("玉ねぎ,肉,スパイス")
                                        .cost("1000")
                                        .build()));

    assertThat(actual).isEqualTo(expected);
  }
}