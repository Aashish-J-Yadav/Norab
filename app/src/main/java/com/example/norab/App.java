package com.example.norab;

class App {
	private final String packageName, label;
	private boolean enabled;
	
	App(String pkg, String name, boolean enable) {
		packageName = pkg;
		label = name;
		enabled = enable;
	}
	
	/**
	 * Updates self in database.
	 * @return This instance.
	 */
	App updateDb() {
		Database.addOrUpdateApp(this);
		return this;
	}
	
	void setEnabled(boolean enable, boolean updateDb) {
		enabled = enable;
		if (updateDb) Database.updateAppEnable(this);
	}
	
	/** Removes self from database. */
	void remove() {
		Database.removeApp(this);
	}
	
	String getLabel() {
		return label;
	}
	
	String getPackage() {
		return packageName;
	}
	
	boolean getEnabled() {
		return enabled;
	}
}
