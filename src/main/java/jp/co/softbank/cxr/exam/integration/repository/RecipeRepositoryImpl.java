package jp.co.softbank.cxr.exam.integration.repository;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import jp.co.softbank.cxr.exam.integration.entity.RecipeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * {@link RecipeRepository} の実装クラス.
 *
 */
@Component
public class RecipeRepositoryImpl implements RecipeRepository {
  @Autowired
  EntityManager entityManager;

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

}
