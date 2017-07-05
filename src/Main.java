import com.sun.org.apache.bcel.internal.generic.FALOAD;
import test.Floor;
import test.Point;
import test.WeightedGraph;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.*;
import test.Utils;

import static test.Utils.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Floor floor1 = new Floor();
        WeightedGraph t = buildFromFile("src/data/test_points");
        floor1.setGraph(t);

    }

}
