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
    List<RecipeEntity> recipeEntities = recipeRepository.get(id);

    if (isEmpty(recipeEntities)) {
      throw new ApplicationException(RECIPE_NOT_FOUND);
    }
    return RecipeEntityMapper.fromEntities(recipeEntities);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Recipe> getRecipes() {
    List<RecipeEntity> recipeEntities = recipeRepository.getAll();

    if (isEmpty(recipeEntities)) {
      throw new ApplicationException(RECIPE_NOT_FOUND);
    }
    return RecipeEntityMapper.fromEntities(recipeEntities);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Recipe> createRecipe(Recipe recipe) {
    List<RecipeEntity> recipeEntities = recipeRepository.create(recipe);
    return RecipeEntityMapper.fromEntities(recipeEntities);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public List<Recipe> deleteRecipe(int id) {

    List<RecipeEntity> recipeEntities = recipeRepository.get(id);
    if (isEmpty(recipeEntities)) {
      throw new ApplicationException(RECIPE_NOT_FOUND);
    }
    recipeRepository.delete(id);
    return RecipeEntityMapper.fromEntities(recipeEntities);
  }

  /**
   * {@inheritDoc}
   *
   */
  @Override
  public List<Recipe> updateRecipe(Recipe recipe) {
    return null;
  }
}
