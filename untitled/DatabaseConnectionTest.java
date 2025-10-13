import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionTest  {
    public static void main(String[] args) {
        // 데이터베이스 연결 정보
        String jdbcUrl = "jdbc:mysql://localhost:3306/tje"; // 데이터베이스 URL
        String dbUser = "user1";  // 데이터베이스 사용자 이름
        String dbPassword = "tjee1234";  // 데이터베이스 비밀번호

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. JDBC 드라이버 로딩
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 데이터베이스 연결
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            if (connection != null) {
                System.out.println("데이터베이스에 성공적으로 연결되었습니다!");
            }

            // 3. SQL 쿼리를 실행하기 위한 Statement 객체 생성
            statement = connection.createStatement();

            // 4. 실행할 SQL 쿼리 작성
            String sql = "SELECT id, name, price, stock FROM goods";

            // 5. 쿼리 실행 및 결과 받기 (SELECT 쿼리는 executeQuery 사용)
            resultSet = statement.executeQuery(sql);

            System.out.println("\n--- 상품 목록 ---");
            // 6. 결과(ResultSet) 처리
            while (resultSet.next()) {
                // 각 컬럼의 데이터를 타입에 맞게 가져옴
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int stock = resultSet.getInt("stock");

                // 가져온 데이터 출력
                System.out.println("ID: " + id + ", 상품명: " + name + ", 가격: " + price + ", 재고: " + stock);
            }
            System.out.println("-----------------");


        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ 데이터베이스 연결 또는 쿼리 중 오류가 발생했습니다.");
            e.printStackTrace();
        } finally {
            // 7. 사용한 자원(Resource) 정리 (연결의 역순으로)
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                    System.out.println("🔌 데이터베이스 연결이 종료되었습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}