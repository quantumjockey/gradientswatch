package com.quantumjockey.colorramps;

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
        ramp = new GradientRamp(stops, 0.0, 1.0);
    }

    @After
    public void tearDown() throws Exception {

    }

    /////////// Tests ///////////////////////////////////////////////////////////////////////

    @Test
    public void GetRampColorValue_WithinLimit_ReturnColor(){
        Assert.assertEquals(Color.YELLOW, ramp.getRampColorValue(0.6));
    }

    @Test
    public void GetRampColorValue_AboveLimit_HighestColorStop(){
        Assert.assertEquals(Color.RED, ramp.getRampColorValue(2.0));
    }

    @Test
    public void GetRampColorValue_BelowLimit_LowestColorStop(){
        Assert.assertEquals(Color.VIOLET, ramp.getRampColorValue(-1.0));
    }

    @Test
    public void GetRampColorValue_Max_ReturnColor(){
        Assert.assertEquals(Color.RED, ramp.getRampColorValue(1.0));
    }

    @Test
    public void GetRampColorValue_Min_ReturnColor(){
        Assert.assertEquals(Color.VIOLET, ramp.getRampColorValue(0.0));
    }

}
