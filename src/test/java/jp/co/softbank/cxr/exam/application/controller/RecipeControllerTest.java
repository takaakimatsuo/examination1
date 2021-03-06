package jp.co.softbank.cxr.exam.application.controller;

import static jp.co.softbank.cxr.exam.common.ErrorDetails.RECIPE_NOT_FOUND;
import static jp.co.softbank.cxr.exam.common.ErrorDetailsRequired.INVALID_RECIPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.co.softbank.cxr.exam.application.payload.CreateRecipeRequest;
import jp.co.softbank.cxr.exam.application.payload.CreateRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.GetRecipeResponse;
import jp.co.softbank.cxr.exam.application.payload.GetRecipesResponse;
import jp.co.softbank.cxr.exam.application.payload.RecipePayload;
import jp.co.softbank.cxr.exam.application.payload.UpdateRecipeRequest;
import jp.co.softbank.cxr.exam.application.payload.UpdateRecipeResponse;
import jp.co.softbank.cxr.exam.common.ApplicationException;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.domain.service.RecipeManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;





/**
 * RecipeController 単体テスト.
 *
 */
@DisplayName("レシピ管理システムのアプリケーション層のテスト")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  RecipeManager recipeManager;

  @Test
  void test_entryのエンドポイントにリクエストを投げると200レスポンスが返される() throws Exception {
    // execute and assert
    mockMvc.perform(get("/"))
           .andExpect(status().isOk());
  }

  @Test
  void test_指定したidを持つレシピが正常にGETリクエストで返される() throws Exception {
    // mock domain method
    when(recipeManager.getRecipe(1)).thenReturn(generateSingleSampleRecipe());

    // expected
    GetRecipeResponse expectedResponse
        = GetRecipeResponse.builder()
                           .message("Recipe details by id")
                           .recipePayloadList(Collections.singletonList(RecipePayload.builder()
                             .id(1)
                             .title("チキンカレー")
                             .makingTime("45分")
                             .serves("4人")
                             .ingredients("玉ねぎ,肉,スパイス")
                             .cost("1000")
                             .build()))
                           .build();

    // execute, assert and verify
    String responseJsonString = mockMvc.perform(get("/recipes/1"))
                                       .andExpect(status().isOk())
                                       .andReturn().getResponse().getContentAsString();
    GetRecipeResponse actualResponse = objectMapper.readValue(
        responseJsonString,
        GetRecipeResponse.class
    );

    assertThat(actualResponse).isEqualTo(expectedResponse);
    verify(recipeManager).getRecipe(1);
  }


  @Test
  void test_指定したidのレシピが存在せずGETとするとエラーが返る() throws Exception {

    // mock domain method
    when(recipeManager.getRecipe(10)).thenThrow(new ApplicationException(RECIPE_NOT_FOUND));

    // expected error response
    String expectedResponse = objectMapper.writeValueAsString(RECIPE_NOT_FOUND);

    // execute, assert and verify
    mockMvc.perform(get("/recipes/10"))
           .andExpect(status().isNotFound())
           .andExpect(content().json(expectedResponse));
    verify(recipeManager).getRecipe(10);
  }


  @Test
  void test_全てのレシピが正常にGETリクエストで返される() throws Exception {

    // mock domain method
    when(recipeManager.getRecipes()).thenReturn(generateSampleRecipes());

    // expected
    GetRecipesResponse expectedResponse = GetRecipesResponse.builder()
        .recipePayloadList(Arrays.asList(RecipePayload.builder()
                                                      .id(1)
                                                      .title("チキンカレー")
                                                      .makingTime("45分")
                                                      .serves("4人")
                                                      .ingredients("玉ねぎ,肉,スパイス")
                                                      .cost("1000")
                                                      .build(),
                                         RecipePayload.builder()
                                                      .id(2)
                                                      .title("オムライス")
                                                      .makingTime("30分")
                                                      .serves("2人")
                                                      .ingredients("玉ねぎ,卵,スパイス,醤油")
                                                      .cost("700")
                                                      .build()))
        .build();

    // execute, assert and verify
    String actualJsonString = mockMvc.perform(get("/recipes"))
                                     .andExpect(status().isOk())
                                     .andReturn().getResponse().getContentAsString();

    String expectedJsonString = objectMapper.writeValueAsString(expectedResponse);

    assertThat(actualJsonString).isEqualTo(expectedJsonString);
    verify(recipeManager).getRecipes();
  }


  @Test
  void test_レシピが存在せずGETとするとエラーが返る() throws Exception {
    // mock domain method
    when(recipeManager.getRecipes()).thenThrow(new ApplicationException(RECIPE_NOT_FOUND));

    // expected error response
    String expectedResponse = objectMapper.writeValueAsString(RECIPE_NOT_FOUND);

    // execute, assert and verify
    mockMvc.perform(get("/recipes"))
           .andExpect(status().isNotFound())
           .andExpect(content().json(expectedResponse));
    verify(recipeManager).getRecipes();
  }

  @Test
  void test_レシピが正常にPOSTリクエストで登録される() throws Exception {

    // mock domain method
    Recipe recipe = Recipe.builder()
                          .title("チキンカレー")
                          .makingTime("45分")
                          .serves("4人")
                          .ingredients("玉ねぎ,肉,スパイス")
                          .cost("1000")
                          .build();
    Recipe recipeCreated = Recipe.builder()
                                 .id(1)
                                 .title("チキンカレー")
                                 .makingTime("45分")
                                 .serves("4人")
                                 .ingredients("玉ねぎ,肉,スパイス")
                                 .cost("1000")
                                 .build();
    when(recipeManager.createRecipe(recipe)).thenReturn(Collections.singletonList(recipeCreated));

    CreateRecipeRequest createRecipeRequest = CreateRecipeRequest.builder()
                                                                 .title("チキンカレー")
                                                                 .makingTime("45分")
                                                                 .serves("4人")
                                                                 .ingredients("玉ねぎ,肉,スパイス")
                                                                 .cost("1000")
                                                                 .build();

    // expected
    CreateRecipeResponse expectedResponse
        = CreateRecipeResponse.builder()
                              .message("Recipe successfully created!")
                              .recipePayloadList(
                                Collections.singletonList(
                                  RecipePayload.builder()
                                               .id(1)
                                               .title("チキンカレー")
                                               .makingTime("45分")
                                               .serves("4人")
                                               .ingredients("玉ねぎ,肉,スパイス")
                                               .cost("1000")
                                               .build()
                                )
                              ).build();

    // execute & assert
    String responseJsonString = mockMvc.perform(post("/recipes")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(
                                         objectMapper.writeValueAsBytes(createRecipeRequest)
                                       ))
                                       .andExpect(status().isCreated())
                                       .andReturn().getResponse().getContentAsString();

    CreateRecipeResponse actualResponse = objectMapper.readValue(
        responseJsonString, CreateRecipeResponse.class
    );

    assertThat(actualResponse).isEqualTo(expectedResponse);

    verify(recipeManager).createRecipe(recipe);

  }

  @Test
  void test_不正なレシピをPOSTリクエストするとエラーが返される() throws Exception {
    // mock domain method
    CreateRecipeRequest recipe = CreateRecipeRequest.builder()
                                                    .title("チキンカレー")
                                                    .makingTime("45分")
                                                    .ingredients("玉ねぎ,肉,スパイス")
                                                    .cost("1000")
                                                    .build();

    // expected error response
    String expectedResponse = objectMapper.writeValueAsString(INVALID_RECIPE);


    // execute, assert and verify
    mockMvc.perform(post("/recipes")
           .contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsBytes(recipe)))
           .andExpect(status().isBadRequest())
           .andExpect(content().json(expectedResponse));
  }

  @Test
  void test_costに文字が混じっているレシピをPOSTリクエストするとエラーが返される() throws Exception {
    // mock domain method
    CreateRecipeRequest recipe = CreateRecipeRequest.builder()
                                                    .title("チキンカレー")
                                                    .serves("2人")
                                                    .makingTime("45分")
                                                    .ingredients("玉ねぎ,肉,スパイス")
                                                    .cost("あいうえお")
                                                    .build();

    // expected error response
    String expectedResponse = objectMapper.writeValueAsString(INVALID_RECIPE);


    // execute, assert and verify
    mockMvc.perform(post("/recipes")
           .contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsBytes(recipe)))
           .andExpect(status().isBadRequest())
           .andExpect(content().json(expectedResponse));
  }

  @Test
  void test_指定したidを持つレシピが正常にDELETEリクエストで削除() throws Exception {

    // mock domain method
    when(recipeManager.deleteRecipe(1)).thenReturn(generateSingleSampleRecipe());

    // execute, assert and verify
    mockMvc.perform(delete("/recipes/1"))
           .andExpect(status().isNoContent())
           .andReturn().getResponse().getContentAsString();
    verify(recipeManager).deleteRecipe(1);
  }

  @Test
  void test_指定したidを持つレシピが存在しない場合DELETEリクエストでエラーが返される() throws Exception {

    // mock domain method
    when(recipeManager.deleteRecipe(1)).thenThrow(new ApplicationException(RECIPE_NOT_FOUND));

    // expected error response
    String expectedResponse = objectMapper.writeValueAsString(RECIPE_NOT_FOUND);

    // execute, assert and verify
    mockMvc.perform(delete("/recipes/1"))
           .andExpect(status().isNotFound())
           .andExpect(content().json(expectedResponse));
    verify(recipeManager).deleteRecipe(1);
  }

  @Test
  void test_レシピが正常にPATCHリクエストで更新される() throws Exception {

    // mock domain method
    Recipe recipe = Recipe.builder()
                          .id(1)
                          .makingTime("10分")
                          .serves("2人")
                          .build();

    Recipe recipeUpdated = Recipe.builder()
                                 .title("チキンカレー")
                                 .makingTime("10分")
                                 .serves("2人")
                                 .ingredients("玉ねぎ,肉,スパイス")
                                 .cost("1000")
                                 .build();

    when(recipeManager.updateRecipe(recipe)).thenReturn(Collections.singletonList(recipeUpdated));



    // expected
    UpdateRecipeResponse expectedResponse
        = UpdateRecipeResponse.builder()
                              .message("Recipe successfully updated!")
                              .recipePayloadList(
                                Collections.singletonList(
                                  RecipePayload.builder()
                                               .title("チキンカレー")
                                               .makingTime("10分")
                                               .serves("2人")
                                               .ingredients("玉ねぎ,肉,スパイス")
                                               .cost("1000")
                                               .build()
                                )
                              )
                              .build();

    // execute & assert

    UpdateRecipeRequest updateRecipeRequest = UpdateRecipeRequest.builder()
                                                                 .makingTime("10分")
                                                                 .serves("2人")
                                                                 .build();

    String responseJsonString = mockMvc.perform(patch("/recipes/1")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(
                                         objectMapper.writeValueAsBytes(updateRecipeRequest))
                                        )
                                       .andExpect(status().isOk())
                                       .andReturn().getResponse().getContentAsString();

    UpdateRecipeResponse actualResponse = objectMapper.readValue(responseJsonString,
                                                                 UpdateRecipeResponse.class);
    assertThat(actualResponse).isEqualTo(expectedResponse);

    verify(recipeManager).updateRecipe(recipe);

  }

  private List<Recipe> generateSingleSampleRecipe() {
    return Collections.singletonList(Recipe.builder()
                                           .id(1)
                                           .title("チキンカレー")
                                           .makingTime("45分")
                                           .serves("4人")
                                           .ingredients("玉ねぎ,肉,スパイス")
                                           .cost("1000")
                                           .build());
  }

  private List<Recipe> generateSampleRecipes() {
    return Arrays.asList(Recipe.builder()
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

  }

}