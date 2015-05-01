package todo.domain.service.todo;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring/applicationContext.xml",
        "classpath*:/META-INF/spring/testContext.xml" })

public class TodoServiceImplTest {
    @Inject
    TodoServiceImpl todoServiceImpl;
    
    @Inject
    NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * findOneで１件取得できているか確認する。
     * <p>　
     * [TodoService#findOne]
     * <ol>
     * <li>todoIdを使って永続層に登録したデータが取得できること.</li>
     * </ol>
     * </p>
     */
    @Test
    public void findOne() {
    }
    
    /**
     * findOne実行時、取得出来なかった場合、exceptionをthrowするか確認する。
     * <p>　
     * [TodoService#findOne]
     * <ol>
     * <li>ResourceNotFoundExceptionをthrowすること.</li>
     * <li>ResourceNotFoundExceptionにmessages"[E404] The requested Todo is
     *  not found.(id=" + todoId + ")"が付与されていること.</li>
     * </ol>
     * </p>
     */
    @Test
    public void findOne_exception() {
    }

}
