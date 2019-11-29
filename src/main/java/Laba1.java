import java.util.*;
import laba.*;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.LogManager;

public class Laba1 {

    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        new GraphicInterface();
        Logger logger = LogManager.getLogger(GraphicInterface.class);
        logger.info("Hello World");

    }
}
