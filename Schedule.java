import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Schedule {
    //parameters
    static LinkedList<Integer> collection = new LinkedList<Integer>();
    static Job pq[];
    static double numberOfEmployees = 0;
    static int time = 0;

    //methods
    public static Job[] makePQArray(LinkedList<Integer> collection,
                                    Job[] pq,
                                    double numberOfEmployees) {
        //reading in the data using StdIn
        while (!StdIn.isEmpty()) {
            numberOfEmployees += 0.25; // each employee has four components
            int i = Integer.parseInt(StdIn.readString());
            collection.add(i);
        }

        pq = new Job[(int) numberOfEmployees];
        Integer[] employeeElements = new Integer[(int) numberOfEmployees * 4];
        for (int i = 0; i < employeeElements.length; i++) {
            employeeElements[i] = collection.removeFirst();
        }

        for (int i = 0; i < numberOfEmployees; i++) {
            int k = i;
            k *= 4;
            Job job = new Job();
            job.jobNumber = employeeElements[k];
            job.prio = employeeElements[k + 1];
            job.arrivalTime = employeeElements[k + 2];
            job.duration = employeeElements[k + 3];
            pq[i] = job;
        }

        return pq;
    }

    public static Job[] Sort(Job[] pq, String type) {
        if (type == "prio") {
            for (int i = 1; i < pq.length; i++) {
                for (int j = i; j > 0 && LessPrio(pq, pq[j], pq[j - 1]); j--) {
                    Exch(pq, j, j - 1);
                }
            }
        }
        if (type == "arrivalTime") {
            for (int i = 1; i < pq.length; i++) {
                for (int j = i; j > 0 && LessArrivalTime(pq, pq[j], pq[j - 1]); j--) {
                    Exch(pq, j, j - 1);
                }
            }
        }
        return pq;
    }

    public static boolean LessPrio(Job[] pq, Job moving, Job comparing) {
        //if employee already worked, disreguard the employee
        //change prio in the RemoveEmployee() method
        if (comparing.prio == -1) {
            return true;
        }

        if (moving.arrivalTime < Schedule.time) {
            if (moving.prio < comparing.prio && moving.prio > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean LessArrivalTime(Job[] pq, Job moving, Job comparing) {
        if (comparing.arrivalTime > moving.arrivalTime) {
            return true;
        }
        return false;
    }


    public static void Exch(Job[] pq, int moving, int comparing) {
        Job temp = pq[moving];
        pq[moving] = pq[comparing];
        pq[comparing] = temp;

    }

    //essentially 'pops' or deletes the employee from the array; sets value to null
    public static Job RemoveEmployee(Job[] pq, int workDuration) {
        Job removedJob = pq[0];
        Schedule.format(removedJob, workDuration);
        pq[0].prio = -1;
        return removedJob;


    }

    public static void runSchedule(Job[] pq) {
        boolean completion = false;
        int workDuration = 0;

        Schedule.Sort(pq, "arrivalTime");
        while (!completion) {
            time++;
            //incrementing time, seconds go up by one
            if (time == pq[0].arrivalTime && workDuration == 0) {
                workDuration = (pq[0].duration + time);
                Schedule.RemoveEmployee(pq, workDuration);
                Schedule.Sort(pq, "prio");
            }

            //when next person needs to work and time in between
            else if (time == workDuration) {
                Schedule.Sort(pq, "prio");
                workDuration = (pq[0].duration + time);
                Schedule.RemoveEmployee(pq, workDuration);
                Schedule.Sort(pq, "prio");
            }

            //check to see if array is 'empty' , no employees left
            else if (pq[0].prio == -1) {
                completion = true;
            }
        }
    }

    public static void format(Job person, int workDuration) {
        StdOut.println(
                "Person: " + person.jobNumber +
                        " Priority: " + person.prio +
                        " Arrival: " + person.arrivalTime +
                        " Length: " + person.duration +
                        " Started: " + Schedule.time +
                        " Ended: " + workDuration);

    }

    public static void main(String[] args) {
        Job[] array = makePQArray(collection, pq, numberOfEmployees);
        Schedule.runSchedule(array);
    }
}

//TODO: need to fix the last line of code not working properly with the start time
//TODO: need to fix the formatting with propper name and everything
//TODO: need to change the priority from being small to large
//TODO: ask Mummy about the formatting

// for (Employee p : array) {
//         StdOut.print(p.jobNumber + " ");
//         StdOut.print(p.prio + " ");
//         StdOut.print(p.arrivalTime + " ");
//         StdOut.print(p.duration + " ");
//         StdOut.println();
//         }



