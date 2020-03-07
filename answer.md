
# DI に関するレポート
作成者: 松尾 賢明

## DIとは
DI は Dependency Injection の略であり、
日本語では**依存性の注入**のことを指す。

DI は、あるコンポーネントが成立するために、他のコンポーネントに依存している時(*Dependency*)、
その依存しているコンポーネントを外部から設定(*注入*)することを指す。
Springでは依存性を注入する際、主に@Autowiredアノテーションと@Componentアノテーションを使用する。

DI の手法は以下の三種類が主に使用される。
- Field injection
```
    // Field injectionの一例
    // この場合、HogeクラスはHugaクラスに依存している。
    
    class Hoge {
        @Autowired //Spring は自動的に Huga を探し注入する。
        Huga Huga; //直接インスタンス化するのではなく、依存性注入を行う。
    }
```
```
    //Hugaクラスを Spring 管理下にする。
    @Component
    class Huga {
        
    }
```
- Constructor injection
```
class Hoge {

  final Huga huga;

  @Autowired
  public Hoge(Huga huga;) {
    this.huga = huga;
  }
}
```
- Setter injection
```
class Hoge {

    Huga huga;

    @Autowired
    void setHuga(Huga huga) {
        this.huga = huga;
    }
}
```

なお、Springでは@Componentの代わりに@Componentを継承している@Controller、@Service、@Repository を使用することもできる。

また、以下のように同一のインターフェースを実装する異なるクラスが複数存在する場合、
@Qualifierアノテーションを使うことで、どのコンポーネントを優先的に注入するか指定することができる。
```
    class Hoge {
        @Autowired 
        @Qualifier("HugaRepository1")//この場合はHugaRepository1が注入される。
        Huga Huga; 
    }
```

```
@Repository("HugaRepository1")
public class HugaRepository1 implements Huga {

    @Override
    public void doSomething() {
       //省略
    }

}

@Repository("HugaRepository2")
public class HugaRepository2 implements Huga {

    @Override
    public void doSomething() {
       //省略
    }
}
```


## DIの利点
DI の利点は以下に示す。
- newによるインスタンス化が必要なくなるため、クラスの記述がより簡潔になる。
- 依存しているコンポーネントの変更、追加および削除が容易になる 
    - 特にテストの実装時に依存するコンポーネントのMock化が可能になる。
    - よって、全ての依存関係にあるコンポーネントを実装することなくテストを作ることができる。
- コンストラクタインジェクションでは依存するコンポーネントをfinalとして宣言でき、イミュータブル・オブジェクトにできる。
## DIを実装するにあたってどう実現したか

レシピ管理システムでは主にField Injectionを使用した。
今回の例では、アプリケーション層のRecipeControllerが
ドメイン層のRecipeManagerに依存しており、RecipeManagerは
インテグレーション層のRecipeRepositoryに依存している。
また、RecipeRepositoryはJPAを用いて実装されているためEntityManager、
および現在の日時を取得するために使われるDateTimeResolverに依存している。

###DIの実装への活用
```java:RecipeController.java
//RecipeControllerはRecipeManagerに依存しており、@Autowireを使って注入を行なっている。

@RestController
public class RecipeController {
  
    @Autowired
    private final RecipeManager recipeManager;

    //以下省略

}
```

```java:RecipeManagerImpl.java
//RecipeManagerはRecipeControllerに注入される対象であるため、@ComponentによってSpringの管理下におく。
//また、依存しているRecipeRepositoryを注入する。

@Component
public class RecipeManagerImpl implements RecipeManager {

  @Autowired
  private RecipeRepository recipeRepository;

    //以下省略

}
```

```java:RecipeRepositoryImpl.java
//RecipeRepositoryはRecipeManagerに注入される対象であるため、@ComponentによってSpringの管理下におく。
//また、依存しているEntityManagerとDateTimeResolverを注入する。

@Component
public class RecipeRepositoryImpl implements RecipeRepository {

  private static final String SELECT_ALL_FROM_RECIPES = "FROM RecipeEntity";

  @Autowired
  EntityManager entityManager;

  @Autowired
  DateTimeResolver dateTimeResolver;

}
```

###DIのテストへの活用

テストを実装するにあたって、
@InjectMocksでアノテートされているコンポーネントに対して、
@Mockにより依存する未実装のコンポーネントを注入した。

具体的な例はRecipeManagerを用いて以下に示す。

```
@DisplayName("レシピ管理システムのビジネスロジックを扱うドメイン層のテスト")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeManagerImplTest {

  @InjectMocks
  RecipeManagerImpl recipeManager;

  @Mock
  RecipeRepository recipeRepository;
    
 @Nested
  class 特定のレシピを取得する時 {
    @Nested
    class 指定されたIDで正常に特定のレシピを取得できる場合 {
      @Test
      void test_指定されたIDでレシピを取得しリストとしてリターンする() {
        when(recipeRepository.get(1)).thenReturn(
            Collections.singletonList(
              RecipeEntity.builder()
                          .id(1)
                          .title("チキンカレー")
                          .makingTime("45分")
                          .serves("4人")
                          .ingredients("玉ねぎ,肉,スパイス")
                          .cost(1000)
                          .createdAt(toSqlTimestamp("2020-02-23 14:00:00"))
                          .updatedAt(toSqlTimestamp("2020-02-23 14:00:00"))
                          .build())
        );

        List<Recipe> expected
            = Collections.singletonList(
              Recipe.builder()
                    .id(1)
                    .title("チキンカレー")
                    .makingTime("45分")
                    .serves("4人")
                    .ingredients("玉ねぎ,肉,スパイス")
                    .cost("1000")
                    .build()
        );

        List<Recipe> actual = recipeManager.getRecipe(1);

        assertThat(actual).isEqualTo(expected);
        verify(recipeRepository).get(1);
      }
    }
    //以下省略
}
```

上記の例では、@MockでアノテートされたRecipeRepositoryが@InjectMocksでアノテートされた
RecipeManagerImplに注入されている。
また、RecipeRepositoryの実際関数は未実装なため、when(recipeRepository.get(1)).thenReturn(//省略)
などを使用することで、特定の関数の戻り値を一時的に指定することができる。
