package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) { // 회원 추가를 누르면 진행되는 로직이다.
        String sql = "insert into member(name) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; // ResultSet 결과를 받는것이다.

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // DB에 인서트를 할 때 이것을 써야 값을 얻는다.

            pstmt.setString(1, member.getName());

            pstmt.executeUpdate(); // 쿼리를 실행시키는 문 (insert 를 실행시키는 문)
            rs = pstmt.getGeneratedKeys(); // 자동으로 들어가는 ID 수를 꺼내준다.

            if (rs.next()) {
                member.setId(rs.getLong(1)); // getLong 을 사용해서 회원이 추가 될때 마다 id가 1씩 올라가며 추가된다.
            } else {
                throw new SQLException("id 조회 실패"); // 실패를 하게 되면 id 조회 실패가 뜨게 된다.
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e); // 객체의 현재 상황에서 메서드 호출이 유효하지 않을 때 throw 되는 예외처리이다.
        } finally {
            close(conn, pstmt, rs); // save 로직문을 끝내면서 Connection prepareStatement, ResultSet 을 종료 즉 닫아준다.
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?"; // id를 찾기 때문에 id에 번호를 주어 조회를 하는 로직이다.

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery(); //(String 문) SELECT(조회)문을 전송할 떄 사용하는 메서드로, ResultSet 자료형의 데이터를 반환한다.

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery(); // Select문을 실행하는 명령문

            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource); // 여기서 dataSource를 이용해서 SpringConfig 에 연결을 해서
                               // 여기서 등록해둔 쿼리명령문들을 진행을 하게되서 웹에서 쿼리 명령문을 실행 할 수 있다.
    }
}