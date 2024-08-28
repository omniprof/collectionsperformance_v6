package com.kenfogel.performance.loaders;

import java.util.*;

import com.butlerpress.dict.Dictionary;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Rewritten to be able to do the benchmark internally for the Swing
 * display or by using Java Microbenchmark Harness
 *
 * Performs a set of tests to determine the performance of an array, array
 * list, arraydeque, and linked list. Does multiple REPETITIONS on data
 * structures of a given SIZE. Displays the results in a table
 *
 * Uses Dictionary.jar Copyright (c) 2004-2007 Scott Willson that generates
 * random words from a set of words derived from a public source
 * dictionary. See Dictionary.jar for more information. In
 * src/main/resources/batch_file is a batch file to add the Dictionary.jar
 * file to your local maven repository.
 *
 * Creates a new data structure for each repetition of each test that
 * writes to the data structure.
 *
 * @author Ken Fogel
 * @version 6.0
 *
 */
// Use this annotation if you declare non-static variables in this class
//@State(Scope.Benchmark)
public class SequenceTests_jmh {

    private final static int SIZE = 1000;

    /**
     * A static class with this annotation performs any tasks that should
     * be performed either before or after tests. To avoid constant folding
     * variables used in a method being benchmarked should come from this
     * class.
     */
    @State(Scope.Thread)
    public static class StateValues {

        public String[] dataArray;
        public String string;

        public String[] array0;
        public String[] array1;

        public ArrayList<String> arrayList0;
        public ArrayList<String> arrayList1;

        public ArrayDeque<String> arrayDeque0;
        public ArrayDeque<String> arrayDeque1;

        public LinkedList<String> linkedList0;
        public LinkedList<String> linkedList1;

        public long startTime, endTime;
        public long runningTime;

        public final int pos = SIZE / 2;
        public final int capacity = SIZE * 2;

        public int counter = 0;

        /**
         * Load words from the dictionary into a set to eliminate
         * duplication and then into an array. Declare and initialize the
         * state variables for the benchmark methods. This method is called
         * for every iteration/invocation
         */
        @Setup(Level.Invocation)
        public void doLoadDataSet() {
            // Load Array
            dataArray = new String[SIZE];
            array0 = new String[SIZE];
            array1 = new String[SIZE];

            arrayList0 = new ArrayList<>(capacity);
            arrayList1 = new ArrayList<>(capacity);

            arrayDeque0 = new ArrayDeque(capacity);
            arrayDeque1 = new ArrayDeque(capacity);

            linkedList0 = new LinkedList<>();
            linkedList1 = new LinkedList<>();

            HashSet<String> dataSet = new HashSet<>();
            for (int x = 0; x < SIZE; ++x) {
                do { // This loop will repeat if the random word is not unique
                    string = Dictionary.getRandomWordTermCommonNameOrConnector();
                } while (!dataSet.add(string));
                dataArray[x] = string;
                array0[x] = string;
                arrayList0.add(string);
                arrayDeque0.add(string);
                linkedList0.add(string);
            }
        }
    }

    @Benchmark
    public void do02aLoadArray(StateValues state, Blackhole blackhole) {
        // Load Array
        for (int x = 0; x < SIZE; ++x) {
            state.array1[x] = state.dataArray[x];
        }
        // The JMH Blackhole when passed a value in the method
        // should block dead code removal. You can also just 
        // return a value such as >>return state.hashMap1; <<
        blackhole.consume(state.array1);
    }

    @Benchmark
    public void do02bAccessFirstElementArray(StateValues state, Blackhole blackhole) {
        // Access first element
        state.string = state.array0[0];
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do02cAccessLastElementArray(StateValues state, Blackhole blackhole) {
        // Access last element
        state.string = state.array0[SIZE - 1];
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do02dAccessMiddleElementArray(StateValues state, Blackhole blackhole) {
        // Access middle element
        state.string = state.array0[state.pos];
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do03aLoadArrayList(StateValues state, Blackhole blackhole) {
        // Load ArrayList
        for (int x = 0; x < SIZE; ++x) {
            state.arrayList1.add(state.dataArray[x]);
        }
        // The JMH Blackhole when passed a value in the method
        // should block dead code removal. You can also just 
        // return a value such as >>return state.hashMap1; <<
        blackhole.consume(state.array1);
    }

    @Benchmark
    public void do03bAccessFirstElementArrayList(StateValues state, Blackhole blackhole) {
        // Access first element
        state.string = state.arrayList0.get(0);
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do03cAccessLastElementArrayList(StateValues state, Blackhole blackhole) {
        // Access last element
        state.string = state.arrayList0.get(SIZE - 1);
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do03dAccessMiddleElementArrayList(StateValues state, Blackhole blackhole) {
        // Access middle element
        state.string = state.arrayList0.get(state.pos);
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do03eInsertFirstElementArrayList(StateValues state, Blackhole blackhole) {
        // Insert at start
        state.arrayList0.addFirst("Dawson College");
        blackhole.consume(state.arrayList0);
    }

    @Benchmark
    public void do03fInsertLastElementArrayList(StateValues state, Blackhole blackhole) {
        // Insert at end
        state.arrayList0.addLast("Dawson College");
        blackhole.consume(state.arrayList0);
    }

    @Benchmark
    public void do03gInsertMiddleElementArrayList(StateValues state, Blackhole blackhole) {
        // Insert in middle
        state.arrayList0.add(state.pos, "Dawson College");
        blackhole.consume(state.arrayList0);
    }

    @Benchmark
    public void do04aLoadDeque(StateValues state, Blackhole blackhole) {
        // Load ArrayDeque
        for (int x = 0; x < SIZE; ++x) {
            state.arrayDeque1.add(state.dataArray[x]);
        }
        // The JMH Blackhole when passed a value in the method
        // should block dead code removal. You can also just 
        // return a value such as >>return state.hashMap1; <<
        blackhole.consume(state.arrayDeque1);
    }

    @Benchmark
    public void do04bAccessFirstElementDeque(StateValues state, Blackhole blackhole) {
        // Access first element
        state.string = state.arrayDeque0.getFirst();
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do04cAccessLastElementDeque(StateValues state, Blackhole blackhole) {
        // Access last element
        state.string = state.arrayDeque0.getLast();
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do04dInsertFirstElementDeque(StateValues state, Blackhole blackhole) {
        // Insert at start
        state.arrayDeque0.addFirst("Dawson College");
        blackhole.consume(state.arrayDeque0);
    }

    @Benchmark
    public void do04eInsertLastElementDeque(StateValues state, Blackhole blackhole) {
        // Insert at end
        state.arrayDeque0.addLast("Dawson College");
        blackhole.consume(state.arrayDeque0);
    }

    @Benchmark
    public void do05aLoadLinkedList(StateValues state, Blackhole blackhole) {
        // Load LinkedList
        for (int x = 0; x < SIZE; ++x) {
            state.linkedList1.add(state.dataArray[x]);
        }
        // The JMH Blackhole when passed a value in the method
        // should block dead code removal. You can also just 
        // return a value such as >>return state.hashMap1; <<
        blackhole.consume(state.linkedList1);
    }

    @Benchmark
    public void do05bAccessFirstElementLinkedList(StateValues state, Blackhole blackhole) {
        // Access first element
        state.string = state.linkedList0.getFirst();
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do05cAccessLastElementLinkedList(StateValues state, Blackhole blackhole) {
        // Access last element
        state.string = state.linkedList0.getLast();
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do05dAccessMiddleElementLinkedList(StateValues state, Blackhole blackhole) {
        state.string = state.linkedList0.get(state.pos);
        blackhole.consume(state.string);
    }

    @Benchmark
    public void do05eInsertFirstElementLinkedList(StateValues state, Blackhole blackhole) {
        // Insert at start
        state.linkedList0.addFirst("Dawson College");
        blackhole.consume(state.linkedList0);
    }

    @Benchmark
    public void do05fInsertLastElementLinkedList(StateValues state, Blackhole blackhole) {
        // Insert at end
        state.linkedList0.addLast("Dawson College");
        blackhole.consume(state.linkedList0);
    }

    @Benchmark
    public void do05gInsertMiddleElementLinkedList(StateValues state, Blackhole blackhole) {
        // Insert in the middle
        state.linkedList0.add(state.pos, "Dawson College");
        blackhole.consume(state.linkedList0);
    }
}
