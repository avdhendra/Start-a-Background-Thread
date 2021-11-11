package com.example.backgroundthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Activity";
    Button start,stop;
    private volatile boolean stopThread=false; //varibale access by thread not cached but update version

     private Handler mainhandler=new Handler(); //this is android class
    //form this it is quite easy to passed the work to be done between different thread

    @Override
     protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       start=findViewById(R.id.btnStart);
       stop=findViewById(R.id.btnstop);
       //This thread currently Running in main Thread which prevent to do anything until it get over

        /*start.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         for(int i=0;i<10;i++) {
             Log.d(TAG, "startThread: " + i);
             try {
                 Thread.sleep(1000);

             } catch (InterruptedException e) {
                 e.printStackTrace();
              }
           }
       }

       });*/
        //So we have write it in background thread
        //this can be achieved by either extending thread class override the run method or
        //by implementing the runnable interface passsing runnable object to the new object
      /**
      Thread class and runnable interface is the class and interface of java
    they acquire the java class
       */
      //there are some andorid specific classes Handler and Looper
      /** Which running these thread and make communication between them*/

    start.setOnClickListener(new View.OnClickListener() {
     @Override
      public void onClick(View view) {
      /* implement thread class
        exampleThread t1=new exampleThread(10);
        t1.start();

    */
        stopThread=false;
        /*implement thread interface*/
    exampleRunnable r1=new exampleRunnable(10);
    new Thread(r1).start();
    //r1.run() it will run the thread in main thread

    /*    //we can also use the anonymous class for this require less space
        new Thread(new Runnable() {
            @Override
            public void run() {
                //work
            }
          }).start();
      }

       });
         */}});
    stop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopThread=true;
        }
    });
    }




    //Implement runnable method

    class exampleRunnable implements Runnable{
        int sec;
        exampleRunnable(int second){
            sec=second;
        }
        @Override
        public void run() {
            for(int i=0;i<sec;i++) {
                if(stopThread)
                    return;
                if(i==5){
                    //mainhandler to work in background thread to ui thread
                    //it post it to main ui thread
                    mainhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            start.setText("50%");
                        }
                    });

                    //we could implement the handler inside the but for that we need looper
                    /**
                     * cant create handler inside thread that has not called looper.prepare

                     Handler threadHandler= new Handler(Looper.getMainLooper())
                     associate threadHandler to looper ui thread getmainLooper
                     threadHandler.post(new Runnable(){
                    @Override
                    public void run(){
                    start.setText("%50")
                    }});
                     */
                    /*Other way
                    start.post(new Runnable(){
                    public void run(){
                    start.setText("%50")

                    }
                    }
                    convice of view class
                    * */
                    /*
                    runOnUiThread(new Runnable(){
                    public void run(){
                    start.setText("%50')
                    }
                    }
                    * */
                }
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //inner class exampleThread which exted Thread class
    class exampleThread extends Thread{
        int sec;
        exampleThread(int sec){
            this.sec=sec;
        }
        @Override
        public void run(){
            for(int i=0;i<sec;i++) {
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

/**
 In both case we implement runnable because in thread class implement the runnable  interface
 runnable object encapsulate the code and run it on new thread
thread class in most staright forward
 */
/** Only the original thread that created a view hierachy can touch its views
 mean we cannot change(ie set text of view in differenet thread rather that main ui thread
 we got crash
  */
/**
 Runnable dont start new thread it just encapsulate the code the which has to be run in handler
 or in thread


 */
}