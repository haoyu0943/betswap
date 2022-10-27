package yijiang.jboot.Core;

import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;

public class ActionToLog4j extends Writer {
    Logger logger = Logger.getLogger(ActionToLog4j.class);
    public void write(String str) throws IOException {
        logger.info(str);
    }
    public void write(char[] cbuf, int off, int len) throws IOException {}
    public void flush() throws IOException {}
    public void close() throws IOException {}
}
