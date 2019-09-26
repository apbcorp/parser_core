package com.parser;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        Thread thread = new Thread(parser);
        thread.setDaemon(true);

        thread.start();

        System.out.println("Core message");

        if (thread.isAlive()) {
            try {
                thread.join();
            } catch (InterruptedException $exception) {
                System.out.println("exception");
            }
        }

        System.out.println(parser.result);
    }
}
