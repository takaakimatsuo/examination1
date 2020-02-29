package jp.co.softbank.cxr.exam.domain.service;

import java.util.List;

import jp.co.softbank.cxr.exam.common.ApplicationException;
import jp.co.softbank.cxr.exam.domain.mapper.RecipeEntityMapper;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import jp.co.softbank.cxr.exam.integration.repository.RecipeRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static jp.co.softbank.cxr.exam.common.ErrorDetails.RECIPE_NOT_FOUND;


/**
 * {@link RecipeManager} の実装クラス.
 */
@NoArgsConstructor
@Component
public class RecipeManagerImpl implements RecipeManager {

  @Autowired
  private RecipeRepository recipeRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Recipe> getRecipe(int id) {
    List<RecipeEntity> recipeEntity = recipeRepository.get(id);

    if (isNull(recipeEntity)) {
      throw new ApplicationException(RECIPE_NOT_FOUND);
    }
    return RecipeEntityMapper.fromEntities(recipeEntity);
  }
}
