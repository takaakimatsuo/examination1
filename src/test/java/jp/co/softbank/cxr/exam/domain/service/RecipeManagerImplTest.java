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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeManagerImplTest {

  @InjectMocks
  RecipeManagerImpl recipeManager;

  @Mock
  RecipeRepository recipeRepository;

  @Test
  void test_指定されたIDでレシピが正常に取得できる場合() {
    when(recipeRepository.get(1)).thenReturn(Arrays.asList(RecipeEntity.builder()
                                                         .id(1)
                                                         .title("チキンカレー")
                                                         .makingTime("45分")
                                                         .serves("4人")
                                                         .ingredients("玉ねぎ,肉,スパイス")
                                                         .cost(1000)
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
                                                .build());

    List<Recipe> actual = recipeManager.getRecipe(1);

    assertThat(actual).isEqualTo(expected);
    verify(recipeRepository).get(1);
  }

  @Test
  void test_指定されたIDのレシピが存在しない場合() {
    // mock repository method
    when(recipeRepository.get(10)).thenReturn(Collections.emptyList());

    // execute, assert and verify
    ApplicationException actual = assertThrows(ApplicationException.class, () -> recipeManager.getRecipe(10));
    assertThat(actual.getErrorDetail()).isEqualTo(RECIPE_NOT_FOUND);
  }


  @Test
  void test_全てのレシピが正常に取得できる場合() {
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

  @Test
  void test_レシピが存在しない場合() {
    // mock repository method
    when(recipeRepository.getAll()).thenReturn(Collections.emptyList());

    // execute, assert and verify
    ApplicationException actual = assertThrows(ApplicationException.class, () -> recipeManager.getRecipes());
    assertThat(actual.getErrorDetail()).isEqualTo(RECIPE_NOT_FOUND);
  }

  @Test
  void test_レシピ登録が正常に行われる場合() {
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

    List<Recipe> expected = Arrays.asList(recipe);
    List<Recipe> actual = recipeManager.createRecipe(recipe);

    assertThat(actual).isEqualTo(expected);
    verify(recipeRepository).create(recipe);

  }

  @Test
  void test_レシピ削除が正常に行われる場合() {
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


  @Test
  void test_レシピ削除対象が存在しない場合() {
    when(recipeRepository.get(1)).thenReturn(Collections.emptyList());


    // execute, assert and verify
    ApplicationException actual = assertThrows(ApplicationException.class, () -> recipeManager.deleteRecipe(1));
    assertThat(actual.getErrorDetail()).isEqualTo(RECIPE_NOT_FOUND);
    verify(recipeRepository).get(1);
  }

}