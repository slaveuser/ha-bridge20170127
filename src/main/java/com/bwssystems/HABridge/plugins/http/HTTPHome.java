package com.bwssystems.HABridge.plugins.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bwssystems.HABridge.BridgeSettingsDescriptor;
import com.bwssystems.HABridge.Home;
import com.bwssystems.HABridge.api.CallItem;
import com.bwssystems.HABridge.api.NameValue;
import com.bwssystems.HABridge.api.hue.HueError;
import com.bwssystems.HABridge.api.hue.HueErrorResponse;
import com.bwssystems.HABridge.dao.DeviceDescriptor;
import com.bwssystems.HABridge.hue.BrightnessDecode;
import com.bwssystems.HABridge.hue.MultiCommandUtil;
import com.bwssystems.HABridge.hue.TimeDecode;
import com.google.gson.Gson;

public class HTTPHome implements Home {
	private static final Logger log = LoggerFactory.getLogger(HTTPHome.class);
	private HTTPHandler anHttpHandler;

	public HTTPHome(BridgeSettingsDescriptor bridgeSettings) {
		super();
		createHome(bridgeSettings);
	}

	@Override
	public String deviceHandler(CallItem anItem, MultiCommandUtil aMultiUtil, String lightId, int intensity,
			Integer targetBri,Integer targetBriInc, DeviceDescriptor device, String body) {
		String responseString = null;
		
		//Backwards Compatibility Items
		if(anItem.getHttpVerb() == null || anItem.getHttpVerb().isEmpty())
		{
			if(device.getHttpVerb() != null && !device.getHttpVerb().isEmpty())
				anItem.setHttpVerb(device.getHttpVerb());
		}

		if(anItem.getHttpHeaders() == null || anItem.getHttpHeaders().isEmpty()) {
			if(device.getHeaders() != null && !device.getHeaders().isEmpty() )
				anItem.setHttpHeaders(device.getHeaders());
		}

		log.debug("executing HUE api request to Http "
				+ (anItem.getHttpVerb() == null ? "GET" : anItem.getHttpVerb()) + ": "
				+ anItem.getItem().getAsString());

			String anUrl = BrightnessDecode.calculateReplaceIntensityValue(anItem.getItem().getAsString(),
					intensity, targetBri, targetBriInc, false);

			anUrl = TimeDecode.replaceTimeValue(anUrl);
			String aBody = null;
			if(anItem.getHttpBody()!= null && !anItem.getHttpBody().isEmpty()) {
				aBody = BrightnessDecode.calculateReplaceIntensityValue(anItem.getHttpBody(),
						intensity, targetBri, targetBriInc, false);
				aBody = TimeDecode.replaceTimeValue(aBody);
			}
			// make call
			if (anHttpHandler.doHttpRequest(anUrl, anItem.getHttpVerb(), anItem.getContentType(), aBody,
					new Gson().fromJson(anItem.getHttpHeaders(), NameValue[].class)) == null) {
				log.warn("Error on calling url to change device state: " + anUrl);
				responseString = new Gson().toJson(HueErrorResponse.createResponse("6", "/lights/" + lightId,
						"Error on calling url to change device state", "/lights/"
						+ lightId + "state", null, null).getTheErrors(), HueError[].class);
			}
		return responseString;
	}

	@Override
	public Home createHome(BridgeSettingsDescriptor bridgeSettings) {
		anHttpHandler = new HTTPHandler();
		log.info("Http Home created.");
		return this;
	}

	@Override
	public Object getItems(String type) {
		// Not a resource
		return null;
	}

	@Override
	public void closeHome() {
		anHttpHandler.closeHandler();
	}

}
