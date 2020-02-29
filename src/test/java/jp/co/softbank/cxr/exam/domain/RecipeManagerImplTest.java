package jp.co.softbank.cxr.exam.domain;

import static jp.co.softbank.cxr.exam.common.ErrorDetails.RECIPE_NOT_FOUND;
import static jp.co.softbank.cxr.exam.common.Utils.toSqlTimestamp;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import jp.co.softbank.cxr.exam.common.ApplicationException;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import jp.co.softbank.cxr.exam.integration.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeManagerImplTest {

  @InjectMocks
  RecipeManagerImpl recipeManager;

  @MockBean
  RecipeRepository recipeRepository;

  @Test
  void test_指定されたIDでレシピが正常に取得できる場合() {
    when(recipeRepository.get(1)).thenReturn(RecipeEntity.builder()
                                                         .id(1)
                                                         .title("チキンカレー")
                                                         .makingTime("45分")
                                                         .serves("4人")
                                                         .ingredients("玉ねぎ,肉,スパイス")
                                                         .cost(1000)
                                                         .createdAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                         .updatedAt(toSqlTimestamp("2020-02-23 14:00:00"))
                                                         .build());

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
    when(recipeRepository.get(10)).thenReturn(null);

    // execute, assert and verify
    ApplicationException actual = assertThrows(ApplicationException.class, () -> recipeManager.getRecipe(10));
    assertThat(actual.getErrorDetail()).isEqualTo(RECIPE_NOT_FOUND);
  }
}