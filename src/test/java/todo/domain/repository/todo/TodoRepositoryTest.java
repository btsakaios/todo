package todo.domain.repository.todo;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TodoRepositoryTest {
	
	@Rule
	public Timeout timeout = new Timeout(100);

	/*
	 * 観点
	 * findAllメソッド実行後、データべ―スに登録されているデータをすべて取得していか確認する。
	 * 方法
	 * DBのデータ完全消去後、データを2件投入する。
	 * その後findAllメソッドを実行し、先に投入したデータ2件が取得できているか確認する。
	 */
	@Test
	public void findAll() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
		ResultSet rs = con.createStatement().executeQuery
				("SELECT todo_id, todo_title, finished, created_at FROM todo");
		
	}

	/*
	 * 観点
	 * findOneメソッド実行後、データを取得できているか確認する。
	 * 方法
	 * DBのデータ完全消去後、データを1件投入する。
	 * 投入したデータのtodoIdを引数としてfindOneを実行し、データが所得できるか確認する。
	 */
	@Test
	public void findOne() {
		fail("Not yet implemented");
	}

	/*
	 * 観点
	 * createメソッド実行後、データが登録できているか確認する。
	 * 方法
	 * DBのデータ完全消去後、任意のTodoを引数としてcreateメソッド実行する。
	 * DBのデータと引数のTodoを比較し、等しいか確認する。
	 */
	@Test
	public void create() {
		fail("Not yet implemented");
	}

	/*
	 * 観点
	 * updateメソッド実行後、データが更新できているか確認する。
	 * 方法
	 * DBのデータ完全消去後、データを1件投入する。
	 * 投入したデータとtodo_idのみ同一であるTodoを引数としてupdateメソッド実行する。
	 * DBのデータと引数のTodoを比較し、等しいか確認する。
	 */
	@Test
	public void update() {
		fail("Not yet implemented");
	}

	/*
	 * 観点
	 * deleteメソッド実行後、データが削除できているか確認する。
	 * 方法
	 * DBのデータ完全消去後、データを2件投入する。
	 * 投入した片方のデータを引数としてdeleteメソッド実行する。
	 * 引数として指定したデータがDBから削除されているか確認する。
	 */
	@Test
	public void delete() {
		fail("Not yet implemented");
	}

	/*
	 * 観点
	 * countByFinishedメソッド実行後、指定したfinishedの総数が取得できるか確認する。
	 * 方法
	 * DBのデータ完全消去後、finished=trueのデータ2件とfinished=falseのデー1件を投入する。
	 * finished=falseのデータを引数としてcountByFinishedメソッドを実行する。
	 * メソッドの返り値として1が返却されるか確認する。
	 */
	@Test
	public void countByFinished() {
		fail("Not yet implemented");
	}
}
