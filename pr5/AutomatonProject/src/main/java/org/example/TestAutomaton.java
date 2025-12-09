package org.example;

public class TestAutomaton {

    //possible states
    public enum State {
        S0("0"), // Початковий
        S1("1"), // T
        S2("2"), // TE
        S3("3"), // TES
        F("F");  // TEST (Final)

        private final String output;

        State(String output) {
            this.output = output;
        }

        @Override
        public String toString() {
            return output;
        }
    }

    private State currentState = State.S0;

    //main method
    public State process(String input) {
        currentState = State.S0; //cleaning before every launch

        if (input == null || input.isEmpty()) {
            return State.S0;
        }

        for (char c : input.toCharArray()) {
            nextState(c);
            //exiting if TEST already found
            if (currentState == State.F) {
                break;
            }
        }
        return currentState;
    }

    //transition function
    private void nextState(char c) {
        switch (currentState) {
            case S0:
                if (c == 'T') currentState = State.S1;
                break;

            case S1: //T
                if (c == 'E') currentState = State.S2;
                else if (c == 'T') currentState = State.S1; //TT must stay on T
                else currentState = State.S0;
                break;

            case S2: //TE
                if (c == 'S') currentState = State.S3;
                else if (c == 'T') currentState = State.S1; //TET must start new T
                else currentState = State.S0;
                break;

            case S3: //TES
                if (c == 'T') currentState = State.F; //succsess
                else currentState = State.S0; //TESA  must 0
                break;

            case F:
                break;
        }
    }
}