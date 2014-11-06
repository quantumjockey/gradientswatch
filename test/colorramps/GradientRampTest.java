package colorramps;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GradientRampTest {

    /////////// Fields //////////////////////////////////////////////////////////////////////

    Color[] stops;
    GradientRamp ramp;

    /////////// Setup/Teardown //////////////////////////////////////////////////////////////

    @Before
    public void setUp() throws Exception {
        stops = new Color[]{Color.VIOLET, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
        ramp = new GradientRamp(stops);
    }

    @After
    public void tearDown() throws Exception {

    }

    /////////// Fields //////////////////////////////////////////////////////////////////////

    @Test
    public void GetRampColorValue_WithinLimit_ReturnColor() throws Exception {
        Assert.assertEquals(Color.VIOLET, ramp.getRampColorValue(0.0, 0.0, 1.0));
    }

}
