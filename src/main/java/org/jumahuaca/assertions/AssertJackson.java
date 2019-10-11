package org.jumahuaca.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AssertJackson {

	public static void assertSerialization(ObjectMapper mapper, Object object, String string)
			throws JsonProcessingException {
		String result = mapper.writeValueAsString(object);
		assertThat(result).isEqualTo(string);
	}

	public static void assertDeserialization(ObjectMapper mapper, Object object, String string, Class<?> clazz)
			throws IOException {
		assertThat(mapper.readValue(string, clazz)).isEqualTo(object);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void assertListDeserialization(ObjectMapper mapper, List object, String string, TypeReference type)
			throws IOException {
		assertThat((List) mapper.readValue(string, type)).isEqualTo(object);
	}

}
