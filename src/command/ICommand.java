package command;

import java.util.List;


public interface ICommand {

    double execute ();

    default double loopExecute (List<ICommand> commands) {
        double lastValue = 0;
        for (int i = 0; i < commands.size(); i++) {
            lastValue = commands.get(i).execute();
        }
        return lastValue;
    }
}
