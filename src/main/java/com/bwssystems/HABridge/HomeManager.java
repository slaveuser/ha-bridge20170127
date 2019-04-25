package com.bwssystems.HABridge;

import java.util.HashMap;
import java.util.Map;

import com.bwssystems.HABridge.devicemanagmeent.ResourceHandler;
import com.bwssystems.HABridge.plugins.NestBridge.NestHome;
import com.bwssystems.HABridge.plugins.domoticz.DomoticzHome;
import com.bwssystems.HABridge.plugins.exec.CommandHome;
import com.bwssystems.HABridge.plugins.hal.HalHome;
import com.bwssystems.HABridge.plugins.harmony.HarmonyHome;
import com.bwssystems.HABridge.plugins.hass.HassHome;
import com.bwssystems.HABridge.plugins.http.HTTPHome;
import com.bwssystems.HABridge.plugins.hue.HueHome;
import com.bwssystems.HABridge.plugins.mqtt.MQTTHome;
import com.bwssystems.HABridge.plugins.tcp.TCPHome;
import com.bwssystems.HABridge.plugins.udp.UDPHome;
import com.bwssystems.HABridge.plugins.vera.VeraHome;
import com.bwssystems.HABridge.util.UDPDatagramSender;

public class HomeManager {
	Map<String, Home> homeList;
	Map<String, Home> resourceList;
	
	public HomeManager() {
		homeList = new HashMap<String, Home>();
		resourceList = new HashMap<String, Home>();
	}

	// factory method
	public void buildHomes(BridgeSettingsDescriptor bridgeSettings, UDPDatagramSender aUdpDatagramSender) {
		Home aHome = null;
        //setup the harmony connection if available
		aHome = new HarmonyHome(bridgeSettings);
		resourceList.put(DeviceMapTypes.HARMONY_ACTIVITY[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.HARMONY_ACTIVITY[DeviceMapTypes.typeIndex], aHome);
		resourceList.put(DeviceMapTypes.HARMONY_BUTTON[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.HARMONY_BUTTON[DeviceMapTypes.typeIndex], aHome);
        //setup the nest connection if available
		aHome = new NestHome(bridgeSettings);
		resourceList.put(DeviceMapTypes.NEST_HOMEAWAY[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.NEST_HOMEAWAY[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.NEST_THERMO_SET[DeviceMapTypes.typeIndex], aHome);
        //setup the hue passtrhu configuration if available
		aHome = new HueHome(bridgeSettings);
		resourceList.put(DeviceMapTypes.HUE_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.HUE_DEVICE[DeviceMapTypes.typeIndex], aHome);
        //setup the hal configuration if available
		aHome = new HalHome(bridgeSettings);
		resourceList.put(DeviceMapTypes.HAL_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.HAL_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.HAL_BUTTON[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.HAL_HOME[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.HAL_THERMO_SET[DeviceMapTypes.typeIndex], aHome);
        //setup the mqtt handlers if available
		aHome = new MQTTHome(bridgeSettings);
		resourceList.put(DeviceMapTypes.MQTT_MESSAGE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.MQTT_MESSAGE[DeviceMapTypes.typeIndex], aHome);
        //setup the HomeAssistant configuration if available
		aHome = new HassHome(bridgeSettings);
		resourceList.put(DeviceMapTypes.HASS_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.HASS_DEVICE[DeviceMapTypes.typeIndex], aHome);
		//setup the command execution Home
		aHome = new CommandHome(bridgeSettings);
		homeList.put(DeviceMapTypes.EXEC_DEVICE_COMPAT[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.CMD_DEVICE[DeviceMapTypes.typeIndex], aHome);
		//setup the http handler Home
		aHome = new HTTPHome(bridgeSettings);
		homeList.put(DeviceMapTypes.HTTP_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.CUSTOM_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.VERA_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.VERA_SCENE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.DOMOTICZ_DEVICE[DeviceMapTypes.typeIndex], aHome);
		//setup the tcp handler Home
		aHome = new TCPHome(bridgeSettings);
		homeList.put(DeviceMapTypes.TCP_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.TCP_DEVICE_COMPAT[DeviceMapTypes.typeIndex], aHome);
		//setup the udp handler Home
		aHome = new UDPHome(bridgeSettings, aUdpDatagramSender);
		homeList.put(DeviceMapTypes.UDP_DEVICE[DeviceMapTypes.typeIndex], aHome);
		homeList.put(DeviceMapTypes.UDP_DEVICE_COMPAT[DeviceMapTypes.typeIndex], aHome);
		
		aHome = new VeraHome(bridgeSettings);
		resourceList.put(DeviceMapTypes.VERA_DEVICE[DeviceMapTypes.typeIndex], aHome);
		resourceList.put(DeviceMapTypes.VERA_SCENE[DeviceMapTypes.typeIndex], aHome);
        //setup the HomeAssistant configuration if available
		aHome = new DomoticzHome(bridgeSettings);
		resourceList.put(DeviceMapTypes.DOMOTICZ_DEVICE[DeviceMapTypes.typeIndex], aHome);
	}
	
	public Home findHome(String type) {
		return homeList.get(type);
	}
	public ResourceHandler findResource(String type) {
		return resourceList.get(type);
	}

	public void closeHomes() {
		
	}
}
