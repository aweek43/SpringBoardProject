//package springBoardProject.context;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springBoardProject.sqlService.ContextSqlService;
//import springBoardProject.sqlService.SqlService;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class SqlServiceContext {
//    @Bean
//    public SqlService sqlService() {
//        ContextSqlService sqlService = new ContextSqlService();
//
//        Map<String, String> sqlMap = new HashMap<String, String>();
//        sqlMap.put("userAdd", "insert into users(id, password) values(?,?)");
//        sqlMap.put("userGet", "select * from users where id = ? and password = ?");
//        sqlMap.put("userDeleteAll", "delete from users");
//        sqlMap.put("userGetCount", "select count(*) from users");
//
//        sqlMap.put("postAdd", "insert into posts(author, title, content, generatedDate, revisedDate) values(?,?,?,?,?)");
//        sqlMap.put("postGet", "select * from posts where id = ?");
//        sqlMap.put("postDeleteAll", "delete from posts");
//        sqlMap.put("postGetCount", "select count(*) from posts");
//        sqlMap.put("postGetAll", "select * from posts");
//        sqlMap.put("postGetName", "select * from posts where author = ?");
//        sqlMap.put("postGetPage", "select * from posts order by generatedDate desc limit ?, 5");
//        sqlMap.put("postUpdate", "update posts set title = ?, content = ?, revisedDate = ? where id = ?");
//
//        sqlService.setSqlMap(sqlMap);
//
//        return sqlService;
//    }
//}
