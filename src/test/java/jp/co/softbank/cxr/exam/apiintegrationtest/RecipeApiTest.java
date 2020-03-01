package jp.co.softbank.cxr.exam.apiintegrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import jp.co.softbank.cxr.exam.application.payload.GetRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.RecipePayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import org.springframework.test.web.servlet.MockMvc;

import static com.ninja_squad.dbsetup.Operations.*;
import static jp.co.softbank.cxr.exam.common.ErrorDetails.RECIPE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeApiTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

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
  void test_正常に指定したIDでレシピの取得を行う() throws Exception {
    // expected
    GetRecipeResponse expectedResponse = GetRecipeResponse.builder()
        .message("Recipe details by id")
        .recipePayloadList(Arrays.asList(RecipePayload.builder().id(1)
                                                                .title("チキンカレー")
                                                                .makingTime("45分")
                                                                .serves("4人")
                                                                .ingredients("玉ねぎ,肉,スパイス")
                                                                .cost("1000")
                                                                .build()))
        .build();

    // execute, assert
    String responseJsonString = mockMvc.perform(get("/recipes/1"))
                                       .andExpect(status().isOk())
                                       .andReturn().getResponse().getContentAsString();
    GetRecipeResponse actualResponse = objectMapper.readValue(responseJsonString, GetRecipeResponse.class);
    assertThat(actualResponse).isEqualTo(expectedResponse);
  }

  @Test
  void test_正常に指定したIDのレシピが存在しない場合() throws Exception {
    // expected
    String expectedResponse = objectMapper.writeValueAsString(RECIPE_NOT_FOUND);

    // execute and assert
    mockMvc.perform(get("/recipes/10"))
      .andExpect(status().isNotFound())
      .andExpect(content().json(expectedResponse));
  }
}
