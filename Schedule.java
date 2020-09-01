import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Schedule {
    public static void main(String[] args) {
        LinkedList<String> collection = new LinkedList<String>();
        Queue<Employee> employeeQueue = new Queue<Employee>();
        double numberOfEmployees = 0;

        //reading in the data using StdIn
        while (!StdIn.isEmpty()) {
            numberOfEmployees += 0.25; // each employee has four components
            String s = StdIn.readString();
            collection.add(s);
        }

        String[] employeeElements = new String[(int) numberOfEmployees * 4];
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
            employeeQueue.enqueue(employee);
        }

        for (Object employee : employeeQueue) {
            Employee item = employeeQueue.dequeue();
            StdOut.print(item.jobNumber + " ");
            StdOut.print(item.prio + " ");
            StdOut.print(item.arrivalTime + " ");
            StdOut.println(item.duration);
        }

        //making a queue of employees . . . TODO Soon to be a priority queue : )
        //for (int i = 0; i > numberOfEmployees; i++) {
        //makeEmployee
        //Employee employee = new Employee();
        // for (int k = 0; k > collection.size(); k += 4) {
        //
        // }
        // employeeQueue.enqueue(employee);
    }
    //add employee to employeeQueue

}

//}
