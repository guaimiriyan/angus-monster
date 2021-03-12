package d_ProxyModel.dbDemo.db;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName dbSourceEntry.java
 * @Description TODO
 * @createTime 2021年03月12日 23:48:00
 */
public class dbSourceEntry {

    static final String DEFAULT = null;

    private dbSourceEntry(){}

    private static final ThreadLocal<String> dbSourece = new ThreadLocal<>();

    public static String getSource(){
        return dbSourece.get();
    }

    public static void setSource(String source){
         dbSourece.set(source);
    }

    public static void restore(){
        dbSourece.set(DEFAULT);
    }
}
