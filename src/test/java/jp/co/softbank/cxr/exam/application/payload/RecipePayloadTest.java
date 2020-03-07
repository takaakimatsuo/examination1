package jp.co.softbank.cxr.exam.application.payload;

import static org.assertj.core.api.Assertions.assertThat;

import jp.co.softbank.cxr.exam.domain.model.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("レシピペイロードのマッピングテスト")
class RecipePayloadTest {

  @Test
  void test_レシピのドメインモデルが正常にペイロードにマッピングされる() {
    RecipePayload expected = RecipePayload.builder()
                                           .id(1)
                                           .title("チキンカレー")
                                           .makingTime("45分")
                                           .serves("4人")
                                           .ingredients("玉ねぎ,肉,スパイス")
                                           .cost("1000")
                                           .build();

    RecipePayload actual = RecipePayload.of(Recipe.builder()
                                                  .id(1)
                                                  .title("チキンカレー")
                                                  .makingTime("45分")
                                                  .serves("4人")
                                                  .ingredients("玉ねぎ,肉,スパイス")
                                                  .cost("1000")
                                                  .build());
    assertThat(actual).isEqualTo(expected);
  }


}