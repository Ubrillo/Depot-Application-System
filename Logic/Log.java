/**
 * Provides attributes and methods that defines a Log object
 * log events
 * write events to a file
 * @author U. UDOETTE
 * @version 12/24
 */

package Logic;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log {
    private static Log logger;

    //report file path
    private String filename = "Depot-System/src/Database/logBook.txt";
    private String logBook = "*************** Log Book ************"+'\n';

    //Constructor
    private Log(){
        this.filename = filename;
    }

    /**
     * @return create or return log instance
     */
    public  static Log getInstance(){
        if (logger == null){
            logger = new Log();
        }
        return logger;
    }

    /**
     * add event log to a temporarily storage
     * @param message - description to be added to log
     */
    public void writeEvent(String message){
        LocalDateTime dateTimeNow = LocalDateTime.now().withNano(0);
        logBook += message+" "+dateTimeNow+'\n';
    }

    /**
     * writes registered events to specified file
     */
    public void writeLogToFile() {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(logBook);
            bw.close();
        } catch (FileNotFoundException fnfex) {
            System.out.println(fnfex.getMessage());
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }

    /**
     * @return temporarily stores logs as string
     */
    public String  getLog(){
        return logBook;
    }
}