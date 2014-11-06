package colorramps;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GradientRampTest {

    Color[] stops;
    GradientRamp ramp;

    @Before
    public void setUp() throws Exception {
        stops = new Color[]{Color.VIOLET, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
        ramp = new GradientRamp(stops);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void GetRampColorValue() throws Exception {
        Assert.assertEquals(Color.VIOLET, ramp.getRampColorValue(0.0, 0.0, 1.0));
    }
}