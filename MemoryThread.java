/**
 * This is the thread to simulate the request, access and release of memory space
 */

package com.mycompany.test;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoryThread implements Runnable {
	public MemoryThread(PhysicalMemory m, int id) {
		physicalMemory = m;
		threadID = id;
	}

        @Override
	public void run() {
		// Generate a memory request of a random size
		Random generator = new Random();
		int requestSize = 1 + generator.nextInt(MAX_REQUEST_SIZE);
		// Debuging message
		System.out.println("Thread " + threadID + " has been started");
            

		// request memory space
		boolean flag = physicalMemory.requestMemory(threadID, requestSize);

		// Debuging message
		System.out.println("Thread " + threadID + " has been allocate memory space " + flag);

		while (!flag) {
			flag = physicalMemory.requestMemory(threadID, requestSize);
		}

		// Generate a random memory access
		int requestPageNumber = generator.nextInt(requestSize);
		int frameNumber = physicalMemory.accessMemory(threadID, requestPageNumber);

		System.out.println("Thread " + threadID + " requests for page number " + requestPageNumber
				+ " which has frame number " + frameNumber + "\n");

             
		// Release the memory
		physicalMemory.releaseMemory(threadID);
	}

	private PhysicalMemory physicalMemory;
	private int threadID;
	public static final int MAX_REQUEST_SIZE = 50;
}
