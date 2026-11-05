
package com.raspberry.client.settings.types;

import com.raspberry.client.settings.Setting;

/**
 * A numeric setting with min/max bounds and increment.
 */
public class NumberSetting extends Setting {

    private double value;
    private final double min;
    private final double max;
    private final double increment;

    public NumberSetting(String name, String description, double defaultValue, double min, double max, double increment) {
        super(name, description);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = Math.max(min, Math.min(max, value));
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getIncrement() {
        return increment;
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            setValue(((Number) value).doubleValue());
        }
    }
}
