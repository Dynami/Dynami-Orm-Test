package org.dynami.orm;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Sqlite3TestDAO {
	HikariDataSource ds;
	Employ employ01 = new Employ(1, "Alessandro", "Atria", new Date(), false, 40_000.0);
	Employ employ02 = new Employ(2, "Anna", "Mazzone", new Date(), false, 50_000.0);
	
	@Before
	public void setUp() throws Exception {
		final File databaseFile = new File("test.db");
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setPoolName("SQLiteConnectionPool");
		hikariConfig.setDriverClassName("org.sqlite.JDBC");
		hikariConfig.setJdbcUrl("jdbc:sqlite:" + databaseFile.getAbsolutePath());
		ds = new HikariDataSource(hikariConfig);
		DAO.$.setUp(ds);
		
	}

	@After
	public void tearDown() throws Exception {
		ds.close();
	}

	@Test
	public final void testLoad() throws Exception {
		Employ e = DAO.$.load(Employ.class, 1);
		Assert.assertNotNull(e);
		Assert.assertEquals(1, e.getId());
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
	public final void testUpdateTStringArray() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDeleteT() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInsert() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDeleteCriteriaOfT() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSelectCriteriaOfT() {
		
	}

	@Test
	public final void testSelectFirstCriteriaOfT() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSelectCriteriaOfTConsumerOfT() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testNumber() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSelectClassOfTStringObjectArray() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSelectFirstClassOfTStringObjectArray() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSaveObject() throws Exception {
		boolean inserted = DAO.$.save(employ01);
		Assert.assertTrue(inserted);
	}

	@Test
	public final void testSaveTCriteriaOfT() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testExists() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testExecuteNativeSQLString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testExecuteNativeSQLStringObjectArray() {
		fail("Not yet implemented"); // TODO
	}

}
