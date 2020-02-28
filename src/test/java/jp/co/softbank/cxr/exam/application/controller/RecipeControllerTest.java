package jp.co.softbank.cxr.exam.application.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;



/**
 * RecipeController 単体テスト.
 *
 */
@DisplayName("RecipeControllerに対するテスト")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  void test_entryのエンドポイントにリクエストを投げると200レスポンスが返される() throws Exception {
    mockMvc.perform(get("/"))
      .andExpect(status().isOk());
  }

}