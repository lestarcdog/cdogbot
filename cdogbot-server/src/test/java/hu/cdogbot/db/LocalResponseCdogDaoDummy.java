package hu.cdogbot.db;

import org.postgresql.ds.PGSimpleDataSource;

/**
 * Created by cdog on 2016.07.19..
 */
public class LocalResponseCdogDaoDummy extends ResponseCdogDao {
    public LocalResponseCdogDaoDummy() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setDatabaseName("cdogbot");
        ds.setUser("postgres");
        ds.setPassword("postgres");
        ds.setPortNumber(5432);

        this.cdogbotDs = ds;
    }
}
