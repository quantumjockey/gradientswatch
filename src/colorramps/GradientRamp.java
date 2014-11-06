package colorramps;

import javafx.scene.paint.Color;
import java.util.ArrayList;

public class GradientRamp {

    /////////// Fields //////////////////////////////////////////////////////////////////////

    public ArrayList<RampStop> ramp;
    public String tag;

    /////////// Constructors ////////////////////////////////////////////////////////////////

    public GradientRamp(Color[] colors) {
        int count;
        double unit;

        ramp = new ArrayList<>();
        count = colors.length;
        unit = 1.0 / ((double)(count - 1));

        for (int i = 0; i < colors.length; i++) {
            ramp.add(new RampStop(colors[i], i * unit));
        }
    }

    public GradientRamp(Color[] _colors, String _tag) {
        this(_colors);
        tag = _tag;
    }

    /////////// Public Methods ////////////////////////////////////////////////////////////////

    public Color getRampColorValue(double offset, double lowerBound, double upperBound){
        double scaledVal;
        int maxByteValue;
        RampStop firstStop, secondStop;

        firstStop = ramp.get(0);
        secondStop = ramp.get(ramp.size() - 1);

        scaledVal = offset;
        maxByteValue = 255;

        if (offset < lowerBound){
            scaledVal = lowerBound;
        }

        if (offset > upperBound){
            scaledVal = upperBound;
        }

        for (RampStop boundary : ramp)
        {
            if (boundary.offset <= scaledVal && boundary.offset > lowerBound) {
                firstStop = boundary;
            }

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

        max = _maxValue / 255;

        channelRange = afterColorChannelValue - beforeColorChannelValue;
        scaleFactor = (_offset - beforeOffset) / (afterOffset - beforeOffset);

        newChannel = (float)(scaleFactor * channelRange);
        result = (float)(newChannel + beforeColorChannelValue);
        byteValue = (int)(((result < max) ? result : max) * 255);

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
