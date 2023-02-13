package com.trackerimmo.geocoder;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;


public class AddressFormat extends Format {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Available parameters:
	 *
	 * %p - postcode %c - country %s - state %d - district %t - settlement (town) %u
	 * - suburb %r - street (road) %h - house %f - formatted address
	 *
	 */

	private final String format;

	public AddressFormat() {
		this("%h %r, %t, %s, %c");
	}

	public AddressFormat(String format) {
		this.format = format;
	}

	private static String replace(String s, String key, String value) {
		if (value != null) {
			s = s.replace(key, value);
		} else {
			s = s.replaceAll("[, ]*" + key, "");
		}
		return s;
	}

	@Override
	public StringBuffer format(Object o, StringBuffer stringBuffer, FieldPosition fieldPosition) {
		Address address = (Address) o;
		String result = format;

		result = replace(result, "%p", address.getPostcode());
		result = replace(result, "%c", address.getCountry());
		result = replace(result, "%s", address.getState());
		result = replace(result, "%d", address.getDistrict());
		result = replace(result, "%t", address.getSettlement());
		result = replace(result, "%u", address.getSuburb());
		result = replace(result, "%r", address.getStreet());
		result = replace(result, "%h", address.getHouse());
//		result = replace(result, "%f", address.getFormattedAddress());

		result = result.replaceAll("^[, ]*", "");

		return stringBuffer.append(result);
	}

	@Override
	public Object parseObject(String arg0, ParsePosition arg1) {
		throw new UnsupportedOperationException();
	}

}
