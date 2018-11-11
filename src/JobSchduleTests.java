import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class JobSchduleTests {

	@Test
	public void test() {
		int x = 0;
		JobSchedule jobSchedule1 = new JobSchedule();
		jobSchedule1.addJob(8);//0
		jobSchedule1.addJob(3);//1
		jobSchedule1.addJob(5);//2
		jobSchedule1.addJob(12);//3
		jobSchedule1.addJob(6);//4
		jobSchedule1.getJob(2).requires(jobSchedule1.getJob(3));
		jobSchedule1.getJob(3).requires(jobSchedule1.getJob(4));
		jobSchedule1.getJob(1).requires(jobSchedule1.getJob(2));
		jobSchedule1.getJob(0).requires(jobSchedule1.getJob(2));
		x = 23;
		assertEquals(x, jobSchedule1.getJob(0).getStartTime());
		assertEquals(x, jobSchedule1.getJob(1).getStartTime());
		
		JobSchedule jobSchedule2 = new JobSchedule();
		jobSchedule2.addJob(8); //adds job 0 with time 8
		JobSchedule.Job j1 = jobSchedule2.addJob(3); //adds job 1 with time 3
		jobSchedule2.addJob(5); //adds job 2 with time 5
		jobSchedule2.minCompletionTime(); //should return 8, since job 0 takes time 8 to complete.
		/* Note it is not the min completion time of any job, but the earliest the entire set can complete. */
		jobSchedule2.getJob(0).requires(jobSchedule2.getJob(2)); //job 2 must precede job 0
		jobSchedule2.minCompletionTime(); //should return 13 (job 0 cannot start until time 5)
		jobSchedule2.getJob(0).requires(j1); //job 1 must precede job 0
		jobSchedule2.minCompletionTime(); //should return 13
		x = 13;
		assertEquals(x, jobSchedule2.minCompletionTime());
		jobSchedule2.getJob(0).getStartTime(); //should return 5
		j1.getStartTime(); //should return 0
		jobSchedule2.getJob(2).getStartTime(); //should return 0
		j1.requires(jobSchedule2.getJob(2)); //job 2 must precede job 1
		jobSchedule2.minCompletionTime(); //should return 16
		x = 16;
		assertEquals(x, jobSchedule2.minCompletionTime());
		jobSchedule2.getJob(0).getStartTime(); //should return 8
		jobSchedule2.getJob(1).getStartTime(); //should return 5
		jobSchedule2.getJob(2).getStartTime(); //should return 0
		jobSchedule2.getJob(1).requires(jobSchedule2.getJob(0)); //job 0 must precede job 1 (creates loop)
		jobSchedule2.minCompletionTime(); //should return -1
		x = -1;
		assertEquals(x, jobSchedule2.minCompletionTime());
		jobSchedule2.getJob(0).getStartTime(); //should return -1
		jobSchedule2.getJob(1).getStartTime(); //should return -1
		jobSchedule2.getJob(2).getStartTime(); //should return 0 (no loops in prerequisites)
		
	}

}
