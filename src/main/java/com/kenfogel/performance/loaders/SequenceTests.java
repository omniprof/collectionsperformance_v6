package com.kenfogel.performance.loaders;

import com.butlerpress.dict.Dictionary;
import com.kenfogel.performance.models.SequenceSpeedTableModel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

/**
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
 * Created a new data structure for each repetition of each test that
 * writes to the data structure.
 *
 * @author Ken Fogel
 * @version 6.0
 *
 */
public class SequenceTests {

    private final static int SIZE = 1000;
    private final static int REPETITIONS = 1000;
    private String[] dataArray;
    private int[] intArray0;
    private SequenceSpeedTableModel sequenceSpeedTableModel = null;
    private ArrayList<String> arrayList0;
    private long runningTime;
    private final int pos = SIZE / 2;
    private Random rnd;

    /**
     * Constructor Receives reference to the table model that will hold the
     * results
     *
     * @param sequenceSpeedTableModel
     */
    public SequenceTests(SequenceSpeedTableModel sequenceSpeedTableModel) {
        rnd = new Random();
        this.sequenceSpeedTableModel = sequenceSpeedTableModel;
        loadArrays();
    }

    /**
     * Load words from the dictionary into a set to eliminate duplication
     * and then into an array
     */
    public final void loadArrays() {

        String string;

        // Load Array
        dataArray = new String[SIZE];
        intArray0 = new int[SIZE];
        arrayList0 = new ArrayList<>(SIZE * 2);

        HashSet<String> dataSet = new HashSet<>();

        rnd = new Random();
        for (int x = 0; x < SIZE; ++x) {
            do { // This loop will repeat if the random word is not unique
                string = Dictionary.getRandomWordTermCommonNameOrConnector();
            } while (!dataSet.add(string));
            dataArray[x] = string;
            intArray0[x] = rnd.nextInt();
            arrayList0.add(string);
        }
    }

    private String[] do02LoadArray() {
        String[] array1 = new String[SIZE];
        long startTime, endTime;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Load the array
            for (int x = 0; x < SIZE; ++x) {
                array1[x] = dataArray[x];
            }
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return array1;
    }

    private String do02AccessFirstElementArray() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        String[] array1 = Arrays.copyOf(dataArray, SIZE);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access first element
            string = array1[0];
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private String do02AccessLastElementArray() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        String[] array1 = Arrays.copyOf(dataArray, SIZE);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access last element
            string = array1[SIZE - 1];
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private String do02AccessMiddleElementArray() {
        long startTime, endTime;
        String string = "";
        String[] array1 = Arrays.copyOf(dataArray, SIZE);
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access middle element
            string = array1[pos];
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    /**
     * Perform tests on an array
     */
    public void doArrayTests() {

        do02LoadArray();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 0, 1);
        do02AccessFirstElementArray();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 1, 1);
        do02AccessLastElementArray();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 2, 1);
        do02AccessMiddleElementArray();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 3, 1);

        // Does not support insert at start
        sequenceSpeedTableModel.setValueAt(-1L, 4, 1);
        // Does not support insert at end
        sequenceSpeedTableModel.setValueAt(-1L, 5, 1);
        // Does not support insert at middle
        sequenceSpeedTableModel.setValueAt(-1L, 6, 1);
    }

    private ArrayList<String> do03LoadArrayList() {
        long startTime, endTime;
        ArrayList<String> arrayList1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            arrayList1 = new ArrayList<>(SIZE);
            startTime = System.nanoTime();
            // Load ArrayList
            for (int x = 0; x < SIZE; ++x) {
                arrayList1.add(arrayList0.get(x));
            }
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return arrayList1;
    }

    private String do03AccessFirstElementArrayList() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        ArrayList<String> arrayList1 = new ArrayList<>(SIZE * 2);
        arrayList1.addAll(arrayList0);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access first element
            string = arrayList1.get(0);
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private String do03AccessLastElementArrayList() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        ArrayList<String> arrayList1 = new ArrayList<>(SIZE * 2);
        arrayList1.addAll(arrayList0);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access last element
            string = arrayList1.get(SIZE - 1);
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private String do03AccessMiddleElementArrayList() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        ArrayList<String> arrayList1 = new ArrayList<>(SIZE * 2);
        arrayList1.addAll(arrayList0);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access middle element
            string = arrayList1.get(pos / 2);
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private ArrayList<String> do03InsertFirstElementArrayList() {
        long startTime, endTime;
        ArrayList<String> arrayList1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            arrayList1 = new ArrayList<>(SIZE * 2);
            arrayList1.addAll(arrayList0);
            startTime = System.nanoTime();
            // Insert at start
            arrayList1.addFirst("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return arrayList1;
    }

    private ArrayList<String> do03InsertLastElementArrayList() {
        long startTime, endTime;
        ArrayList<String> arrayList1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            arrayList1 = new ArrayList<>(SIZE * 2);
            arrayList1.addAll(arrayList0);
            startTime = System.nanoTime();
            // Insert at end
            arrayList1.add("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return arrayList1;
    }

    private ArrayList<String> do03InsertMiddleElementArrayList() {
        long startTime, endTime;
        ArrayList<String> arrayList1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            arrayList1 = new ArrayList<>(SIZE * 2);
            arrayList1.addAll(arrayList0);
            startTime = System.nanoTime();
            // Insert in middle
            arrayList1.add(pos, "Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return arrayList1;
    }

    /**
     * Perform tests on an ArrayList
     */
    public void doArrayListTests() {
        do03LoadArrayList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 0, 2);
        do03AccessFirstElementArrayList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 1, 2);
        do03AccessLastElementArrayList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 2, 2);
        do03AccessMiddleElementArrayList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 3, 2);
        do03InsertFirstElementArrayList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 4, 2);
        do03InsertLastElementArrayList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 5, 2);
        do03InsertMiddleElementArrayList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 6, 2);
    }

    private ArrayDeque<String> do04LoadDeque() {
        long startTime, endTime;
        ArrayDeque<String> arrayDeque1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            arrayDeque1 = new ArrayDeque<>(SIZE * 2);
            startTime = System.nanoTime();
            // Load ArrayDeque
            for (int x = 0; x < SIZE; ++x) {
                arrayDeque1.add(arrayList0.get(x));
            }
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return arrayDeque1;
    }

    private String do04AccessFirstElementDeque() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        ArrayDeque<String> arrayDeque1 = new ArrayDeque<>(SIZE * 2);
        arrayDeque1.addAll(arrayList0);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access first element
            string = arrayDeque1.getFirst();
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private String do04AccessLastElementDeque() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        ArrayDeque<String> arrayDeque1 = new ArrayDeque<>(SIZE * 2);
        arrayDeque1.addAll(arrayList0);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access last element
            string = arrayDeque1.getLast();
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private ArrayDeque<String> do04InsertFirstElementDeque() {
        long startTime, endTime;
        ArrayDeque<String> arrayDeque1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            arrayDeque1 = new ArrayDeque<>(SIZE * 2);
            arrayDeque1.addAll(arrayList0);
            startTime = System.nanoTime();
            // Insert at start
            arrayDeque1.addFirst("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return arrayDeque1;
    }

    private ArrayDeque<String> do04InsertLastElementDeque() {
        long startTime, endTime;
        ArrayDeque<String> arrayDeque1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            arrayDeque1 = new ArrayDeque<>(SIZE * 2);
            arrayDeque1.addAll(arrayList0);
            startTime = System.nanoTime();
            // Insert at end
            arrayDeque1.addLast("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return arrayDeque1;
    }

    /**
     * Perform tests on an ArrayDeque
     */
    public void doDequeTests() {

        do04LoadDeque();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 0, 3);
        do04AccessFirstElementDeque();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 1, 3);
        do04AccessLastElementDeque();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 2, 3);
        // Access middle element not supported
        sequenceSpeedTableModel.setValueAt(-1L, 3, 4);
        do04InsertFirstElementDeque();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 4, 3);
        do04InsertLastElementDeque();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 5, 3);

        // Does not support insert at middle
        sequenceSpeedTableModel.setValueAt(-1L, 6, 3);
    }

    private LinkedList<String> do05LoadLinkedList() {
        long startTime, endTime;
        LinkedList<String> linkedList1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            linkedList1 = new LinkedList<>();
            startTime = System.nanoTime();
            // Load LinkedList
            for (int x = 0; x < SIZE; ++x) {
                linkedList1.add(arrayList0.get(x));
            }
            linkedList1 = new LinkedList<>(Arrays.asList(dataArray));
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return linkedList1;
    }

    private String do05AccessFirstElementLinkedList() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        LinkedList<String> linkedList1 = new LinkedList<>(arrayList0);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access first element
            string = linkedList1.getFirst();
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private String do05AccessLastElementLinkedList() {
        long startTime, endTime;
        String string = "";
        runningTime = 0;
        LinkedList<String> linkedList1 = new LinkedList<>(arrayList0);
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access last element
            string = linkedList1.getLast();
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private String do05AccessMiddleElementLinkedList() {
        long startTime, endTime;
        String string = "";
        LinkedList<String> linkedList1 = new LinkedList<>(arrayList0);
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            startTime = System.nanoTime();
            // Access middle element
            string = linkedList1.get(pos);
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return string;
    }

    private LinkedList<String> do05InsertFirstElementLinkedList() {
        long startTime, endTime;
        LinkedList<String> linkedList1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            linkedList1 = new LinkedList<>(arrayList0);
            startTime = System.nanoTime();
            // Insert at start
            linkedList1.addFirst("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return linkedList1;
    }

    private LinkedList<String> do05InsertLastElementLinkedList() {
        long startTime, endTime;
        LinkedList<String> linkedList1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            linkedList1 = new LinkedList<>(arrayList0);
            startTime = System.nanoTime();
            // Insert at end
            linkedList1.addLast("Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return linkedList1;
    }

    private LinkedList<String> do05InsertMiddleElementLinkedList() {
        long startTime, endTime;
        LinkedList<String> linkedList1 = null;
        runningTime = 0;
        for (int i = 0; i < REPETITIONS; ++i) {
            linkedList1 = new LinkedList<>(arrayList0);
            startTime = System.nanoTime();
            // Insert in middle
            linkedList1.add(pos, "Dawson College");
            endTime = System.nanoTime() - startTime;
            runningTime += endTime;
        }
        return linkedList1;
    }

    /**
     * Perform tests on a LinkedList
     */
    public void doLinkedListTests() {

        do05LoadLinkedList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 0, 4);
        do05AccessFirstElementLinkedList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 1, 4);
        do05AccessLastElementLinkedList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 2, 4);
        do05AccessMiddleElementLinkedList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 3, 4);
        do05InsertFirstElementLinkedList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 4, 4);
        do05InsertLastElementLinkedList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 5, 4);
        do05InsertMiddleElementLinkedList();
        sequenceSpeedTableModel.setValueAt(runningTime / REPETITIONS, 6, 4);
    }
}
