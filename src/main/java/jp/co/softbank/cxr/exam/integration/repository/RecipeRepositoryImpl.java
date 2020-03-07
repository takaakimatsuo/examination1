package jp.co.softbank.cxr.exam.integration.repository;

import static java.util.Objects.nonNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import jp.co.softbank.cxr.exam.common.DateTimeResolver;
import jp.co.softbank.cxr.exam.domain.mapper.RecipeEntityMapper;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



/**
 * {@link RecipeRepository} の実装クラス.
 *
 */
@Component
public class RecipeRepositoryImpl implements RecipeRepository {

  private static final String SELECT_ALL_FROM_RECIPES = "FROM RecipeEntity";

  @Autowired
  EntityManager entityManager;

  @Autowired
  DateTimeResolver dateTimeResolver;


  /**
   * entityManager を返却するメソッド.
   *
   * @return entityManager
   */
  EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<RecipeEntity> get(Integer id) {
    List<RecipeEntity> result = new ArrayList<>();
    RecipeEntity recipeEntity = entityManager.find(RecipeEntity.class, id);
    if (nonNull(recipeEntity)) {
      result.add(recipeEntity);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<RecipeEntity> getAll() {
    return entityManager.createQuery(SELECT_ALL_FROM_RECIPES, RecipeEntity.class).getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public List<RecipeEntity> create(Recipe recipe) {
    RecipeEntity recipeEntity = RecipeEntityMapper.toEntity(recipe,
                                                            dateTimeResolver.getCurrentTime()
    );
    entityManager.persist(recipeEntity);
    return Collections.singletonList(recipeEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public List<RecipeEntity> delete(Integer id) {
    RecipeEntity recipeEntity = entityManager.find(RecipeEntity.class, id);
    if (nonNull(recipeEntity)) {
      entityManager.remove(recipeEntity);
    }
    return Collections.singletonList(recipeEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public List<RecipeEntity> update(Recipe recipe) {
    RecipeEntity patchRecipeEntity = RecipeEntityMapper.toEntity(recipe);
    Integer id = recipe.getId();
    RecipeEntity recipeEntity = entityManager.find(RecipeEntity.class, id);
    List<RecipeEntity> result = new ArrayList<>();
    if (nonNull(recipeEntity)) {
      recipeEntity = patchRecipeEntity(recipeEntity, patchRecipeEntity);
      result = Collections.singletonList(entityManager.merge(recipeEntity));
    }

    return result;
  }

  private RecipeEntity patchRecipeEntity(RecipeEntity recipeEntity, RecipeEntity patchRecipeEntity) {
    recipeEntity.setUpdatedAt(Timestamp.valueOf(dateTimeResolver.getCurrentTime()));

    if (nonNull(patchRecipeEntity.getTitle())) {
      recipeEntity.setTitle(patchRecipeEntity.getTitle());
    }
    if (nonNull(patchRecipeEntity.getMakingTime())) {
      recipeEntity.setMakingTime(patchRecipeEntity.getMakingTime());
    }
    if (nonNull(patchRecipeEntity.getCost())) {
      recipeEntity.setCost(patchRecipeEntity.getCost());
    }
    if (nonNull(patchRecipeEntity.getIngredients())) {
      recipeEntity.setIngredients(patchRecipeEntity.getIngredients());
    }
    if (nonNull(patchRecipeEntity.getServes())) {
      recipeEntity.setServes(patchRecipeEntity.getServes());
    }
    return recipeEntity;
  }

}
