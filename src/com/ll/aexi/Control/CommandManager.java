package com.ll.aexi.Control;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class CommandManager {
    private Command currentCommand;

    private List<Command> commands = new ArrayList<>();

    public void setCurrentCommand(Command currentCommand) {
        this.currentCommand = currentCommand;
    }

    public void excuteCommand() {
        if (currentCommand.excute() && currentCommand.canUndo())
            addToCommandList();
    }


    private void addToCommandList() {
        assert currentCommand.canUndo();
        commands.add(currentCommand);
    }






    //单例相关代码
    private static CommandManager instance = new CommandManager();

    private CommandManager() {}

    public static CommandManager getInstance() {
        return instance;
    }
}
