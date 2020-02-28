package jp.co.softbank.cxr.exam.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * レシピ管理システムの REST コントローラー.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class RecipeController {
  /**
   * entry のエンドポイント.
   */
  @GetMapping(path = "/")
  @ResponseStatus(HttpStatus.OK)
  public void entry() {
    log.debug("Request sent to entry endpoint.");
  }


}
