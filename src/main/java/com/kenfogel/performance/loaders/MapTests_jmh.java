package com.kenfogel.performance.loaders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

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
 * Performs a set of tests to determine the performance of an HashMap and
 * TreeMap
 *
 * Displays the results in a table
 *
 * Uses Dictionary.jar Copyright (c) 2004-2007 Scott Willson that generates
 * random words from a set of words derived from a public source
 * dictionary. See Dictionary.jar for more information. In
 * src/main/resources/batch_file is a batch file to add the Dictionary.jar
 * file to your local maven repository.
 *
 * @author Ken Fogel
 * @version 6.0
 *
 */
public class MapTests_jmh {

    private final static int SIZE = 1000;
    private final static int SEARCH_SIZE = 10;

    /**
     * A static class with this annotation performs any tasks that should
     * be performed either before or after tests. To avoid constant folding
     * variables used in a method being benchmarked should come from this
     * class.
     */
    @State(Scope.Thread)
    public static class StateValues {

        public Set<String> dataSet;
        public Set<String> searchSet;
        public String[] dataArray;
        public String string;

        public long startTime, endTime, runningTime;
        public HashMap<String, String> hashMap0;
        public HashMap<String, String> hashMap1;
        public TreeMap<String, String> treeMap0;
        public TreeMap<String, String> treeMap1;

        public Iterator<String> it;

        /**
         * Load words from the dictionary into a set to eliminate
         * duplication and then into an array. Declare and initialize the
         * state variables for the benchmark methods. This method is called
         * for every iteration/invocation
         */
        @SuppressWarnings("empty-statement")
        @Setup(Level.Invocation)
        public void doLoadDataSet() {
            Random random = new Random();
            // Load Array
            dataSet = new HashSet<>();
            dataArray = new String[SIZE];
            for (int x = 0; x < SIZE; ++x) {
                do { // This loop will repeat if the random word in not unique
                    string = Dictionary.getRandomWordTermCommonNameOrConnector();
                } while (!dataSet.add(string));
                dataArray[x] = string;
            }
            hashMap0 = new HashMap<>();
            treeMap0 = new TreeMap<>();
            for (int x = 0; x < SIZE; ++x) {
                hashMap0.put(dataArray[x], dataArray[x]);
                treeMap0.put(dataArray[x], dataArray[x]);
            }
            treeMap1 = new TreeMap<>();
            hashMap1 = new HashMap<>();

            searchSet = new HashSet<>();
            for (int x = 0; x < SEARCH_SIZE; ++x) {
                while (!searchSet.add(dataArray[random.nextInt(SIZE)]));
            }
            it = searchSet.iterator();
        }
    }

    @Benchmark
    public void do01LoadHashMap(StateValues state, Blackhole blackhole) {
        // Load Data
        for (int x = 0; x < SIZE; ++x) {
            state.hashMap1.put(state.dataArray[x], state.dataArray[x]);
        }
        // The JMH Blackhole when passed a value in the method
        // should block dead code removal. You can also just 
        // return a value such as >>return state.hashMap1; <<
        blackhole.consume(state.hashMap1);
    }

    @Benchmark
    public void do02AddToHashMap(StateValues state, Blackhole blackhole) {
        // Add to Hash Map
        state.hashMap0.put("KenF", "KenF");
        blackhole.consume(state.hashMap0);
    }

    @Benchmark
    public void do03HashMapSearch(StateValues state, Blackhole blackhole) {
        // Find SEARCH_SIZE elements in HashMap
        while (state.it.hasNext()) {
            state.string = state.hashMap0.get(state.it.next());
        }
        blackhole.consume(state.hashMap0);
    }

    @Benchmark
    public void do04LoadTreeMap(StateValues state, Blackhole blackhole) {
        // Load Tree Map
        for (int x = 0; x < SIZE; ++x) {
            state.treeMap1.put(state.dataArray[x], state.dataArray[x]);
        }
        // The JMH Blackhole when passed a value in the method
        // should block dead code removal. You can also just 
        // return a value such as >>return state.hashMap1; <<
        blackhole.consume(state.treeMap1);
    }

    @Benchmark
    public void do05AddToTreeMap(StateValues state, Blackhole blackhole) {
        // Add to Tree Map
        state.treeMap0.put("kenF", "kenF");
        blackhole.consume(state.treeMap0);
    }

    @Benchmark
    public void do06TreeMapSearch(StateValues state, Blackhole blackhole) {
        // Find SEARCH_SIZE elements in Tree
        while (state.it.hasNext()) {
            state.string = state.treeMap0.get(state.it.next());
        }
        blackhole.consume(state.string);
    }

}
