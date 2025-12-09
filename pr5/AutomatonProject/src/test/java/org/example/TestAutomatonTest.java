package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAutomatonTest {

    @ParameterizedTest(name = "Input: {0} -> Expected: {1}")
    @CsvSource({

            "abcTESTabc, F",
            "abcTES, 3",

            "randomText, 0",   // nothing
            "T, 1",            // T match
            "TE, 2",           // TE match
            "TES, 3",          // TES match
            "TEST, F",         // absoloute match

            // hard part
            "TT, 1",           // first letter repeat
            "TET, 1",          // miss on 3rd letter, but its new word start(TE T...)
            "TESA, 0",         // miss at end
            "TESTEST, F",      // double test must return F

            //empty check
            "'', 0",
    })
    void testAutomatonStates(String input, String expectedState) {

        TestAutomaton automaton = new TestAutomaton();

        if (input != null && input.equals("NULL")) input = null;

        TestAutomaton.State result = automaton.process(input);

        assertEquals(expectedState, result.toString());
    }
}