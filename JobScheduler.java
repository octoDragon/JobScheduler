import edu.princeton.cs.algs4.StdIn;
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

    //Standard Input working in conjunction with Linked List
    //to read in all of the data and then assign it to 'Job'
    //objects in pairs of four, which fit into the array I
    //use as the priority queue
    public static Job[] makePQArray(LinkedList<Integer> collection,
                                    Job[] pq,
                                    double numberOfEmployees) {
        //reading in the data using StdIn
        while (!StdIn.isEmpty()) {
            numberOfEmployees += 0.25; // each employee has four components
            int i = Integer.parseInt(StdIn.readString());
            collection.add(i); //adding the integers to the LL
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

    public static Job[] sort(Job[] pq, int length) {
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 && less(pq, pq[j], pq[j - 1]); j--) {
                exch(pq, j, j - 1);
            }
        }
        return pq;
    }

    public static boolean less(Job[] array, Job moving, Job comparing) {
        if (time == 0) {
            if (comparing.arrivalTime > moving.arrivalTime) {
                return true;
            }
        }

        else if (time >= 0) {
            if (moving.prio > comparing.prio && moving.prio > 0) {
                return true;
            }
        }

        //will need to add for the remove method
        // if (comparing == null) {
        //     return true;
        // }
        return false;
    }

    public static void exch(Job[] pq, int moving, int comparing) {
        Job temp = pq[moving];
        pq[moving] = pq[comparing];
        pq[comparing] = temp;

    }

    //essentially 'pops' or deletes the employee from the array; sets prio to -1

    public static void runSchedule(Job[] pq, Job[] jobInfo) {
        boolean completion = false;
        JobScheduler.sort(jobInfo, pq.length); //sorts the arrivals into order
        while (!completion) {
            if (jobInfoCounter < jobInfo.length) {
                time++;
                if (JobScheduler.time == jobInfo[jobInfoCounter].arrivalTime) {
                    JobScheduler.insert(pq, jobInfo[jobInfoCounter]);
                    jobInfoCounter++;
                }
                else if (JobScheduler.time != jobInfo[jobInfoCounter].arrivalTime
                        && pq[0] != null) {
                    //StdOut.println(jobInfoCounter);
                    JobScheduler.decrement(pq[0]);
                    //knows which item finishes first, but it doesn't continue onto the next one'
                }
                else if (jobInfoCounter == jobInfo.length) {
                    completion = true;
                    StdOut.println("finished");
                }
            }
        }

    }

    public static void insert(Job[] pq, Job job) {
        Job p = job;
        pq[pqCounter] = p;
        pqCounter++;
        JobScheduler.sort(pq, pqCounter);
    }

    public static int decrement(Job job) {
        job.duration--;
        if (job.duration == 0) {
            pqCounter--;
            StdOut.println(job.jobNumber);
            StdOut.println(jobInfoCounter);
            //JobScheduler.removeMax(pq);
        }
        return job.duration;
    }

    public static Job removeMax(Job[] pq) {
        Job removedJob = pq[0];
        // JobScheduler.format(removedJob, workDuration);
        // pq[0].prio = -1;
        // return removedJob;
        pqCounter--;
        return removedJob;
    }

    public static void format(Job person, int workDuration) {
        StdOut.println(
                "Person: " + person.jobNumber +
                        " Priority: " + person.prio +
                        " Arrival: " + person.arrivalTime +
                        " ExecutionTime: " + person.duration +
                        " WaitingTime: " + JobScheduler.time +
                        " Ended: " + workDuration);

    }

    public static void main(String[] args) {
        jobInfo = makePQArray(collection, jobInfo, numberOfEmployees);
        pq = new Job[jobInfo.length];
        JobScheduler.runSchedule(pq, jobInfo);
    }
}

//TODO: need to fix the last line of code not working properly with the start time
//TODO: need to fix the formatting with propper name and everything DONE
//TODO: need to change the priority from being small to large DONE
//TODO: ask Mummy about the formatting
//TODO: implement waiting time

// for (Job p : pq) {
//     StdOut.print(p.jobNumber + " ");
//     StdOut.print(p.prio + " ");
//     StdOut.print(p.arrivalTime + " ");
//     StdOut.print(p.duration + " ");
//     StdOut.println();
// }

// public static boolean LessArrivalTime(Job[] pq, Job moving, Job comparing) {
//     if (comparing.arrivalTime > moving.arrivalTime) {
//         return true;
//     }
//     return false;
// }

