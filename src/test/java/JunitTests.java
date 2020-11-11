import com.revature.Accounts.AccountServiceImp;
import com.revature.Accounts.BankAccount;
import com.revature.Utils.Adresses.Address;
import com.revature.Utils.DatabaseConnection;
import com.revature.Utils.PasswordEncoder;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class JunitTests {
    @Test
    public void addressTests() {
        DatabaseConnection db = new DatabaseConnection();
        InputStream testInput = new ByteArrayInputStream("43040 713 MilcrestDrive Y".getBytes());
        Address address = new Address(testInput);
        assert (address.getPostalCode().equals("43040"));
        assert (address.getState().equals("OH"));
    }

    @Test
    public void accountTests() {
        BankAccount testAccount = new BankAccount(1);
        testAccount.setBalance(1000);
        AccountServiceImp ASI = new AccountServiceImp();
        ASI.withdraw(testAccount, 500);
        assert (testAccount.getBalance() == 500);
    }

    @Test
    public void passWordEncoderTest(){
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        assert(passwordEncoder.confirmPass("Hello","$argon2i$v=19$m=2048,t=10,p=10$Woqus8ldynqzpaYwFOmShQ$KwaDffnsmO7uAkvct6s9+dq4pDcLXTRlD4Xj+zvBP+c"));
        assert(!passwordEncoder.confirmPass("hello","$argon2i$v=19$m=2048,t=10,p=10$Woqus8ldynqzpaYwFOmShQ$KwaDffnsmO7uAkvct6s9+dq4pDcLXTRlD4Xj+zvBP+c"));
    }

    @Test
    public void DBConTests(){
        //Testing that DatabaseConnection is a singleton.
        DatabaseConnection db = new DatabaseConnection();
        assert(db.hasConnection());
    }
}
