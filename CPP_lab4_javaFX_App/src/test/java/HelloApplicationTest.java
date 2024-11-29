import lab3.cpp_lab4_javafx_app.HelloApplication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelloApplicationTest {

    @Test
    void testApplicationStart() {
        // Запуск програми
        assertDoesNotThrow(() -> HelloApplication.main(new String[]{}));
    }
}
