package lazyblinds.thijs.blindsmob;

/**
 * Created by thijs on 8-10-16.
 */
public class TimedCommandBuilderHolder {
    private static TimedCommandBuilder tcb = new TimedCommandBuilder();

    public static TimedCommandBuilder gettcb() {
        return tcb;
    }
}
