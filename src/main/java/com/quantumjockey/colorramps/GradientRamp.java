package com.quantumjockey.colorramps;

import javafx.scene.paint.Color;
import java.util.ArrayList;

public class GradientRamp {

    /////////// Constants ///////////////////////////////////////////////////////////////////

    private final int MAX_BYTE_VALUE = 255;

    /////////// Fields //////////////////////////////////////////////////////////////////////

    public double lowerBound;
    public ArrayList<RampStop> ramp;
    public String tag;
    public double upperBound;

    /////////// Constructors ////////////////////////////////////////////////////////////////

    public GradientRamp(Color[] _colors, String _tag, double _lowerBound, double _upperBound){
        int count;
        double unit;

        ramp = new ArrayList<>();
        tag = _tag;

        lowerBound = _lowerBound;
        upperBound = _upperBound;

        count = _colors.length;
        unit = 1.0 / ((double)(count - 1));

        for (int i = 0; i < count; i++) {
            ramp.add(new RampStop(_colors[i], i * unit));
        }
    }

    public GradientRamp(Color[] _colors, double _lowerBound, double _upperBound){
        this(_colors, "(Unnamed Ramp)", _lowerBound, _upperBound);
    }

    public GradientRamp(Color[] _colors, String _tag) {
        this(_colors, _tag, 0.0, 1.0);
    }

    public GradientRamp(Color[] _colors) {
        this(_colors, "(Unnamed Ramp)");
    }

    /////////// Public Methods ////////////////////////////////////////////////////////////////

    public Color getRampColorValue(double offset){
        double scaledVal;
        int maxByteValue;
        RampStop firstStop, secondStop;

        firstStop = ramp.get(0);
        secondStop = ramp.get(ramp.size() - 1);

        scaledVal = offset;
        maxByteValue = this.MAX_BYTE_VALUE;

        if (offset < lowerBound)
            scaledVal = lowerBound;

        if (offset > upperBound)
            scaledVal = upperBound;

        for (RampStop boundary : ramp)
        {
            if (boundary.offset < scaledVal && boundary.offset > lowerBound)
                firstStop = boundary;

            if (boundary.offset > scaledVal && boundary.offset < upperBound) {
                secondStop = boundary;
                break;
            }
        }

        return Color.rgb(
                calculateChannelValue(firstStop, secondStop, 'R', scaledVal, maxByteValue),
                calculateChannelValue(firstStop, secondStop, 'G', scaledVal, maxByteValue),
                calculateChannelValue(firstStop, secondStop, 'B', scaledVal, maxByteValue)
        );
    }

    /////////// Private Methods ///////////////////////////////////////////////////////////////

    private int calculateChannelValue(RampStop _before, RampStop _after, char _colorComponent, double _offset, int _maxValue){
        double afterColorChannelValue, afterOffset;
        double beforeColorChannelValue, beforeOffset;
        double channelRange, scaleFactor;
        float max, newChannel, result;
        int byteValue;

        afterOffset = _after.offset;
        afterColorChannelValue = getRgbChannelValue(_after.color, _colorComponent);

        beforeOffset = _before.offset;
        beforeColorChannelValue = getRgbChannelValue(_before.color, _colorComponent);

        max = _maxValue / this.MAX_BYTE_VALUE;

        channelRange = afterColorChannelValue - beforeColorChannelValue;
        scaleFactor = (_offset - beforeOffset) / (afterOffset - beforeOffset);

        newChannel = (float)(scaleFactor * channelRange);
        result = (float)(newChannel + beforeColorChannelValue);
        byteValue = (int)(((result < max) ? result : max) * this.MAX_BYTE_VALUE);

        return byteValue;
    }

    private double getRgbChannelValue(Color _color, char _component){
        double value;

        switch (_component){
            case 'R':
                value = _color.getRed();
                break;
            case 'G':
                value = _color.getGreen();
                break;
            case 'B':
                value = _color.getBlue();
                break;
            default:
                value = _color.getRed();
        }

        return value;
    }

}
