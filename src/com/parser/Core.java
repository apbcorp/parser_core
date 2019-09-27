package com.parser;

import com.parser.processors.TaskProcessor;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Core {
    private ArrayList<Parser> parsers;
    private ArrayList<Thread> threads;
    private TaskProcessor taskProcessor;

    public void execute()
    {
        this.taskProcessor = new TaskProcessor();
        this.init();
        this.taskProcessor.setTasks(this.parsers);

        while (!this.isStopped()) {
            for (int i = 0; i < this.parsers.size(); i++) {
                Thread thread = this.threads.get(i);
                Parser parser = this.parsers.get(i);
                if (!thread.isAlive()) {
                    if (!parser.isEmpty() && parser.isEmptyTask()) {
                        System.out.println("Thread #" + parser.getProcId() + ":" + parser.getResult());

                        this.taskProcessor.setTask(parser);
                    }

                    thread.run();
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException $exception) {
                System.out.println("InterruptedException");
            }
        }
    }

    private boolean isStopped()
    {
        for (Parser i:this.parsers) {
            if (i.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private void init()
    {
        this.parsers = new ArrayList<>();
        this.threads = new ArrayList<>();

        this.parsers.add(new Parser().setDomain("http://project-admin.local").setProcId(1));
        this.parsers.add(new Parser().setDomain("http://project-admin.local").setProcId(2));
        this.parsers.add(new Parser().setDomain("http://project-admin.local").setProcId(3));
        this.parsers.add(new Parser().setDomain("http://project-admin.local").setProcId(4));

        for (int i = 0; i < parsers.size(); i++) {
            Thread thread = new Thread(this.parsers.get(i));
            thread.setDaemon(true);
            this.threads.add(thread);
        }
    }
}
