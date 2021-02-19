package org.rs.core.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.lang.reflect.Type;

@JacksonStdImpl
@SuppressWarnings("serial")
public class RsJsonNullSerializer extends StdSerializer<Object>{

public final static RsJsonNullSerializer instance = new RsJsonNullSerializer();
    
    private RsJsonNullSerializer() { super(Object.class); }
    
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        //gen.writeNull();
    	gen.writeString("");
    }

    /**
     * Although this method should rarely get called, for convenience we should override
     * it, and handle it same way as "natural" types: by serializing exactly as is,
     * without type decorations. The most common possible use case is that of delegation
     * by JSON filter; caller can not know what kind of serializer it gets handed.
     */
    @Override
    public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer)
        throws IOException
    {
        //gen.writeNull();
    	gen.writeString("");
    }
    
    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        //return createSchemaNode("null");
    	return createSchemaNode("");
    }
    
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        visitor.expectNullFormat(typeHint);
    }
}
