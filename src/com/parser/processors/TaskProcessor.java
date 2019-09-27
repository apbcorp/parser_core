package com.parser.processors;

import com.parser.Parser;

import java.util.ArrayList;

public class TaskProcessor {
    public void setTasks(ArrayList<Parser> parsers) {
        for (Parser parser:parsers) {
            parser.setTask("/en/api/json-rpc");
        }
    }

    public void setTask(Parser parser)
    {
        parser.setTask("");
    }
}
