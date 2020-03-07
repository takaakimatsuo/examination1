package jp.co.softbank.cxr.exam.domain.service;

import static jp.co.softbank.cxr.exam.common.ErrorDetails.RECIPE_NOT_FOUND;
import static jp.co.softbank.cxr.exam.common.Utils.toSqlTimestamp;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jp.co.softbank.cxr.exam.common.ApplicationException;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import jp.co.softbank.cxr.exam.integration.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@DisplayName("レシピ管理システムのビジネスロジックを扱うドメイン層のテスト")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeManagerImplTest {

  @InjectMocks
  RecipeManagerImpl recipeManager;

  @Mock
  RecipeRepository recipeRepository;

  @Nested
  class 特定のレシピを取得する時 {
    @Nested
    class 指定されたIDで正常に特定のレシピを取得できる場合 {
      @Test
      void test_指定されたIDでレシピを取得しリストとしてリターンする() {
        when(recipeRepository.get(1)).thenReturn(Collections.singletonList(RecipeEntity.builder()
                                                                                       .id(1)
                                                                                       .title("チキンカレー")
                                                                                       .makingTime("45分")
                                                                                       .serves("4人")
                                                                                       .ingredients("玉ねぎ,肉,スパイス")
                                                                                       .cost(1000)
                                                                                       .createdAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                                       .updatedAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                                       .build()));

        List<Recipe> expected = Collections.singletonList(Recipe.builder()
                                                                .id(1)
                                                                .title("チキンカレー")
                                                                .makingTime("45分")
                                                                .serves("4人")
                                                                .ingredients("玉ねぎ,肉,スパイス")
                                                                .cost("1000")
                                                                .build());

        List<Recipe> actual = recipeManager.getRecipe(1);

        assertThat(actual).isEqualTo(expected);
        verify(recipeRepository).get(1);
      }
    }

    @Nested
    class 指定されたIDのレシピが存在しなく取得ができない場合 {
      @Test
      void test_必要なレシピデータがないためApplicationExceptionが投げられる() {
        // mock repository method
        when(recipeRepository.get(10)).thenReturn(Collections.emptyList());

        // execute, assert and verify
        ApplicationException actual = assertThrows(
            ApplicationException.class, () -> recipeManager.getRecipe(10)
        );
        assertThat(actual.getErrorDetail()).isEqualTo(RECIPE_NOT_FOUND);
      }
    }
  }




  @Nested
  class 全てのレシピを取得する時 {
    @Nested
    class 正常に特定のレシピを取得できる場合 {
      @Test
      void test_全てのレシピを取得しリストとしてリターンする() {
        when(recipeRepository.getAll()).thenReturn(Arrays.asList(RecipeEntity.builder()
                                                                             .id(1)
                                                                             .title("チキンカレー")
                                                                             .makingTime("45分")
                                                                             .serves("4人")
                                                                             .ingredients("玉ねぎ,肉,スパイス")
                                                                             .cost(1000)
                                                                             .createdAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                             .updatedAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                             .build(),
                                                                 RecipeEntity.builder()
                                                                             .id(2)
                                                                             .title("オムライス")
                                                                             .makingTime("30分")
                                                                             .serves("2人")
                                                                             .ingredients("玉ねぎ,卵,スパイス,醤油")
                                                                             .cost(700)
                                                                             .createdAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                             .updatedAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                             .build()));

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
                                                    .makingTime("30分")
                                                    .serves("2人")
                                                    .ingredients("玉ねぎ,卵,スパイス,醤油")
                                                    .cost("700")
                                                    .build());

        List<Recipe> actual = recipeManager.getRecipes();

        assertThat(actual).isEqualTo(expected);
        verify(recipeRepository).getAll();
      }
    }

    @Nested
    class レシピが存在しなく取得ができない場合 {
      @Test
      void test_必要なレシピデータがないためApplicationExceptionが投げられる() {
        // mock repository method
        when(recipeRepository.getAll()).thenReturn(Collections.emptyList());

        // execute, assert and verify
        ApplicationException actual = assertThrows(ApplicationException.class, () -> recipeManager.getRecipes());
        assertThat(actual.getErrorDetail()).isEqualTo(RECIPE_NOT_FOUND);
      }
    }
  }




  @Nested
  class 新しいレシピを登録する時 {
    @Nested
    class 正常に特定のレシピを登録できる場合 {
      @Test
      void test_登録されたレシピをリストとしてリターンする() {
        // mock repository method
        Recipe recipe = Recipe.builder()
                              .title("チキンカレー")
                              .makingTime("45分")
                              .serves("4人")
                              .ingredients("玉ねぎ,肉,スパイス")
                              .cost("1000")
                              .build();

        when(recipeRepository.create(recipe)).thenReturn(Collections.singletonList(RecipeEntity.builder()
                                                                                               .title("チキンカレー")
                                                                                               .makingTime("45分")
                                                                                               .serves("4人")
                                                                                               .ingredients("玉ねぎ,肉,スパイス")
                                                                                               .cost(1000)
                                                                                               .createdAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                                               .updatedAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                                               .build()));

        List<Recipe> expected = Collections.singletonList(recipe);
        List<Recipe> actual = recipeManager.createRecipe(recipe);

        assertThat(actual).isEqualTo(expected);
        verify(recipeRepository).create(recipe);
      }
    }
  }

  @Nested
  class 既存レシピを削除する時 {
    @Nested
    class 正常に特定のレシピを削除できる場合 {
      @Test
      void test_指定されたIDで削除されたレシピをリストとしてリターンする() {
        when(recipeRepository.get(1)).thenReturn(Collections.singletonList(RecipeEntity.builder()
                                                                                       .title("チキンカレー")
                                                                                       .makingTime("45分")
                                                                                       .serves("4人")
                                                                                       .ingredients("玉ねぎ,肉,スパイス")
                                                                                       .cost(1000)
                                                                                       .build()));

        when(recipeRepository.delete(1)).thenReturn(Collections.singletonList(RecipeEntity.builder()
                                                                                              .title("チキンカレー")
                                                                                              .makingTime("45分")
                                                                                              .serves("4人")
                                                                                              .ingredients("玉ねぎ,肉,スパイス")
                                                                                              .cost(1000)
                                                                                              .build()));

        List<Recipe> expected = Collections.singletonList(Recipe.builder()
                                                                .title("チキンカレー")
                                                                .makingTime("45分")
                                                                .serves("4人")
                                                                .ingredients("玉ねぎ,肉,スパイス")
                                                                .cost("1000")
                                                                .build());

        List<Recipe> actual = recipeManager.deleteRecipe(1);

        assertThat(actual).isEqualTo(expected);
        verify(recipeRepository).get(1);
        verify(recipeRepository).delete(1);

      }
    }
    @Nested
    class レシピが存在しなく削除ができない場合 {
      @Test
      void test_必要なレシピデータがないためApplicationExceptionが投げられる() {
        when(recipeRepository.get(1)).thenReturn(Collections.emptyList());


        // execute, assert and verify
        ApplicationException actual = assertThrows(ApplicationException.class, () -> recipeManager.deleteRecipe(1));
        assertThat(actual.getErrorDetail()).isEqualTo(RECIPE_NOT_FOUND);
        verify(recipeRepository).get(1);
      }
    }
  }

  @Nested
  class 既存レシピを更新する時 {
    @Nested
    class 正常に特定のレシピを更新できる場合 {

      @Test
      void test_指定されたIDで更新されたレシピをリストとしてリターンする() {

        // mock repository method.
        when(recipeRepository.get(1)).thenReturn(Collections.singletonList(RecipeEntity.builder()
                                                                                       .title("チキンカレー")
                                                                                       .makingTime("45分")
                                                                                       .serves("4人")
                                                                                       .ingredients("玉ねぎ,肉,スパイス")
                                                                                       .cost(1000)
                                                                                       .createdAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                                                       .updatedAt(toSqlTimestamp("2020-02-23 18:00:00"))
                                                                                       .build()));

        Recipe recipe = Recipe.builder()
                              .id(1)
                              .makingTime("10分")
                              .serves("2人")
                              .build();

        when(recipeRepository.update(recipe))
          .thenReturn(Collections.singletonList(RecipeEntity.builder()
                                                            .title("チキンカレー")
                                                            .makingTime("10分")
                                                            .serves("2人")
                                                            .ingredients("玉ねぎ,肉,スパイス")
                                                            .cost(1000)
                                                            .createdAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                            .updatedAt(toSqlTimestamp("2020-03-01 10:00:00"))
                                                            .build()));

        // expected
        List<Recipe> expected = Collections.singletonList(Recipe.builder()
                                                                .title("チキンカレー")
                                                                .makingTime("10分")
                                                                .serves("2人")
                                                                .ingredients("玉ねぎ,肉,スパイス")
                                                                .cost("1000")
                                                                .build());

        List<Recipe> actual = recipeManager.updateRecipe(recipe);

        assertThat(actual).isEqualTo(expected);
        verify(recipeRepository).get(1);
        verify(recipeRepository).update(recipe);
      }
    }
    @Nested
    class レシピが存在しなく更新ができない場合 {
      @Test
      void test_必要なレシピデータがないためApplicationExceptionが投げられる() {
        when(recipeRepository.get(10)).thenReturn(Collections.emptyList());

        // mock repository method.
        Recipe recipe = Recipe.builder()
          .id(10)
          .makingTime("10分")
          .serves("2人")
          .build();
        // execute, assert and verify
        ApplicationException actual = assertThrows(ApplicationException.class, () -> recipeManager.updateRecipe(recipe));
        assertThat(actual.getErrorDetail()).isEqualTo(RECIPE_NOT_FOUND);
        verify(recipeRepository).get(10);
      }
    }
  }


}