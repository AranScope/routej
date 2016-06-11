import java.util.Date;

/**
 * Class for logging to server console.
 */
public class Logger {
    private Date date;

    /**
     * Construct a new logger.
     */
    public Logger(){
        this.date = new Date();
    }

    /**
     * Send a regular log to the console.
     * @param string
     */
    public void log(String string){
        System.out.println(date + " | " + string);
    }

    /**
     * Send an error log to the console.
     * @param string
     */
    public void elog(String string){
        System.err.println(date + " | " + string);
    }
}
