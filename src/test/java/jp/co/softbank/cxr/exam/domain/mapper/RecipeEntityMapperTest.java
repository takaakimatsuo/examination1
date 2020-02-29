package jp.co.softbank.cxr.exam.domain.mapper;

import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import org.junit.jupiter.api.Test;

import static jp.co.softbank.cxr.exam.common.Utils.toSqlTimestamp;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
}