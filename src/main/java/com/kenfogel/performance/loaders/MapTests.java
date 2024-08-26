package com.kenfogel.performance.loaders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import com.butlerpress.dict.Dictionary;
import com.kenfogel.performance.models.MapSpeedTableModel;

/**
 * Rewritten to be able to do the benchmark internally for the Swing display or
 * by using Java Microbenchmark Harness
 *
 * Performs a set of tests to determine the Big-O performance of an HashMap and
 * TreeMap
 *
 * Does multiple REPETITIONS on data structures of a given SIZE
 *
 * Displays the results in a table
 *
 * Uses Dictionary.jar Copyright (c) 2004-2007 Scott Willson that generates
 * random words from a set of words derived from a public source dictionary. See
 * Dictionary.jar for more information. In src/main/resources/batch_file is a
 * batch file to add the Dictionary.jar file to your local maven repository.
 *
 * Using Java 21 used the copy constructor for Hashmap and TreeMap.
 *
 * Created a new data structure for each repetition of each test.
 *
 * @author Ken Fogel
 * @version 6.0
 *
 */
public class MapTests {

    private final static int REPETITIONS = 1000;
    private final static int SIZE = 1000;
    private final static int SEARCH_SIZE = 10;

    private final MapSpeedTableModel mapSpeedTableModel;
    private Set<String> dataSet;
    private Set<String> searchSet;
    private String[] dataArray;
    private String string;

    private final Random random;

    private long startTime, endTime, runningTime;
    private HashMap<String, String> hashMap0;
    private HashMap<String, String> hashMap1;
    private TreeMap<String, String> treeMap0;
    private TreeMap<String, String> treeMap1;

    /**
     * Constructor Receives reference to the table model that will hold the
     * results
     *
     * @param mapSpeedTableModel
     */
    public MapTests(MapSpeedTableModel mapSpeedTableModel) {
        this.mapSpeedTableModel = mapSpeedTableModel;
        random = new Random();
        loadDataSet();
        loadSearchSet();
    }

    /**
     * Load a set with random values and copy the values to an array The array
     * is used to select a SEARCH_SIZE of random words to search for
     */
    private void loadDataSet() {
        // Load Array
        dataSet = new HashSet<>();
        dataArray = new String[SIZE];
        for (int x = 0; x < SIZE; ++x) {
            do { // This loop will repeat if the random word in not unique
                string = Dictionary.getRandomWordTermCommonNameOrConnector();
                dataArray[x] = string;
            } while (!dataSet.add(string));
        }
        hashMap0 = new HashMap<>();
        for (int x = 0; x < SIZE; ++x) {
            hashMap0.put(dataArray[x], dataArray[x]);
        }
        treeMap0 = new TreeMap<>(hashMap0);
    }

    /**
     * Select SEARCH_SIZE random words from the array to use in the search test
     */
    @SuppressWarnings("empty-statement")
    private void loadSearchSet() {
        searchSet = new HashSet<>();
        for (int x = 0; x < SEARCH_SIZE; ++x) {
            while (!searchSet.add(dataArray[random.nextInt(SIZE)]));
        }
    }

    public void do01LoadHashMap() {
        runningTime = 0;
        for (int r = 0; r < REPETITIONS; ++r) {
            hashMap1 = new HashMap<>();
            startTime = System.nanoTime();
            // Load Data
            for (int x = 0; x < SIZE; ++x) {
                hashMap1.put(dataArray[x], dataArray[x]);
            }
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
    }

    public void do02AddToHashMap() {
        runningTime = 0;
        for (int r = 0; r < REPETITIONS; ++r) {
            hashMap1 = new HashMap<>(hashMap0);
            startTime = System.nanoTime();
            hashMap1.put("KenF", "KenF");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
    }

    public void do03HashMapSearch() {
        runningTime = 0;
        for (int r = 0; r < REPETITIONS; ++r) {
            hashMap1 = new HashMap<>(hashMap0);
            startTime = System.nanoTime();
            Iterator<String> it = searchSet.iterator();
            while (it.hasNext()) {
                string = hashMap1.get(it.next());
            }
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
    }

    /**
     * Carry out the tests on the hash map
     */
    public void doHashMapTests() {
        do01LoadHashMap();
        mapSpeedTableModel.setValueAt(runningTime / REPETITIONS, 0, 1);
        do02AddToHashMap();
        mapSpeedTableModel.setValueAt(runningTime / REPETITIONS, 1, 1);
        do03HashMapSearch();
        mapSpeedTableModel.setValueAt(runningTime / REPETITIONS, 2, 1);
    }

    public void do04LoadTreeMap() {
        runningTime = 0;
        for (int r = 0; r < REPETITIONS; ++r) {
            treeMap1 = new TreeMap<>();
            startTime = System.nanoTime();
            // Load Tree Map
            for (int x = 0; x < SIZE; ++x) {
                treeMap1.put(dataArray[x], dataArray[x]);
            }
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
    }

    public void do05AddToTreeMap() {
        runningTime = 0;
        for (int r = 0; r < REPETITIONS; ++r) {
            treeMap1 = new TreeMap<>(treeMap0);
            startTime = System.nanoTime();
            // Add Tree Map
            treeMap1.put("kenF", "kenF");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
    }

    public void do06TreeMapSearch() {
        runningTime = 0;
        for (int r = 0; r < REPETITIONS; ++r) {
            treeMap1 = new TreeMap<>(treeMap0);
            startTime = System.nanoTime();
            // Find SEARCH_SIZE elements
            Iterator<String> it = searchSet.iterator();
            while (it.hasNext()) {
                string = treeMap1.get(it.next());
            }
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
    }

    /**
     * Carry out the tests on a tree map
     */
    public void doTreeMapTests() {
        do04LoadTreeMap();
        mapSpeedTableModel.setValueAt(runningTime / REPETITIONS, 0, 2);
        do05AddToTreeMap();
        mapSpeedTableModel.setValueAt(runningTime / REPETITIONS, 1, 2);
        do06TreeMapSearch();
        mapSpeedTableModel.setValueAt(runningTime / REPETITIONS, 2, 2);
    }
}
