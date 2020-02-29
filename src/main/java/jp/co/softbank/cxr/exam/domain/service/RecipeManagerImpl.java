package jp.co.softbank.cxr.exam.domain.service;

import java.util.Collections;
import java.util.List;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * {@link RecipeManager} の実装クラス.
 */
@NoArgsConstructor
@Component
public class RecipeManagerImpl implements RecipeManager {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Recipe> getRecipe(int id) {
    return Collections.emptyList();
  }
}
