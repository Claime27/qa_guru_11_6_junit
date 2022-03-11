package guru.qa;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

public class ParametrizedSearchtest {
    @BeforeEach
    void precondition() {
        Selenide.open("https://ya.ru");

    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @ValueSource(strings = {"Selenide", "Junit5"})
    @ParameterizedTest(name = "{0}")
    void commonsearchTest(String testData) {
        Selenide.$("#text").setValue(testData);
        Selenide.$("button[type='submit']").click();
        Selenide.$$("li.serp-item").find(Condition.text(testData)).shouldBe(Condition.visible);
    }


    @CsvSource({
            "Selenide, Java",
            "Junit 5 , IntelliJ IDEA"
    })
    @ParameterizedTest(name = "{0}")
    void complexSearchTest(String testData, String expectedText) {
        Selenide.$("#text").setValue(testData);
        Selenide.$("button[type='submit']").click();
        Selenide.$$("li.serp-item").find(Condition.text(expectedText)).shouldBe(Condition.visible);
    }

    static Stream<Object> mixedArgumentsTestsDataProvider(){
        return Stream.of(
             Arguments.of("Selenide", List.of(1,2,4), true),
             Arguments.of("Junit 5", List.of(5,6,7), false)
                );
    }

    @MethodSource(value = "mixedArgumentsTestsDataProvider")
    @ParameterizedTest(name = "Name {2}")
    void mixedArgumentsTests(String firstArg, List<Integer> secondArg, boolean aBooleanValue){
        System.out.println("String:" + firstArg + " list: " + secondArg.toString() + " boolean: " + aBooleanValue);
    }
}