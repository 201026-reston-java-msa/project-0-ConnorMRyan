import com.revature.Utils.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MokitoTests {


        @InjectMocks private DatabaseConnection dbConnection;
        @Mock private Connection mockConnection;
        @Mock private Statement mockStatement;

        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
        }
        @Test
        public void dbTests() throws Exception{
            String SQL = "SELECT Balance FROM Accounts.Checking WHERE CheckingID = ?";
            Mockito.when(dbConnection.getResult(SQL,""+3)).thenReturn(mockStatement.getResultSet());
            ResultSet rs = mockStatement.getResultSet();
            rs.next();
            int val = rs.getInt("Balance");
            assert(val == 2000);


        }
    }


