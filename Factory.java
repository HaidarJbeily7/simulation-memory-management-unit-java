/**
 * Factory class to start five threads.
 */
package com.mycompany.test;

import java.util.Scanner;

public class Factory {
        public static void main(String args[]) throws InterruptedException {
                // Modified to get memory size and frame size from user input
                Scanner sc= new Scanner(System.in);
                System.out.println("Enter Memory Size- "); 
                int  memorySize = sc.nextInt();
                System.out.println("Enter Frame Size - "); 
                int frameSize = sc.nextInt();
                //.........//
                
                
                int numFrames = memorySize / frameSize;
                PhysicalMemory physicalMemory = new PhysicalMemory(numFrames, frameSize);
                Thread[] threadArray = new Thread[5];
                
                //Creating the Thread instancess
                for (int i = 0; i < 5; i++)
                        threadArray[i] = new Thread(new MemoryThread(physicalMemory, i));
                
                //Starting the Thread instancess
                for (int i = 0; i < 5; i++) {
                        threadArray[i].start();
                        // Waits for this thread to die.
                        threadArray[i].join();
                }

        }
}
