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
import java.util.concurrent.ExecutorService;

/**
 * This is a thread pool to reuse mailer threads for cancellation emails
 * 
 * @author carstm
 * 
 */
public class MailQueue {

	private int nThreads = 0;
	private final PoolWorker[] threads;
	private static LinkedList<Runnable> queue;
	private static ExecutorService exec;

	public MailQueue(int nThreads) {
		this.nThreads = nThreads;
		queue = new LinkedList<Runnable>();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			System.out.println("In for()");
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public void execute(Runnable r) {

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
					System.out.println("Q size is: " + queue.size());
					while (queue.isEmpty()) {
						try {
							System.out.println("Q is empty");
							queue.wait();

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					job = (Runnable) queue.removeFirst();

				}
				try {
					job.run();

				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
