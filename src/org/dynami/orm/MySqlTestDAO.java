package org.dynami.orm;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySqlTestDAO {
	HikariDataSource ds;
	Employ employ01 = new Employ(1, "Alessandro", "Atria", new Date(), false, 40_000.0);
	Employ employ02 = new Employ(2, "Anna", "Mazzone", new Date(), false, 50_000.0);
	Employ employ03 = new Employ(3, "Deletable03", "Deletable03", new Date(), false, 0.0);
	Employ employ04 = new Employ(4, "Deletable04", "Deletable04", new Date(), false, 0.0);
	Employ employ05 = new Employ(5, "Deletable05", "Deletable05", new Date(), false, 0.0);
	Employ employ06 = new Employ(6, "Updatable06", "Updatable06", new Date(), false, 0.0);
	Employ employ07 = new Employ(7, "Insertable07", "Insertable07", new Date(), false, 0.0);
	
	@Before
	public void setUp() throws Exception {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://192.168.1.100:3306/test");
		config.setUsername("root");
		config.setPassword("root");
		config.setIdleTimeout(0);
		config.setMaxLifetime(0);
		config.setMaximumPoolSize(1);
		ds = new HikariDataSource(config);
		
		DAO.$.setUp(DAO.SqlDialect.MySql, ds, c->c.close());
		DAO.$.save(employ01);
		DAO.$.save(employ02);
		DAO.$.save(employ03);
		DAO.$.save(employ04);
		DAO.$.save(employ05);
		DAO.$.save(employ06);
	}

	@After
	public void tearDown() throws Exception {
//		DAO.$.executeNativeSQL("drop table employ");
		ds.close();
	}

	@Test
	public final void testLoad() throws Exception {
		Employ e = DAO.$.load(Employ.class, 1);
		Assert.assertNotNull(e);
		Assert.assertEquals("Alessandro", e.getName());
	}

	@Test
	public final void testGet() throws Exception {
		Employ e = new Employ();
		e.setId(1);
		e = DAO.$.get(e);
		Assert.assertNotNull(e);
		Assert.assertEquals("Alessandro", e.getName());
	}

	@Test
	public final void testUpdateT() throws Exception {
		Date now = new Date();
		employ01.setBirthdate(now);
		
		int actual = DAO.$.update(employ01);
		int expected = 1;
		
		Assert.assertEquals(expected, actual);
	}

	@Test
	public final void testUpdateTStringArray() throws Exception {
		employ06.setSurname("Pippo");
		employ06.setSalary(10_000);
		int result = DAO.$.update(employ06, "surname");
		Employ e = DAO.$.get(employ06);
		Assert.assertEquals(1, result);
		Assert.assertNotEquals("Pippo", e.getSurname());
		Assert.assertEquals("Updatable06", e.getSurname());
		Assert.assertEquals(10_000, e.getSalary(), 0.0);
	}

	@Test
	public final void testDeleteT() throws Exception {
		int result = DAO.$.delete(employ03);
		Assert.assertEquals(1, result);
	}

	@Test
	public final void testInsert() throws Exception {
		int result = DAO.$.insert(employ07);
		Assert.assertEquals(1, result);
	}

	@Test
	public final void testDeleteCriteriaOfT() throws Exception {
		int result = DAO.$.delete(new Criteria<>(Employ.class).andEquals("id", 4));
		Assert.assertEquals(1, result);
	}

	@Test
	public final void testSelectCriteriaOfT() throws Exception {
		List<Employ> list = DAO.$.select(new Criteria<>(Employ.class).andIn("id", 1, 2));
		Assert.assertEquals(2, list.size());
	}

	@Test
	public final void testSelectFirstCriteriaOfT() throws Exception {
		Employ employ = DAO.$.selectFirst(new Criteria<>(Employ.class).orderBy("id"));
		Assert.assertEquals("Alessandro", employ.getName());
	}

	@Test
	public final void testSelectCriteriaOfTConsumerOfT() throws Exception {
		StringBuilder builder = new StringBuilder();
		DAO.$.select(new Criteria<>(Employ.class).andIn("id", 1, 2), (e)->{
			builder.append(e.getSurname());
		});
		Assert.assertEquals("AtriaMazzone", builder.toString());
	}

	@Test
	public final void testNumber() throws Exception {
		int number = DAO.$.number(Integer.class, "select count(*) from employ where surname = 'Atria'");
		Assert.assertEquals(1, number);
	}

	@Test
	public final void testSelectClassOfTStringObjectArray() throws Exception {
		List<Employ> list = DAO.$.select(Employ.class, "select * from employ where id in (?, ?)", 1, 2);
		StringBuilder builder = new StringBuilder();
		list.forEach((e)->{
			builder.append(e.getSurname());
		});
		Assert.assertEquals("AtriaMazzone", builder.toString());
	}

	@Test
	public final void testSelectFirstClassOfTStringObjectArray() throws Exception {
		Employ employ = DAO.$.selectFirst(Employ.class, "select * from employ where id < ? order by id asc", 3);
		Assert.assertEquals(1, employ.getId());
	}

	@Test
	public final void testSaveObject01() throws Exception {
		boolean inserted = DAO.$.save(employ01);
		
		Assert.assertFalse(inserted);
	}
	
	@Test
	public final void testSaveObject02() throws Exception {
		boolean inserted = DAO.$.save(employ02);
		
		Assert.assertFalse(inserted);
	}

	@Test
	public final void testInsertIfNotExistT() throws Exception {
		boolean notExist = DAO.$.insertIfNotExist(employ01, new Criteria<>(Employ.class).andEquals("id", 1));
		Assert.assertFalse(notExist);
	}

	@Test
	public final void testExists() throws Exception{
		boolean exist = DAO.$.exists(new Criteria<>(Employ.class).andEquals("name", "Alessandro"));
		Assert.assertEquals(true, exist);
	}

	@Test
	public final void testExecuteNativeSQLString() throws Exception {
		DAO.$.executeNativeSQL("update employ set name=\"Annina\" where id = 2" );
		Employ e = DAO.$.load(Employ.class, 2);
		Assert.assertNotNull(e);
		Assert.assertEquals("Annina", e.getName());
	}

	@Test
	public final void testExecuteNativeSQLStringObjectArray() throws Exception {
		DAO.$.executeNativeSQL("delete from employ where id = ?", 7);
		
	}
}
