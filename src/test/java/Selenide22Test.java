import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class Selenide22Test {

    String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    }

    @Test
    public void regCardTest() {
        String date = generateDate(6);
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Самара");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(date);
        $("[data-test-id='name'] input").setValue("Стороженко Юлия Алексеевна");
        $("[data-test-id='phone'] input").setValue("+79998887766");
        $("[data-test-id='agreement']").click();
        $$("[type='button']").find(Condition.exactText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
