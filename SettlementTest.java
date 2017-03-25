import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;

public class SettlementTest {
    Settlement s;

    @Before
    public void setUp() throws Exception {
        s = new Settlement();
    }
}