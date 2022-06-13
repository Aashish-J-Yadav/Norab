package com.example.norab;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class Shake implements SensorEventListener {
	private final Context context;
	private final SensorManager manager;
	private final Sensor sensor;
	private OnShakeListener listener;
	private int threshold, overThresholdCount;
	private float accelCurrent, accelLast;
	
	Shake(Context c) {
		context = c;
		manager = (SensorManager)c.getSystemService(Context.SENSOR_SERVICE);
		assert manager != null; // Prevent Lint warning. Should never be null, I want a crash report if it is.
		sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}
	
	void enable() {
		if (listener == null) return;
		try {
			threshold = Integer.parseInt(Common.getPrefs(context).getString(context.getString(R.string.key_shake_threshold), null));
		} catch (NumberFormatException e) {
			// Don't enable if threshold setting is blank
			return;
		}
		manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	void disable() {
		manager.unregisterListener(this);
		accelCurrent = 0;
		accelLast = 0;
		overThresholdCount = 0;
	}
	
	void setOnShakeListener(OnShakeListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		accelCurrent = (float)Math.sqrt(x * x + y * y + z * z);
		float accel = accelCurrent - accelLast;
		if (accelLast != 0 && Math.abs(accel) > threshold / 10) {
			overThresholdCount++;
			if (overThresholdCount >= 2) {
				listener.onShake();
			}
		} else {
			overThresholdCount = 0;
		}
		accelLast = accelCurrent;
	}
	
	interface OnShakeListener {
		void onShake();
	}
}
