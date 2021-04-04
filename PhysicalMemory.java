package com.mycompany.test;

public class PhysicalMemory {

    /*
        PhysicalMemory constructor :
               - freeFrames  represents the number of free frames.
               - frameSize represents the size of each frame.  
         NOTE:
              I have frame[] as a boolean array for tracking the allocated 
                     and free frames in the physical memory
    */
    
    public PhysicalMemory(int freeFrames, int frameSize) {
        num_free_frames = freeFrames;
        frame_size = frameSize;
        frames = new boolean[freeFrames+15];
    }

    public boolean requestMemory(int id, int requestLength) {
        // If the memory has enough free frames to satisfy this request        
        if (requestLength <= num_free_frames) {
           //Building The pagetable for this request
            PageTable p = new PageTable(id, requestLength);
            
            // Updating the physicalMemory Properties 
            // and Binding each page number with frame number
            for (int i = 0; i < requestLength; i++) {
                p.appendEntry(logical_address);
                frames[logical_address] = true;
                logical_address++;
                logical_address%=(num_free_frames*frame_size+15);
            }
            pageTableList.addFirst(p);
            num_free_frames -= requestLength;
           
            return true;
        }
        // If the memory doesn't have enough free frames to satisfy this request   
        return false;
    }

    public int accessMemory(int id, int pageNumber) {
        PageTable pt = pageTableList.lookup(id);
        // Debuging message
        pt.printPageTable();
        //.....//
        return pt.getFrameNumber(pageNumber);
    }

    public void releaseMemory(int id) {
        PageTable pt = pageTableList.lookup(id);
        num_free_frames += pt.getLength();
        for (int i = 0 ; i<pt.getLength();i++)
        {
            frames[pt.getFrameNumber(i)] = false;
            // Debuging message
            System.out.println( "Thread "+id +" :  " + pt.getFrameNumber(i) + " frame became empty");
        }
            
    }

    public int num_free_frames;
    public int frame_size;
    // Here number 15 is only a random starting point in Memory
    public int logical_address = 15;
    public boolean[] frames;
    PageTableList pageTableList = new PageTableList();
}
