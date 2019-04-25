package com.bwssystems.HABridge.plugins.hal;

public class HalDevice {
	private String haldevicetype;
	private String haldevicename;
	private String haladdress;
	private String halname;
	private DeviceElements buttons;
	public String getHaldevicetype() {
		return haldevicetype;
	}
	public void setHaldevicetype(String haldevicetype) {
		this.haldevicetype = haldevicetype;
	}
	public String getHaldevicename() {
		return haldevicename;
	}
	public void setHaldevicename(String haldevicename) {
		this.haldevicename = haldevicename;
	}
	public String getHaladdress() {
		return haladdress;
	}
	public void setHaladdress(String haladdress) {
		this.haladdress = haladdress;
	}
	public String getHalname() {
		return halname;
	}
	public void setHalname(String halname) {
		this.halname = halname;
	}
	public DeviceElements getButtons() {
		return buttons;
	}
	public void setButtons(DeviceElements buttons) {
		this.buttons = buttons;
	}
}
