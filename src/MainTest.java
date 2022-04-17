import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.*;

public class MainTest {


    @ParameterizedTest
    @ArgumentsSource(ArgForTest.class)
    void getSimilarStringsTest(Set<String> sublist1, Set<String> sublist2, List<String> expectedList) {
        List<String> actualList = Main.getSimilarStrings(sublist1, sublist2);
        Assertions.assertEquals(expectedList, actualList);
    }

    static class ArgForTest implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            Arguments arguments1 = Arguments.of(
                    new LinkedHashSet() {{
                        add("Бетон с присадкой");
                    }},
                    new LinkedHashSet() {{
                        addAll(asList("присадка для бетона", "доставка"));
                    }},
                    new ArrayList<>() {{addAll(asList("Бетон с присадкой:присадка для бетона",
                            "доставка:?"));}}
            );
            Arguments arguments2 = Arguments.of(
                    new LinkedHashSet() {{
                        addAll(asList("гвоздь", "шуруп","краска синяя", "ведро для воды"));
                    }},
                    new LinkedHashSet() {{
                        addAll(asList("краска", "корыто для воды", "шуруп 3х1.5"));
                    }},
                    new ArrayList<>() {{addAll(asList("гвоздь:?", "шуруп:шуруп 3х1.5",
                            "краска синяя:краска", "ведро для воды:корыто для воды"));}}
            );
            Arguments arguments3 = Arguments.of(
                    new HashSet<>() {{
                        add("Бетон с присадкой");
                    }},
                    new HashSet<>() {{
                        addAll(asList("присадка бля бетона", "доставка"));
                    }},
                    new ArrayList<>() {{addAll(asList("Бетон с присадкой:присадка бля бетона",
                            "доставка:?"));}}
            );
            return Stream.of(arguments1, arguments2, arguments3);
        }

    }
}
