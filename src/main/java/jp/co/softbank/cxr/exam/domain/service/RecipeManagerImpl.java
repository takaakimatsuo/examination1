package jp.co.softbank.cxr.exam.domain.service;

import static jp.co.softbank.cxr.exam.common.ErrorDetails.RECIPE_NOT_FOUND;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import jp.co.softbank.cxr.exam.common.ApplicationException;
import jp.co.softbank.cxr.exam.domain.mapper.RecipeEntityMapper;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import jp.co.softbank.cxr.exam.integration.repository.RecipeRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



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

    if (isEmpty(recipeEntity)) {
      throw new ApplicationException(RECIPE_NOT_FOUND);
    }
    return RecipeEntityMapper.fromEntities(recipeEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Recipe> getRecipes() {
    List<RecipeEntity> recipeEntity = recipeRepository.getAll();

    if (isEmpty(recipeEntity)) {
      throw new ApplicationException(RECIPE_NOT_FOUND);
    }
    return RecipeEntityMapper.fromEntities(recipeEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Recipe> createRecipe(Recipe recipe) {
    List<RecipeEntity> recipeEntity = recipeRepository.create(recipe);
    return RecipeEntityMapper.fromEntities(recipeEntity);
  }

  /**
   * {@inheritDoc}
   *
   */
  public List<Recipe> deleteRecipe(int id) {
    return null;
  }
}
