package org.rs.core.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class MyJsonDateDeserializer extends JsonDeserializer<Date>{

	static private String regex = "^\\d{4}-\\d{2}-\\d{2}( \\d{2}:\\d{2}:\\d{2})?$";
	@Override
	public Date deserialize(JsonParser parser, DeserializationContext desContext) throws IOException, JsonProcessingException {
		
		Date rtnValue = null;
		String dtText = parser.getText();
		if( dtText.matches(regex) ) {
			SimpleDateFormat formater = null;
			if( dtText.length() == 10 )
				formater = new SimpleDateFormat("yyyy-MM-dd");
			else
				formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				rtnValue = formater.parse(dtText);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}		
		return rtnValue;
	}

}
