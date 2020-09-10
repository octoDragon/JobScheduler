import edu.princeton.cs.algs4.StdIn;

import java.util.LinkedList;

public class Schedule {
    //parameters
    static LinkedList<Integer> collection = new LinkedList<Integer>();
    static Employee pq[];
    static double numberOfEmployees = 0;

    //methods
    public static Employee[] makePQArray(LinkedList<Integer> collection,
                                         Employee[] pq,
                                         double numberOfEmployees) {
        //reading in the data using StdIn
        while (!StdIn.isEmpty()) {
            numberOfEmployees += 0.25; // each employee has four components
            int i = Integer.parseInt(StdIn.readString());
            collection.add(i);
        }

        pq = new Employee[(int) numberOfEmployees];
        Integer[] employeeElements = new Integer[(int) numberOfEmployees * 4];
        for (int i = 0; i < employeeElements.length; i++) {
            employeeElements[i] = collection.removeFirst();
        }

        for (int i = 0; i < numberOfEmployees; i++) {
            int k = i;
            k *= 4;
            Employee employee = new Employee();
            employee.jobNumber = employeeElements[k];
            employee.prio = employeeElements[k + 1];
            employee.arrivalTime = employeeElements[k + 2];
            employee.duration = employeeElements[k + 3];
            pq[i] = employee;
        }

        return pq;
    }

    public static Employee[] Sort(Employee[] pq) {
        for (int i = 1; i < pq.length; i++) {
            for (int j = i; j > 0 && Less(pq, pq[j], pq[j - 1]); j--) {
                Exch(pq, j, j - 1);
            }
        }
        return pq;
    }

    //probably have to make this sorting work with the 'while'
    public static boolean Less(Employee[] pq, Employee moving, Employee comparing) {
        if (comparing.arrivalTime > moving.arrivalTime) {
            return true;
        }
        return false;
    }

    public static void Exch(Employee[] pq, int moving, int comparing) {
        Employee temp = pq[moving];
        pq[moving] = pq[comparing];
        pq[comparing] = temp;

    }

    public static void main(String[] args) {
        Employee[] array = makePQArray(collection, pq, numberOfEmployees);
        // for (Employee p : array) {
        //     StdOut.println(p.arrivalTime);
        //
        // }
        //StdOut.println("-----------");
        Schedule.Sort(array);
        // for (Employee p : array) {
        //     StdOut.println(p.arrivalTime);
        // }
    }
}

// for (Employee p : array) {
//         StdOut.print(p.jobNumber + " ");
//         StdOut.print(p.prio + " ");
//         StdOut.print(p.arrivalTime + " ");
//         StdOut.print(p.duration + " ");
//         StdOut.println();
//         }



