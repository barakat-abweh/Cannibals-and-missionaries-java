import java.util.LinkedList;  
import java.util.Queue;  
  
public class MCB4 {  
    // the state queue used for search  
    private Queue<State> q = new LinkedList<State>();  
      
    // State class   
    private class State {  
        private int missionary;  // the number of  missionaries between 0 and 4  
        private int cannibals;  // the number of cannibals between 0 and 4  
        private int boat;   // the number of boat between 0 and 1  
        private State previous; // the previous state used to record the state transfer path  
        private int stateLevel; // state level used to record the height of the tree  
          
        // constructor  
        public State(int missionary, int cannibals, int boat, int stateLevel) {  
            this(missionary, cannibals, boat, null, stateLevel);  
        }  
          
        // constructor  
        public State(int missionary, int cannibals, int boat, State preState, int stateLevel) {  
            this.missionary = missionary;  
            this.cannibals = cannibals;  
            this.boat = boat;  
            this.previous = preState;  
            this.stateLevel = stateLevel;  
        }  
          
        // get the state level  
        public int getStateLevel() {  
            return this.stateLevel;  
        }  
          
        // is the state a valid state  
        public boolean isValid() {  
            if (missionary < 0 || missionary > 4 || cannibals < 0 || cannibals > 4) return false;  
            if(4 - missionary < 0 || 4 - missionary > 4 || 4 - cannibals < 0 || 4 - cannibals > 4) return false;  
            if(missionary > 0 && cannibals > missionary) return false;  
            if(4 - missionary > 0 && 4 - cannibals > 4 - missionary) return false;  
            return true;  
        }  
          
        // is two states equal to each other  
        public boolean isEqual(State s) {  
            return (s.missionary == this.missionary &&   
                    s.cannibals == this.cannibals &&  
                    s.boat == this.boat);  
        }  
          
        // is the state is the goal state  
        public boolean isGoal() {  
            State goal = new State(0, 0, 0, 99999);  
            return isEqual(goal);  
        }  
          
        // print the path  
        public void print() {  
            if(this.previous != null) {  
                this.previous.print();  
            }  
              
            String side = this.boat == 1 ? " Boat Right -> " : " <- Boat Left ";  
            System.out.println(this.missionary + "M/" + this.cannibals + "C " + side + "" +   
            (4 - this.missionary) + "M/" + (4 - this.cannibals) + "C");  
        }  
    }  
      
    // expand the successors from the current state and enqueue these successor states  
    private void successors(State currentState) {  
        for(int i = 0; i <= 3; i++) {  
            for(int j = 0; j <= 3; j++) {  
                if(i == 0 && j == 0) continue;  
                if(i + j > 3) break;  
                addStatetoQueue(currentState, i, j);  
            }  
        }  
    }  
      
    // generate a new state and enqueue the state  
    private void addStatetoQueue(State preState, int missionary, int cannibal) {  
        // TODO Auto-generated method stub  
        int side = preState.boat == 1 ? -1 : 1;  
        State state = new State(preState.missionary + side * missionary,   
                preState.cannibals + side * cannibal, 1 - preState.boat, preState,  
                preState.getStateLevel() + 1);  
        if(state.isValid())  
            q.add(state);  
    }  
      
    // solve the mcb problem with bfs  
    public void solve() {  
        //ArrayList<State> solutions = new ArrayList<State>();  
        boolean isFirstFound = false;   // is the state first time found  
        boolean isallFound = false; // is all the states found  
        int optimalLevel = 0;   // the state level  
        int count = 0;  // solution count  
          
        State start = new State(4, 4, 1, 1);    // start state  
        q.add(start);  
          
        while(!q.isEmpty() && !isallFound) {  
            State current = q.remove();  
              
            if(current.isGoal()) {  
                if(isFirstFound) {  
                    if(current.getStateLevel() <= optimalLevel) {  
                        count++;  
                        System.out.println("The "+ count + "th solution ===");  
                        current.print();  
                        System.out.println();  
                    } else {  
                        isallFound = true;  
                    }  
                } else {  
                    isFirstFound = true;  
                    optimalLevel = current.getStateLevel();  
                    count++;  
                    System.out.println("The "+ count + "th solution, the level is " + optimalLevel + " ======" );  
                    current.print();  
                    System.out.println();  
                }  
                  
            } else {  
                successors(current);  
            }  
        }  
    }  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        MCB4 mcb = new MCB4();  
        mcb.solve();  
    }  
  
}  