package todo.domain.service.todo;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import todo.domain.model.Todo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/META-INF/spring/applicationContext.xml",
		"classpath*:/META-INF/spring/testContext.xml" })
public class TodoServiceTest {

	@Inject
	TodoService todoService;

	@Inject
	NamedParameterJdbcTemplate npJdbcTemplate;

	@Inject
	JdbcTemplate jdbcTemplate;
	
	int MAX_UNFINISHED_COUNT = 5;

	/**
	 * findAllで全件取得できているか確認する。
	 * <p>
	 * 　 [TodoService#findAll]
	 * <ol>
	 * <li>永続層に登録したデータを全件取得できること.</li>
	 * </ol>
	 * </p>
	 */
	@Test
	public void findAll() {
//		jdbcTemplate.execute("DELETE FROM todo");

        String todoIdOne = UUID.randomUUID().toString();
        String todoIdTwo = UUID.randomUUID().toString();
        String todoTitle = "test";
        String finished = "0";
        String createdAt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS").format(new Date());
		
		jdbcTemplate.execute("INSERT INTO todo("
				+ "todo_id, todo_title, finished, created_at"
				+ "VALUES('" + todoIdOne + "','" + todoTitle 
				+ "'," + finished + ",'" + createdAt + "');");
		
		jdbcTemplate.execute("INSERT INTO todo("
				+ "todo_id, todo_title, finished, created_at"
				+ "VALUES('" + todoIdTwo + "','" + todoTitle 
				+ "'," + finished + ",'" + createdAt + "');");

		List<Todo> findAllTodo = (List<Todo>) todoService.findAll();

		Todo resultTodoOne = npJdbcTemplate.queryForObject(
						"SELECT todo_id,todo_title,finished,created_at FROM TODO WHERE todo_id = :todoId",
						new MapSqlParameterSource().addValue("todoId",todoIdOne),
						new BeanPropertyRowMapper<Todo>(Todo.class));
		Todo resultTodoTwo = npJdbcTemplate.queryForObject(
						"SELECT todo_id,todo_title,finished,created_at FROM TODO WHERE todo_id = :todoId",
						new MapSqlParameterSource().addValue("todoId",todoIdTwo),
						new BeanPropertyRowMapper<Todo>(Todo.class));
		
		assertThat(resultTodoOne.getTodoId(), is(findAllTodo.get(0).getTodoId()));
		assertThat(resultTodoOne.getTodoTitle(), is(findAllTodo.get(0).getTodoTitle()));
		assertThat(resultTodoOne.isFinished(), is(findAllTodo.get(0).isFinished()));
		assertThat(resultTodoOne.getCreatedAt().getTime(), is(findAllTodo.get(0).getCreatedAt().getTime()));
		
		assertThat(resultTodoTwo.getTodoId(), is(findAllTodo.get(1).getTodoId()));
		assertThat(resultTodoTwo.getTodoTitle(), is(findAllTodo.get(1).getTodoTitle()));
		assertThat(resultTodoTwo.isFinished(), is(findAllTodo.get(1).isFinished()));
		assertThat(resultTodoTwo.getCreatedAt().getTime(), is(findAllTodo.get(1).getCreatedAt().getTime()));
		
	}

	/**
	 * createで1件登録した内容を永続層から取得して確認する。
	 * <p>
	 * [TodoService#create]
	 * <ol>
	 * <li>自動採番したtodoIdを使って永続層に登録したデータが取得できること.</li>
	 * <li>自動採番したtodoIdがcreateの戻り値と永続層から取得した値が一致していること.</li>
	 * <li>todoTitleが本メソッドで登録した内容と永続層から取得した値が一致していること.</li>
	 * <li>自動採番したCreatedAtがcreateの戻り値と永続層から取得した値が一致していること.</li>
	 * </ol>
	 * </p>
	 */
	@Test
	public void create() {
		Todo todo = new Todo();
		todo.setTodoTitle("test_create");
		todo.setFinished(false);

		Todo createTodo = todoService.create(todo);

		Todo resultTodo = npJdbcTemplate.queryForObject(
						"SELECT todo_id,todo_title,finished,created_at FROM TODO WHERE todo_id = :todoId",
						new MapSqlParameterSource().addValue("todoId",
								createTodo.getTodoId()),
						new BeanPropertyRowMapper<Todo>(Todo.class));

		assertThat(resultTodo.getTodoId(), is(createTodo.getTodoId()));
		assertThat(resultTodo.getTodoTitle(), is("test_create"));
		assertThat(resultTodo.getCreatedAt().getTime(), is(createTodo
				.getCreatedAt().getTime()));

	}

	/**
	 * create実行時、既にMAX_UNFINISHED_COUNT件登録済みの場合exceptionをthrowするか確認する。
	 * <p>
	 * [TodoService#create]
	 * <ol>
	 * <li>BusinessExceptionをthrowすること.</li>
	 * <li>BusinessExceptionにmessages"[E001] The count of un-finished Todo must
	 * not be over "+ MAX_UNFINISHED_COUNT + "."が付与されていること.</li>
	 * </ol>
	 * </p>
	 */
	@Test
	public void create_exception() {
//		for (int i = 0; i < MAX_UNFINISHED_COUNT; i++) {
//	        String todoId = UUID.randomUUID().toString();
//	        String todoTitle = "test";
//	        String finished = "0";
//	        String createdAt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS").format(new Date());
//			
//			jdbcTemplate.execute("INSERT INTO todo("
//					+ "todo_id, todo_title, finished, created_at"
//					+ "VALUES('" + todoId + "','" + todoTitle 
//					+ "'," + finished + ",'" + createdAt + "');");
//		}
		
		
		
		
	}

	/**
	 * finishで変更した内容を永続層から取得して確認する。
	 * <p>
	 * [TodoService#finish]
	 * <ol>
	 * <li>todoIdを使って永続層に登録したデータが取得できること.</li>
	 * <li>本メソッド実行後、永続層から取得したFinishedがtrueとなっていること.</li>
	 * </ol>
	 * </p>
	 */
	@Test
	public void finish() {

		// String todoId = UUID.randomUUID().toString();
		// Date createdAt = new Date();
		// String todoTitle ="test_finish";
		// Boolean Finished = false;
		//
		// Todo resultTodo = jdbcTemplate
		// .queryForObject(
		// "SELECT todo_id,todo_title,finished,created_at FROM TODO WHERE todo_id = :todoId",
		// new MapSqlParameterSource().addValue("todoId",Todo.getTodoId()),
		// new BeanPropertyRowMapper<Todo>(Todo.class));
		//
		//
		//
		// Todo finishedTodo = todoService.finish(todo.getTodoId());
		//
		// Todo resultTodo = jdbcTemplate
		// .queryForObject(
		// "SELECT todo_id,todo_title,finished,created_at FROM TODO WHERE todo_id = :todoId",
		// new MapSqlParameterSource().addValue("todoId",
		// finishedTodo.getTodoId()),
		// new BeanPropertyRowMapper<Todo>(Todo.class));
		//
		// assertThat(resultTodo.isFinished(), is(finishedTodo.isFinished()));
	}

	/**
	 * finish実行時、既にFinishedがtrueだった場合、exceptionをthrowするか確認する。
	 * <p>
	 * [TodoService#finish]
	 * <ol>
	 * <li>BusinessExceptionをthrowすること.</li>
	 * <li>BusinessExceptionにmessages"[E002] The requested Todo is already
	 * finished. (id="+ todoId + ")"が付与されていること.</li>
	 * </ol>
	 * </p>
	 */
	@Test
	public void finish_exception() {
	}

	/**
	 * deleteで１件削除できているか確認する。
	 * <p>
	 * 　 [TodoService#delete]
	 * <ol>
	 * <li>todoIdを使って永続層に登録したデータが取得できること.</li>
	 * <li>todoIdで指定した内容を永続層から削除していること.</li>
	 * </ol>
	 * </p>
	 */
	@Test
	public void delete() {
	}
	

}
