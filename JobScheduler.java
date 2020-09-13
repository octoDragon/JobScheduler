import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class JobScheduler {
    //parameters
    static LinkedList<Integer> collection = new LinkedList<Integer>();
    static Job jobInfo[];
    static Job pq[];
    static double numberOfEmployees = 0;
    static int time = 0;
    static int pqCounter = 0;
    static int jobInfoCounter = 0;

    //reads in data from a file and creates job objects in an array
    public static Job[] makeInfoArray(In in, LinkedList<Integer> collection,
                                      Job[] jobInfo,
                                      double numberOfEmployees) {
        //reading in the data using StdIn
        while (!in.isEmpty()) {
            numberOfEmployees += 0.25; // each employee has four components
            int i = Integer.parseInt(in.readString());
            collection.add(i); //adding the integers to the LL
        }

        jobInfo = new Job[(int) numberOfEmployees]; //creating array with specific size
        Integer[] employeeElements = new Integer[(int) numberOfEmployees * 4];
        for (int i = 0; i < employeeElements.length; i++) { //taking info. out of LL
            employeeElements[i] = collection.removeFirst();
        }

        //creating each job object with instance variables assigned to the ints read in
        for (int i = 0; i < numberOfEmployees; i++) {
            int k = i;
            k *= 4;
            Job job = new Job();
            job.jobNumber = employeeElements[k];
            job.prio = employeeElements[k + 1];
            job.arrivalTime = employeeElements[k + 2];
            job.duration = employeeElements[k + 3];
            jobInfo[i] = job;
        }

        return jobInfo;
    }

    //uses insertion sort to determine correct positioning in pq and jobInfo arrays
    public static Job[] sort(Job[] pq, int length) {
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 && less(pq, pq[j], pq[j - 1]); j--) {
                exch(pq, j, j - 1);
            }
        }
        return pq;
    }

    //compares jobs arrival time or prioirty depending on time
    public static boolean less(Job[] array, Job moving, Job comparing) {
        if (comparing == null) {
            return true;
        }
        else if (time == 0) {
            if (comparing.arrivalTime > moving.arrivalTime) {
                return true;
            }
        }
        else if (time >= 0) {
            if (moving.prio > comparing.prio && moving.prio > 0) {
                return true;
            }
        }
        return false;
    }

    //exchanges jobs
    public static void exch(Job[] pq, int moving, int comparing) {
        Job temp = pq[moving];
        pq[moving] = pq[comparing];
        pq[comparing] = temp;

    }

    //inserts jobs into my pq
    public static void insert(Job[] pq, Job job) {
        Job p = job;
        pq[pqCounter] = p;
        pqCounter++;
        JobScheduler.sort(pq, pqCounter);
    }

    //keeps track of how much more a job needs to work
    public static void decrement() {
        pq[0].duration--;

        //calculating waiting time instance variable
        if (pq[0].waitingTime == 0) {
            pq[0].waitingTime = (time - pq[0].arrivalTime);
        }
        //checks if a job has worked its entire time
        if (pq[0].duration == 0) {
            JobScheduler.removeMax();
        }
    }

    //removed jobs from the pq when they have worked their full duration
    public static void removeMax() {
        Job removedJob = pq[0];
        pq[0] = null;
        pqCounter--;
        JobScheduler.sort(pq, pqCounter + 1);
        JobScheduler.format(removedJob);
    }

    //formating for standard output
    public static void format(Job person) {
        StdOut.println(
                "Person: " + person.jobNumber +
                        " Priority: " + person.prio +
                        " Duration " + ((time + 1) - ((person.waitingTime - 1)
                        + person.arrivalTime)) +
                        " Arrival: " + person.arrivalTime +
                        " WaitingTime: " + (person.waitingTime - 1) +
                        " ExecutionTime: " + ((time + 1) - (person.arrivalTime + (person.waitingTime
                        - 1))) +
                        " FinishTime: " + (time + 1));

    }

    //determins who works when, implements all the other methods
    public static void runSchedule(Job[] pq, Job[] jobInfo) {
        boolean completion = false;
        JobScheduler.sort(jobInfo, pq.length); //sorts jobs based on arrival time

        while (!completion) {
            time++;
            //checks if people need to be added to the pq
            if (jobInfoCounter < jobInfo.length) {
                //Insertion in qp
                if (time == jobInfo[jobInfoCounter].arrivalTime) {
                    JobScheduler.insert(pq, jobInfo[jobInfoCounter]);
                    jobInfoCounter++;
                }
            }

            //when somone is working, subtract 1 from the amount of work left
            if (pq[0] != null) {
                JobScheduler.decrement();
            }

            //if nothing in pq, after job has joined it, end the program
            else if (pq[0] == null && time > jobInfo[jobInfo.length - 1].arrivalTime) {
                completion = true;
            }
        }
    }

    //main
    public static void main(String[] args) {
        In in = new In(args[0]);
        jobInfo = makeInfoArray(in, collection, jobInfo, numberOfEmployees);
        pq = new Job[jobInfo.length];
        JobScheduler.runSchedule(pq, jobInfo);
    }
}
