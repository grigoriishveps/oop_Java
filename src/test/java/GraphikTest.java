import laba.FormatFilterException;
import laba.GraphicInterface;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphikTest {

    @Test
    public void testisEmpty() {
        try {
            GraphicInterface.checkField("data","11.wfg" );}
        catch (Exception exc){
            assertTrue(exc instanceof FormatFilterException) ;
        }
        try {
            GraphicInterface.checkField("data","22.22.1999" );}
        catch (Exception exc){
            assertTrue(exc instanceof FormatFilterException) ;
        }
        try {
            GraphicInterface.checkField("data","22.а.1999" );}
        catch (Exception exc){
            assertTrue(exc instanceof FormatFilterException) ;
        }
    }


}
