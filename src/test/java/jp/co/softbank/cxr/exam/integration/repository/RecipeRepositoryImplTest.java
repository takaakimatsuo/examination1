package jp.co.softbank.cxr.exam.integration.repository;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static jp.co.softbank.cxr.exam.common.Utils.toSqlTimestamp;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.co.softbank.cxr.exam.common.DateTimeResolver;
import jp.co.softbank.cxr.exam.common.Utils;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeRepositoryImplTest {

  @Autowired
  RecipeRepositoryImpl recipeRepository;


  private static final Operation DELETE_ALL = deleteAllFrom("recipes");
  private static final Operation INSERT_RECIPE
      = insertInto("recipes").columns("id",
                                            "title",
                                            "making_time",
                                            "serves",
                                            "ingredients",
                                            "cost",
                                            "created_at",
                                            "updated_at")
                                            .values(
                                              1,
                                              "チキンカレー",
                                              "45分",
                                              "4人",
                                              "玉ねぎ,肉,スパイス",
                                              1000,
                                              "2016-01-10 12:10:12",
                                              "2016-01-11 12:10:12")
                                            .values(
                                              2,
                                              "オムライス",
                                              "30分",
                                              "2人",
                                              "玉ねぎ,卵,スパイス,醤油",
                                              700,
                                              "2016-02-10 12:10:12",
                                              "2016-02-11 12:10:12")
                                            .build();

  @BeforeEach
  void setUp() {
    Operation operation = sequenceOf(DELETE_ALL, INSERT_RECIPE);
    DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:h2:mem:test;MODE=MSSQLServer;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false", "sa", "sa"), operation);
    dbSetup.launch();
  }


  @Test
  void test_指定したidでレシピを正常に取得 () {
    // expected
    List<RecipeEntity> expected = Arrays.asList(RecipeEntity.builder()
                                        .id(1)
                                        .title("チキンカレー")
                                        .makingTime("45分")
                                        .serves("4人")
                                        .ingredients("玉ねぎ,肉,スパイス")
                                        .cost(1000)
                                        .createdAt(toSqlTimestamp("2016-01-10 12:10:12"))
                                        .updatedAt(toSqlTimestamp("2016-01-11 12:10:12"))
                                        .build());

    // execute
    List<RecipeEntity> actual = recipeRepository.get(1);
    // assert
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void test_指定したidのレシピが存在しない場合の取得() {
    List<RecipeEntity> expected = Collections.emptyList();
    List<RecipeEntity> actual = recipeRepository.get(10);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void test_全てのレシピを正常に取得 () {
    // expected
    List<RecipeEntity> expected = Arrays.asList(RecipeEntity.builder()
                                                            .id(1)
                                                            .title("チキンカレー")
                                                            .makingTime("45分")
                                                            .serves("4人")
                                                            .ingredients("玉ねぎ,肉,スパイス")
                                                            .cost(1000)
                                                            .createdAt(toSqlTimestamp("2016-01-10 12:10:12"))
                                                            .updatedAt(toSqlTimestamp("2016-01-11 12:10:12"))
                                                            .build(),
                                                RecipeEntity.builder()
                                                            .id(2)
                                                            .title("オムライス")
                                                            .makingTime("30分")
                                                            .serves("2人")
                                                            .ingredients("玉ねぎ,卵,スパイス,醤油")
                                                            .cost(700)
                                                            .createdAt(toSqlTimestamp("2016-02-10 12:10:12"))
                                                            .updatedAt(toSqlTimestamp("2016-02-11 12:10:12"))
                                                            .build());

    // execute
    List<RecipeEntity> actual = recipeRepository.getAll();
    // assert
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void test_レシピが存在しない場合の取得() {
    Operation operation = sequenceOf(DELETE_ALL);
    DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:h2:mem:test;MODE=MSSQLServer;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false", "sa", "sa"), operation);
    dbSetup.launch();

    List<RecipeEntity> expected = Collections.emptyList();
    List<RecipeEntity> actual = recipeRepository.getAll();
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void test_レシピを正常に登録() {

    Recipe recipe = Recipe.builder()
                          .title("チキンスープ")
                          .makingTime("45分")
                          .serves("4人")
                          .ingredients("玉ねぎ,鳥肉,スパイス")
                          .cost("1000")
                          .build();
    // expected
    List<RecipeEntity> expected = Collections.singletonList(RecipeEntity.builder()
                                                                        .title("チキンスープ")
                                                                        .makingTime("45分")
                                                                        .serves("4人")
                                                                        .ingredients("玉ねぎ,鳥肉,スパイス")
                                                                        .cost(1000)
                                                                        .build());

    // execute
    List<RecipeEntity> actual = recipeRepository.create(recipe);
    // assert
    assertThat(actual).usingElementComparatorIgnoringFields("id", "createdAt", "updatedAt").isEqualTo(expected);
  }
}