package com.parser;

public class Main {
    private Parser[] parsers;
    private Thread[] threads;

    public static void main(String[] args) {
        Core core = new Core();

        core.execute();
    }
}
