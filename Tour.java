/**
 * This class creates a Tour of Points using a 
 * Linked List implementation.  The points can
 * be inserted into the list using two heuristics.
 * @author XX (ADD YOUR NAME HERE)
 * @author Layla Oesper, modified code 09-22-2017
 */

public class Tour {

    /** A helper class that defines a single node for use in a tour.
     * A node consists of a Point, representing the location of that
     * city in the tour, and a pointer to the next Node in the tour.
     */
    private class Node {
        private Point p;
        private Node next;
	
        /** Constructor creates a new Node at the given Point newP
         * with an intital next value of null.
         * @param newP - the point to associate with the Node.
         */
        public Node(Point newP) {
            p = newP;
            next = null;
        }

        /** Constructor creates a new Node at the given Point newP
         * with the specified next node.
         * @param newP - the point to associate with the Node.
         * @param nextNode - the nextNode this node should point to.
         */
        public Node(Point newP, Node nextNode) {
            p = newP;
            next = nextNode;
        }
	
        /**
         * Return the Point associated with this Node. 
         * (Same value can also be accessed from a Node object node
         * using node.p)
         * @return The Point object associated with this node.
         */
        public Point getPoint() {
            return p;
        }
        
        /**
         * Return the next Node associated with this Node. 
         * (Same value can also be accessed from a Node object node
         * using node.next)
         * @return The next Node object linked from this node..
         */
	   public Node getNext() {
	       return next;
	   }
          
    } // End Node class
    

    // Tour class Instance variables
    private Node head;
    private int size; //number of nodes
    //Add other instance variables you think might be useful.
    
    
    /**
     * Constructor for the Tour class.  By default sets head to null.
     */
    public Tour() {
        head = null;
        size = 0;
    }
    
    // ADD YOUR METHODS BELOW HERE
    /**
     * @return Returns the list of points in the tour.
     */
    public String toString() {
    	String a = "";
    	Node n = head.getNext();
    	while(n != null) {
    		//Adds the point that the node points to to the string.
    		a += "Point: " + n.getPoint().toString() + "\n";
    		n = n.getNext();
    	}
    	return a;
    }
    /**
     * Loops through linked list and draws lines between all points.
     */
    public void draw() {
    	Node n = head.getNext();
    	//N.getNext because it looks at the node after n as well.
    	while(n.getNext() != null) {
    		//Creates a point at the point n references.
    		n.getPoint().draw();
    		//Draws a line from n to the next point in the linked list
    		n.getPoint().drawTo(n.getNext().getPoint());
    		//Changes n to the next node.
    		n = n.getNext();
    	}
    	//Draws a line from the last node in the list to the first node.
    	n.getPoint().draw();
    	n.getPoint().drawTo(head.getNext().getPoint());
    }
    
    /**
     * @return Returns the number of nodes in the linked list.
     */
    public int size() {
    	return size;
    }
    
    /**
     * 
     * @return The total distance from all the nodes in the list
     */
    public double distance() {
    	double dis = 0.0;
    	Node n = head.getNext();
    	while (n.getNext() != null) {
    		//Finds distance between n and the the next node in the list.
    		dis += n.getPoint().distanceTo(n.getNext().getPoint());
    		//Increments n so it references the next node in the list.
    		n = n.getNext();
    	}
    	//Finds the distance between the last point and the first point.
    	dis += n.getPoint().distanceTo(head.getNext().getPoint());
    	return dis;
    }
    /**
     * @param P The point to insert into the list.
     */
    public void insertNearest(Point P) {
    	//If the list is empty then the point will be added at the first index.
    	if(size == 0) {
    		//Creates new node to be inserted
    		Node n = new Node(P);
    		//Head now points to the new node
    		head = new Node(null, n);
    		size++;
    		return;
    	}
    	double minDist = Double.MAX_VALUE;
    	Node n = head.next;
    	Node min = n;
    	while(n != null) {
    		//If smallest distance is found then update smallest distance and make 
    		//Node min assigned to the Node before the smallest distance.
    		if(n.getPoint().distanceTo(P) < minDist) {
    			minDist = n.getPoint().distanceTo(P);
    			min = n;
    		}
    		//N now equals the next node in the list.
    		n = n.getNext();
    	}
    	//Creates new node that points at node after min.
    	Node p = new Node(P, min.getNext());
    	//Min is assigned to point at new node.
    	min.next = p;
    	size++;
    }
    
    public void insertSmallest(Point p) {
    	//If the list is empty then the pointed will be added at the first index
    	if(size == 0) {
    		//Creates new node to be inserted
    		Node n = new Node(p);
    		//Head now points to the new node
    		head = new Node(null, n);
    		size++;
    		return;
    	}
    	double smallestDist = Double.MAX_VALUE;
    	Node n = head.next;
    	Node min = n;
    	//Creates double with total distance in the linked list w/o new point.
    	double totalDist = distance();
    	while(n.getNext() != null) {
    		//Creates double with new distance if N was inserted at a specific point in the list
    		double newDist = totalDist - n.getPoint().distanceTo(n.getNext().getPoint()) +
   			     n.getPoint().distanceTo(p) + p.distanceTo(n.getNext().getPoint());
    		//Compares new distance with the smallest distance and if smaller, the value is changed.
    		if(newDist < smallestDist) {
    			smallestDist = newDist;
    			min = n;
    		}
    		n = n.getNext();
    	}
    	//Checks to see if p should be added between the last and first points in the linked list
    	double newDist = totalDist - n.getPoint().distanceTo(head.next.getPoint()) +
  			     n.getPoint().distanceTo(p) + p.distanceTo(head.next.getPoint());
   		if(newDist < smallestDist) {
   			smallestDist = newDist;
   			min = n;
   		}
   		//Creates new node pointing to node after where it will be added.
    	Node newp = new Node(p, min.getNext());
    	//Node before where it is added now points to the new node.
    	min.next = newp;
    	size++;
    }
    
    
    // ADD YOUR METHODS ABOVE HERE
   
    public static void main(String[] args) {
        /* Use your main() function to test your code as you write it. 
         * This main() will not actually be run once you have the entire
         * Tour class complete, instead you will run the NearestInsertion
         * and SmallestInsertion programs which call the functions in this 
         * class. 
         */
        
        
        //One example test could be the follow (uncomment to run):
        
        Tour tour = new Tour();
        Point p = new Point(0,0);
        tour.insertNearest(p);
        p = new Point(0,100);
        tour.insertNearest(p);
        p = new Point(100, 100);
        tour.insertNearest(p);
        p = new Point(10,10);
        tour.insertSmallest(p);
        System.out.println("Tour distance =  "+tour.distance());
        System.out.println("Number of points = "+tour.size());
        System.out.println(tour);
        
         

        // the tour size should be 3 and the distance 341.42 (don't forget to include the trip back
        // to the original point)
    
        // uncomment the following section to draw the tour, setting w and h to the max x and y 
        // values that occur in your tour points
	
        
        int w = 100 ; //Set this value to the max that x can take on
        int h = 100 ; //Set this value to the max that y can take on
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);
        StdDraw.setPenRadius(.005);
        tour.draw(); 
        
    }
   
}