/**
 * Copyright 2009 Vastra Gotalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 */
package se.vgregion.webbtidbok.mail;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.webbtidbok.State;
import se.vgregion.webbtidbok.domain.Booking;

/**
 * READ ME. READ ME. READ ME. READ ME.
 * 
 * This is really a manual test which tells you that you can run x number of jobs using the MailQueue class and it's threading. It
 * also tells you that the four threads started in MailQueue share the work randomly and lives thru the test.
 * 
 * @author carstm
 * 
 */

public class MailQueueTest {
	public static MailQueue queue;
	private static State state;
	private Booking booking;
	static int nJobs = 8;
	final boolean testModeOn = true;

	@Before
	public void setUp() throws Exception {

		queue = new MailQueue(4, testModeOn);

	}

	@Ignore
	@Test
	public void testIfClassIsSetUpForProduction() {
		assertFalse("Test Mode is not on. ", queue.isTestModeOn());
	}

	/**
	 * It's random when something is performed in the MailQueue class which means it's not really directly testable since you
	 * can't assert anything expected compared to anything given. however from manual testing it looks like the queue is either 0
	 * or nJobs long.
	 * 
	 * @throws InterruptedException
	 */

	@Test
	public void testSentJobsEqualsJobsRun() throws InterruptedException {
		assertTrue("MailQueue is NOT set to test mode. Do that.", queue.isTestModeOn() == true);
		for (int i = 0; i < nJobs; i++) {
			ThreadStarter ts = new ThreadStarter();
			ts.start();
		}
		if (queue.qCounter.getNJobValue() == 0 || queue.qCounter.getNJobValue() == nJobs) {
			System.out.println("in if.");
			assertTrue(true);
		} else {
			assertTrue(false);
		}

	}

	private class ThreadStarter extends Thread {
		public void run() {
			NewThread nt = new NewThread();
		}
	}

	private class NewThread extends Thread {

		public NewThread() {
			CreateAndSendMailToQueue job = new CreateAndSendMailToQueue(queue);
		}
	}

	private class CreateAndSendMailToQueue implements Runnable {
		public CreateAndSendMailToQueue(MailQueue queue) {
			MailSender sender = new MailSender(state, booking);
			queue.execute(sender);
		}

		@Override
		public void run() {

		}
	}

	// @After
	// public void tearDown() {
	// queue.setTestModeOn(false);
	// }
}
