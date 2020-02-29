package jp.co.softbank.cxr.exam.integration.repository;

import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import org.springframework.stereotype.Component;

/**
 * {@link RecipeRepository} の実装クラス.
 *
 */
@Component
public class RecipeRepositoryImpl implements RecipeRepository {

  @Override
  public RecipeEntity get(Integer id) {
    return null;
  }
}
