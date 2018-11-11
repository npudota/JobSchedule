import java.util.*;

public class JobSchedule {
	private ArrayList<Job> jobs;

	//Constructor for JobSchdule
	
	public JobSchedule() {
		jobs = new ArrayList<Job>();
	}

	

		//Adds a job to the array in job schedule and gives it a time as well
		public Job addJob(int time) {
			Job job = new Job();
			job.time = time;
			jobs.add(job);
			return job;
		}

		//getJob just return an index in the order of job added to array in job schedule
		public Job getJob(int index) {
			return jobs.get(index);
		}

		/*
		 * first checks if its a cycle, if it is returns -1
		 * If job is a vertex with 0 out add to an array
		 * check the longest time in that array where the job are with vertex out 0
		 */
		public int minCompletionTime() {
			ArrayList<Job> last = new ArrayList<Job>();
			for (int i = 0; i < jobs.size(); i++) {
				if (jobs.get(i).cycle) {
					return -1;
				}
				if (jobs.get(i).out.isEmpty()) {
					last.add(jobs.get(i));
				}
			}
			int finalTime = 0;
			for (int i = 0; i < last.size(); i++) {
				if (finalTime < last.get(i).startTime + last.get(i).time) {
					finalTime = last.get(i).startTime + last.get(i).time;
				}
			}
			return finalTime;
		}
		
		//inner class for Job
		class Job {

			private ArrayList<Job> in = new ArrayList<Job>();
			private ArrayList<Job> out = new ArrayList<Job>();
			private ArrayList<Job> v = new ArrayList<Job>();
			private int time;
			private boolean cycle = false;
			private int startTime = 0;
		/*
		 * determines a pre-req to job. fixes time at start time
		 * added to incoming, outgoing and visit array accordingly 
		 * calls update method which updates times
		 */
		public void requires(Job j) {
			if (j.startTime + j.time > startTime || in.isEmpty()) {
				startTime = j.time + j.startTime;
			}
			in.add(j);
			j.out.add(this);
			v.add(this);
			update(this);
			v.removeAll(v);
		}

		/*
		 * update times of current jobs when called on by requires
		 * if no outs then no need to update, because its last
		 * check if there is a cycle
		 * adds all outgoing edges to v arraylist and updates times to each vertex, calls till last vertex
		 */
		public void update(Job current) {
			if (current.out.isEmpty()) {
				return;
			} else {
				for (int i = 0; i < current.out.size(); i++) {
					if (sameJob(current.out.get(i))) {
						startTime = -1;
						current.startTime = -1;
						cycle = true;
						return;
					}
					v.add(current.out.get(i));
					current.out.get(i).startTime = current.time + current.startTime;
					update(current.out.get(i));
				}
			}
		}
		
		 //checks if there is a cycle
		 

		public boolean sameJob(Job j) {
			for (int i = 0; i < v.size(); i++) {
				if (v.get(i).equals(j)) {
					return true;
				}
			}
			return false;
		}
		//returns start time
		
		public int getStartTime() {
			return startTime;
		}
	}
}