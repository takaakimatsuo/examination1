package jp.co.softbank.cxr.exam.domain.mapper;

import static jp.co.softbank.cxr.exam.common.Utils.toSqlTimestamp;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import org.junit.jupiter.api.Test;




class RecipeEntityMapperTest {

  @Test
  void test_レシピのエンティティが正常にドメインモデルに変換される() {
    Recipe expected = Recipe.builder()
                            .id(1)
                            .title("チキンカレー")
                            .makingTime("45分")
                            .serves("4人")
                            .ingredients("玉ねぎ,肉,スパイス")
                            .cost("1000")
                            .build();

    Recipe actual = RecipeEntityMapper.fromEntity(RecipeEntity.builder()
                                                              .id(1)
                                                              .title("チキンカレー")
                                                              .makingTime("45分")
                                                              .serves("4人")
                                                              .ingredients("玉ねぎ,肉,スパイス")
                                                              .cost(1000)
                                                              .createdAt(toSqlTimestamp("2020-02-03 18:00:00"))
                                                              .updatedAt(toSqlTimestamp("2020-02-29 10:45:00"))
                                                              .build());
    assertThat(actual).isEqualTo(expected);
  }


  @Test
  void test_レシピのエンティティのリストが正常にドメインモデルのリストに変換される() {
    List<Recipe> expected = Arrays.asList(Recipe.builder()
                                               .id(1)
                                               .title("チキンカレー")
                                               .makingTime("45分")
                                               .serves("4人")
                                               .ingredients("玉ねぎ,肉,スパイス")
                                               .cost("1000")
                                               .build(),
                                               Recipe.builder()
                                               .id(2)
                                               .title("オムライス")
                                               .makingTime("45分")
                                               .serves("3人")
                                               .ingredients("玉ねぎ,卵,スパイス,醤油")
                                               .cost("3000")
                                               .build());

    List<Recipe> actual = RecipeEntityMapper.fromEntities(Arrays.asList(RecipeEntity.builder()
                                                                .id(1)
                                                                .title("チキンカレー")
                                                                .makingTime("45分")
                                                                .serves("4人")
                                                                .ingredients("玉ねぎ,肉,スパイス")
                                                                .cost(1000)
                                                                .createdAt(toSqlTimestamp("2020-02-03 18:00:00"))
                                                                .updatedAt(toSqlTimestamp("2020-02-29 10:45:00"))
                                                                .build(),
                                                                RecipeEntity.builder()
                                                                  .id(2)
                                                                  .title("オムライス")
                                                                  .makingTime("45分")
                                                                  .serves("3人")
                                                                  .ingredients("玉ねぎ,卵,スパイス,醤油")
                                                                  .cost(3000)
                                                                  .createdAt(toSqlTimestamp("2020-02-03 18:00:00"))
                                                                  .updatedAt(toSqlTimestamp("2020-02-29 10:45:00"))
                                                                  .build()
                                                                ));
    assertThat(actual).isEqualTo(expected);
  }
}