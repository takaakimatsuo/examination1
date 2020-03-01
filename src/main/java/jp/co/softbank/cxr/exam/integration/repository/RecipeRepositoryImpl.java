package jp.co.softbank.cxr.exam.integration.repository;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import jp.co.softbank.cxr.exam.common.DateTimeResolver;
import jp.co.softbank.cxr.exam.domain.mapper.RecipeEntityMapper;
import jp.co.softbank.cxr.exam.domain.model.Recipe;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
    List<RecipeEntity> recipeEntity = entityManager.createQuery(SELECT_ALL_FROM_RECIPES, RecipeEntity.class).getResultList();
    return recipeEntity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public List<RecipeEntity> create(Recipe recipe) {
    RecipeEntity recipeEntity = RecipeEntityMapper.toEntity(recipe, dateTimeResolver.getCurrentTime());
    entityManager.persist(recipeEntity);
    return Collections.singletonList(recipeEntity);
  }

}
