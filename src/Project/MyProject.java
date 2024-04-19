 package Project;
 import java.util.*;

 public class MyProject {
     public static void main(String[] args) {
         HashMap<String, Integer> map = new HashMap<String, Integer>();
         //define initial state
         State RooT = new State(0,3,3, map, null);
         //calling BFS method
         BFS(RooT);
         //calling DFS method
         DFS(RooT);
         System.out.println("Note:\n "
         		+ "The letter C symbolizes for Cannibals.\n"
         		+ "The letter M symbolizes for Missionaries.\n"
         		+ "The minus sign (-) symbolizes the river\n"
         		+ "");
     }

     public static class State {
         int BoatStat;
         //num of cannibals on left
         int NCL;
         //num of missionaries on left
         int NML;
         //num of cannibals on Right
         int NCR;
         //num of missionaries on right
         int NMR;
         State ParenT = null;
         ArrayList <State> childState = new ArrayList<State>();

         public State (int Boat, int Misionary, int Canibals, HashMap<String, Integer> map, State parent) {
             map.put(""+Boat+Misionary+Canibals,1);
             BoatStat = Boat;
             NCL = Canibals;
             NML = Misionary;
             NCR = 3 - Canibals;
             NMR = 3 - Misionary;
             ParenT = parent;
             //num of cannibals
             int nC = NCL;
             //num of missionaries
             int nM = NML;
             if (BoatStat == 1) {
                 nC = NCR;
                 nM = NMR;
             }

             //when boat on left we represent boat as 1
             //and whet boat is right we represent it as 0
             if (nC >= 2) {
                 if (BoatStat == 0) { // Boat on the left
                     String str = "1" + nM + (nC - 2);
                     if (ValidityOfNewState(3 - nM, nC - 2) && !map.containsKey(str)) {
                         childState.add(new State(1, nM, nC - 2, map, this));
                     }
                 } else { // Boat on the right
                     String str = "0" + (3 - nM) + (3 - (nC - 2));
                     if (ValidityOfNewState(3 - nM, 3 - (nC - 2)) && !map.containsKey(str)) {
                         childState.add(new State(8, 3 - nM, 3 - (nC - 2), map, this));
                     }
                 }
             }
             if (nM >= 2) {
                 // Boat on the left
                 if (BoatStat == 0) {
                     String str = "1" + (nM - 2) + nC;
                     if (ValidityOfNewState(nM - 2, nC) && !map.containsKey(str)) {
                         childState.add(new State(1, nM - 2, nC, map, this));
                     }
                     // Boat on the right
                 } else {
                     String str = "0" + (3 - (nM - 2)) + (3 - nC);
                     if (ValidityOfNewState(3 - (nM - 2), 3 - nC) && !map.containsKey(str)) {
                         childState.add(new State(0, 3 - (nM - 2), 3 - nC, map, this));
                     }
                 }
             }
             if (nM >= 1 && nC >= 1) {
                 // Boat on the left
                 if (BoatStat == 0) {
                     String str = "1" + (nM - 1) + (nC - 1);
                     if (ValidityOfNewState(nM - 1, nC - 1) && !map.containsKey(str)) {
                         childState.add(new State(1, nM - 1, nC - 1, map, this));
                     }
                     // Boat on the right
                 } else {
                     String str = "0" + (3 - (nM - 1)) + (3 - (nC - 1));
                     if (ValidityOfNewState(3 - (nM - 1), 3 - (nC - 1)) && !map.containsKey(str)) {
                         childState.add(new State(0, 3 - (nM - 1), 3 - (nC - 1), map, this));
                     }
                 }
             }
             if (nM >= 1) {
                 if (BoatStat == 0) { // Boat on the left
                     String str = "1" + (nM - 1) + nC;
                     if (ValidityOfNewState(nM - 1, nC) && !map.containsKey(str)) {
                         childState.add(new State(1, nM - 1, nC, map, this));
                     }
                 } else { // Boat on the right
                     String str = "0" + (3 - (nM - 1)) + (3 - nC);
                     if (ValidityOfNewState(3 - (nM - 1), 3 - nC) && !map.containsKey(str)) {
                         childState.add(new State(0, 3 - (nM - 1), 3 - nC, map, this));
                     }
                 }
             }
             if (nC >= 1) {
                 if (BoatStat == 0) { // Boat on the left
                     String str = "1" + nM + (nC - 1);
                     if (ValidityOfNewState(nM, nC - 1) && !map.containsKey(str)) {
                         childState.add(new State(1, nM, nC - 1, map, this));
                     }
                 } else { // Boat on the right
                     String str = "0" + (3 - nM) + (3 - (nC - 1));
                     if (ValidityOfNewState(3 - nM, 3 - (nC - 1)) && !map.containsKey(str)) {
                         childState.add(new State(0, 3 - nM, 3 - (nC - 1), map, this));
                     }
                 }
             }
         }
     }
     //to determine the validity or invalidity of this condition.
     //It depends on the number of missionaries on both banks of the river being equal to
     // or more than the number of cannibals.
     public static boolean ValidityOfNewState(int m, int c) {//
         if ((c > m && m != 0) || (3 - c > 3 - m && 3 - m != 0)) {
             return false;
         }
         return true;
     }
     //using BFS to presented a steps
     public static void BFS(State realRoot) {
         Queue<State> queue = new LinkedList<>();
         Set<State> arrived = new HashSet<>();
         queue.add(realRoot);
         boolean found = false;
         State goal = null;
         while (!queue.isEmpty()) {
             State root = queue.remove();
             if (root.NMR == 3 && root.NCR == 3) {
                 goal = root;
                 found = true;
                 break;
             }
             arrived.add(root);
             for (int j = 0; j < root.childState.size(); j++) {
                 queue.add(root.childState.get(j));
             }
         }
         System.out.println("-------- By BFS search --------");
         if (found && goal != null) {
             while (goal != null) {
                 System.out.println( StringMultiplier("C", goal.NCL) +
                         StringMultiplier("M", goal.NML)
                         + "----" + StringMultiplier("C", goal.NCR)
                         + StringMultiplier("M", goal.NMR));
                 goal = goal.ParenT;
             }
         }
     }

 //using  DFS to presented a steps

     public static boolean DFS(State realRoot) {
         Stack<State> stack = new Stack<>();
         Set<State> visited = new HashSet<>();
         stack.push(realRoot);
         boolean found = false;
         State goal = null;

         while (!stack.isEmpty()) {
             State root =stack.pop();
             if (root.NMR == 3 && root.NCR == 3) {
                 goal = root;
                 found = true;
                 break;
             }

             visited.add(root);
             for (int j = 0; j < root.childState.size(); j++) {
                 stack.push(root.childState.get(j));
             }
         }
         System.out.println("-------- By DFS search --------");
         if (found && goal != null) {
             while (goal != null) {
                 System.out.println( StringMultiplier("C", goal.NCL) +
                         StringMultiplier("M", goal.NML)
                         + "----" + StringMultiplier("C", goal.NCR) +
                         StringMultiplier("M", goal.NMR));
                 goal = goal.ParenT;
             }
         }
         return false ;
     }
     //method for printing the results
     public static String StringMultiplier(String str, int n) {
         StringBuilder newStr = new StringBuilder();
         for (int i = 0; i < n; i++) {
             newStr.append(str);
         }
         return newStr.toString();
     }
 }