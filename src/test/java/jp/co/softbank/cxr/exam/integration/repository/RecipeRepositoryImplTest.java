package jp.co.softbank.cxr.exam.integration.repository;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static jp.co.softbank.cxr.exam.common.Utils.toSqlTimestamp;
import static org.assertj.core.api.Assertions.assertThat;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



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

  @Nested
  class 特定のレシピを取得する時 {
    @Nested
    class 正常に特定のレシピを削除できる場合 {
      @Test
      void test_指定したidでレシピをリストとしてリターンする() {
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
    }

    @Nested
    class レシピが存在しなく取得ができない場合 {
      @Test
      void test_空のリストをリターンする() {
        List<RecipeEntity> expected = Collections.emptyList();
        List<RecipeEntity> actual = recipeRepository.get(10);
        assertThat(actual).isEqualTo(expected);
      }
    }
  }


  @Nested
  class 全てのレシピを取得する時 {
    @Nested
    class 正常に全てのレシピを取得できる場合 {
      @Test
      void test_全てのレシピをリストとしてリターンする() {
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
    }

    @Nested
    class レシピが存在しなく取得ができない場合 {
      @Test
      void test_空のリストをリターンする() {
        Operation operation = sequenceOf(DELETE_ALL);
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:h2:mem:test;MODE=MSSQLServer;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false", "sa", "sa"), operation);
        dbSetup.launch();

        List<RecipeEntity> expected = Collections.emptyList();
        List<RecipeEntity> actual = recipeRepository.getAll();
        assertThat(actual).isEqualTo(expected);
      }
    }
  }



  @Nested
  class 新しいレシピを登録する時 {
    @Nested
    class 正常に特定のレシピを登録できる場合 {
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
  }


  @Nested
  class 既存のレシピを削除する時 {
    @Nested
    class 正常に特定のレシピを削除できる場合 {
      @Test
      void test_削除されたレシピのエンティティをリストとしてリターンする() {
        // expected
        List<RecipeEntity> expected = Collections.singletonList(RecipeEntity.builder()
                                                                            .title("チキンカレー")
                                                                            .makingTime("45分")
                                                                            .serves("4人")
                                                                            .ingredients("玉ねぎ,肉,スパイス")
                                                                            .cost(1000)
                                                                            .build());

        // execute
        List<RecipeEntity> actual = recipeRepository.delete(1);

        // assert
        assertThat(actual).usingElementComparatorIgnoringFields("id", "createdAt", "updatedAt").isEqualTo(expected);
      }
    }

    @Nested
    class レシピが存在しなく削除ができない場合 {
      @Test
      void test_nullを格納したリストをリターンする() {
        // expected
        RecipeEntity nullEntity = null;
        List<RecipeEntity> expected = Collections.singletonList(nullEntity);

        // execute
        List<RecipeEntity> actual = recipeRepository.delete(10);

        // assert
        assertThat(actual).isEqualTo(expected);
      }
    }
  }



  @Nested
  class 既存のレシピを更新する時 {
    @Nested
    class 正常に特定のレシピを更新できる場合 {
      @Test
      void test_情報が二つ更新されたレシピのエンティティをリストとしてリターンする() {

        Recipe recipe = Recipe.builder()
                              .id(1)
                              .makingTime("20分")
                              .serves("2人")
                              .build();

        // expected
        List<RecipeEntity> expected = Collections.singletonList(RecipeEntity.builder()
                                                                            .title("チキンカレー")
                                                                            .makingTime("20分")
                                                                            .serves("2人")
                                                                            .ingredients("玉ねぎ,肉,スパイス")
                                                                            .cost(1000)
                                                                            .build());
        // execute
        List<RecipeEntity> actual = recipeRepository.update(recipe);

        // assert
        assertThat(actual).usingElementComparatorIgnoringFields("id", "createdAt", "updatedAt").isEqualTo(expected);
      }

      @Test
      void test_情報がすべて更新されたレシピのエンティティをリストとしてリターンする() {

        Recipe recipe = Recipe.builder()
                              .id(1)
                              .title("新しいカレー")
                              .makingTime("120分")
                              .serves("1人")
                              .ingredients("真玉ねぎ,黒毛和牛肉,スパイス")
                              .cost("20000")
                              .build();

        // expected
        List<RecipeEntity> expected = Collections.singletonList(RecipeEntity.builder()
                                                                            .title("新しいカレー")
                                                                            .makingTime("120分")
                                                                            .serves("1人")
                                                                            .ingredients("真玉ねぎ,黒毛和牛肉,スパイス")
                                                                            .cost(20000)
                                                                            .build());
        // execute
        List<RecipeEntity> actual = recipeRepository.update(recipe);

        // assert
        assertThat(actual).usingElementComparatorIgnoringFields("id", "createdAt", "updatedAt").isEqualTo(expected);
      }
    }

    @Nested
    class レシピが存在しなく更新ができない場合 {
      @Test
      void test_nullを格納したリストをリターンする() {
        // expected
        RecipeEntity nullEntity = null;
        List<RecipeEntity> expected = Collections.singletonList(nullEntity);

        Recipe recipe = Recipe.builder()
                              .id(10)
                              .makingTime("20分")
                              .serves("2人")
                              .build();

        // execute
        List<RecipeEntity> actual = recipeRepository.update(recipe);

        // assert
        assertThat(actual).isEqualTo(expected);
      }
    }
  }
}