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

import java.util.LinkedList;

/**
 * This is a thread pool to reuse mailer threads for cancellation emails
 * 
 * @author carstm
 * 
 */
public class MailQueue {

	private int nThreads = 0;
	private PoolWorker[] threads;
	private static LinkedList<Runnable> queue;
	static boolean testModeOn = false;
	Counter qCounter = new Counter();
	Counter jobCounter = new Counter();

	/**
	 * Constructor used by test class. Only difference is the call to setTestMode
	 */
	public MailQueue(int nThreads, boolean testMode) {
		setTestModeOn(testMode);
		this.nThreads = nThreads;
		queue = new LinkedList<Runnable>();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].setName(Integer.toString(i));
			threads[i].start();
		}
	}

	public MailQueue(int nThreads) {
		this.nThreads = nThreads;
		queue = new LinkedList<Runnable>();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].setName(Integer.toString(i));
			threads[i].start();
		}
	}

	public void execute(Runnable r) {
		synchronized (qCounter) {
			qCounter.increment();
			System.out.println("Jobs put into queue: " + qCounter.getNJobValue());
		}

		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	private class PoolWorker extends Thread {

		public void run() {

			Runnable job = null;

			while (true) {
				synchronized (queue) {

					while (queue.isEmpty()) {
						try {

							queue.wait();

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					job = (Runnable) queue.removeFirst();

				}
				try {
					if (testModeOn == false) {

						job.run();

					} else if (testModeOn == true) {

						Thread ct = Thread.currentThread();
						System.out.println("ct: " + ct.getName());
						synchronized (jobCounter) {
							jobCounter.increment();
							System.out.println("Job's run: " + jobCounter.getNJobValue());
						}
					}

				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public final class Counter {
		private int nJob = 0;

		public synchronized int getNJobValue() {
			return nJob;
		}

		public synchronized int increment() {
			return ++nJob;
		}
	}

	public static boolean isTestModeOn() {
		return testModeOn;
	}

	public static void setTestModeOn(boolean testModeOn) {
		MailQueue.testModeOn = testModeOn;
	}

}
