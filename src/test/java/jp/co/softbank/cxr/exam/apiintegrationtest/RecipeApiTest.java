package jp.co.softbank.cxr.exam.apiintegrationtest;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static jp.co.softbank.cxr.exam.common.ErrorDetails.RECIPE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.util.Arrays;
import java.util.Collections;
import jp.co.softbank.cxr.exam.application.payload.CreateRecipeRequest;
import jp.co.softbank.cxr.exam.application.payload.CreateRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.GetRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.GetRecipesResponse;
import jp.co.softbank.cxr.exam.application.payload.RecipePayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;




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
                                              "玉ねぎ,肉,卵",
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

  @Test
  void test_正常に全てのレシピの取得を行う() throws Exception {
    // expected
    GetRecipesResponse expectedResponse = GetRecipesResponse.builder()
        .recipePayloadList(Arrays.asList(RecipePayload.builder().id(1)
                                                                .title("チキンカレー")
                                                                .makingTime("45分")
                                                                .serves("4人")
                                                                .ingredients("玉ねぎ,肉,スパイス")
                                                                .cost("1000")
                                                                .build(),
                                         RecipePayload.builder().id(2)
                                                                .title("オムライス")
                                                                .makingTime("30分")
                                                                .serves("2人")
                                                                .ingredients("玉ねぎ,肉,卵")
                                                                .cost("700")
                                                                .build()))
        .build();

    // execute, assert
    String responseJsonString = mockMvc.perform(get("/recipes"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    String expectedJsonString = objectMapper.writeValueAsString(expectedResponse);

    assertThat(responseJsonString).isEqualTo(expectedJsonString);
  }

  @Test
  void test_正常に新しいレシピの登録を行う() throws Exception {
    // expected
    CreateRecipeResponse expectedResponse = CreateRecipeResponse.builder()
                                                                .message("Recipe successfully created!")
                                                                .recipePayloadList(Collections.singletonList(RecipePayload.builder()
                                                                                                                          .title("カレー")
                                                                                                                          .makingTime("45分")
                                                                                                                          .serves("4人")
                                                                                                                          .ingredients("玉ねぎ,肉,スパイス,カレールー")
                                                                                                                          .cost("1000")
                                                                                                                          .build()))
                                                                .build();

    CreateRecipeRequest createRecipeRequest = CreateRecipeRequest.builder()
                                                              .title("カレー")
                                                              .makingTime("45分")
                                                              .serves("4人")
                                                              .ingredients("玉ねぎ,肉,スパイス,カレールー")
                                                              .cost("1000")
                                                              .build();


    // execute & assert
    String responseJsonString = mockMvc.perform(post("/recipes")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(objectMapper.writeValueAsBytes(createRecipeRequest)))
                                       .andExpect(status().isCreated())
                                       .andReturn().getResponse().getContentAsString();

    CreateRecipeResponse actualResponse = objectMapper.readValue(responseJsonString, CreateRecipeResponse.class);
    assertThat(actualResponse.getRecipePayloadList().get(0).getTitle()).isEqualTo(expectedResponse.getRecipePayloadList().get(0).getTitle());
    assertThat(actualResponse.getRecipePayloadList().get(0).getIngredients()).isEqualTo(expectedResponse.getRecipePayloadList().get(0).getIngredients());
    assertThat(actualResponse.getRecipePayloadList().get(0).getMakingTime()).isEqualTo(expectedResponse.getRecipePayloadList().get(0).getMakingTime());
    assertThat(actualResponse.getRecipePayloadList().get(0).getServes()).isEqualTo(expectedResponse.getRecipePayloadList().get(0).getServes());
    assertThat(actualResponse.getRecipePayloadList().get(0).getCost()).isEqualTo(expectedResponse.getRecipePayloadList().get(0).getCost());
  }


  @Test
  void test_正常に指定したIDでレシピの削除を行う() throws Exception {
    // execute, assert
    mockMvc.perform(delete("/recipes/1"))
      .andExpect(status().isNoContent());
  }

  @Test
  void test_正常に指定したIDで存在しないレシピの削除を行う場合にエラーが返る() throws Exception {
    // expected
    String expectedResponse = objectMapper.writeValueAsString(RECIPE_NOT_FOUND);
    // execute, assert
    mockMvc.perform(delete("/recipes/10"))
      .andExpect(status().isNotFound())
      .andExpect(content().json(expectedResponse));
  }


}
