package com.example.norab;
import android.content.Context;
import java.util.Iterator;
import java.util.Set;

enum IgnoreReason {
	SERVICE_STOPPED(R.string.reason_service_stopped),
	SUSPENDED(R.string.reason_suspended),
	QUIET(R.string.reason_quiet),
	SHAKE(R.string.reason_shake),
	SILENT(R.string.reason_silent),
	CALL(R.string.reason_call),
	SCREEN_OFF(R.string.reason_screen_off),
	SCREEN_ON(R.string.reason_screen_on),
	HEADSET_OFF(R.string.reason_headset_off),
	HEADSET_ON(R.string.reason_headset_on),
	APP(R.string.reason_app),
	STRING(R.string.reason_string),
	EMPTY_MSG(R.string.reason_empty_msg),
	IDENTICAL(R.string.reason_identical);
	
	private final int stringId;
	
	IgnoreReason(int resId) {
		this.stringId = resId;
	}
	
	/**
	 * @param c Context required to get the string resource.
	 * @return The user-visible string for this ignore reason.
	 */
	String getString(Context c) {
		return c.getString(stringId);
	}
	
	/**
	 * Converts a set of ignore reasons to a comma-separated string.
	 * @param reasons The set to be converted.
	 * @param c Context required to get string resources.
	 * @return The resulting string.
	 */
	static String convertSetToString(Set<IgnoreReason> reasons, Context c) {
		StringBuilder builder = new StringBuilder();
		Iterator<IgnoreReason> iterator = reasons.iterator();
		while (iterator.hasNext()) {
			builder.append(iterator.next().getString(c));
			if (iterator.hasNext()) builder.append(", ");
		}
		return builder.toString();
	}
}
