import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GoodsManager {

    // 1. 데이터베이스 연결 정보를 static final 상수로 선언
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tje";
    private static final String DB_USER = "user1";
    private static final String DB_PASSWORD = "tjee1234";

    // JDBC 드라이버는 프로그램 시작 시 한 번만 로드하면 되므로 static 초기화 블록 사용
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC 드라이버 로딩 실패");
            e.printStackTrace();
        }
    }

    /**
     * 데이터베이스 커넥션을 생성하여 반환하는 메서드
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * 📦 모든 상품 목록을 조회하여 출력하는 메서드
     */
    public void listAllGoods() {
        System.out.println("\n--- 📦 전체 상품 목록 ---");
        String sql = "SELECT * FROM goods ORDER BY id";

        // try-with-resources: try() 안에 선언된 자원은 자동으로 close() 됨
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("ID: %d, 상품명: %s, 가격: %,d원, 재고: %d개%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.err.println("DB 조회 중 오류 발생");
            e.printStackTrace();
        }
        System.out.println("-------------------------");
    }

    /**
     * 🔍 상품 ID를 받아 특정 상품의 상세 정보를 출력하는 메서드
     * @param goodsId 조회할 상품의 ID
     */
    public void viewGoodsById(int goodsId) {
        System.out.printf("\n--- 🔍 상품 ID(%d) 상세 정보 ---%n", goodsId);
        // SQL Injection 공격을 방지하기 위해 PreparedStatement 사용
        String sql = "SELECT * FROM goods WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, goodsId); // SQL의 첫 번째 ?에 goodsId 값을 설정

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // 결과가 있다면
                    System.out.printf("  - 상품명: %s%n", rs.getString("name"));
                    System.out.printf("  - 가  격: %,d원%n", rs.getInt("price"));
                    System.out.printf("  - 재  고: %d개%n", rs.getInt("stock"));
                } else { // 결과가 없다면
                    System.out.println("해당 ID의 상품을 찾을 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            System.err.println("DB 상세 조회 중 오류 발생");
            e.printStackTrace();
        }
        System.out.println("-----------------------------");
    }

    public static void main(String[] args) {
        GoodsManager manager = new GoodsManager();
        Scanner scanner = new Scanner(System.in);

        // 1. 전체 목록 보여주기
        manager.listAllGoods();

        // 2. 사용자로부터 ID 입력받기
        try {
            System.out.print("\n상세 조회할 상품의 ID를 입력하세요: ");
            int inputId = scanner.nextInt();

            // 3. 상세 조회 메서드 호출
            manager.viewGoodsById(inputId);

        } catch (InputMismatchException e) {
            System.err.println("잘못된 입력입니다. 숫자만 입력해주세요.");
        } finally {
            scanner.close(); // 스캐너 자원 해제
        }
    }
}