package com.github.lodolant.java21.kata;

public record UrlProcessor() implements StringTemplate.Processor<String, IllegalArgumentException> {

	public String process(StringTemplate stringTemplate) throws IllegalArgumentException {
		int valuesNumber = stringTemplate.values().size();
		for (int i = 0; i < valuesNumber; i++) {
			Object value = stringTemplate.values().get(i);
			final int currentIndex = i;
			switch (value) {
			case null:
				throw new IllegalArgumentException("Missing value");
			case String string when currentIndex + 3 > valuesNumber:
				throw new IllegalArgumentException("Missing Parameter");
			case String string: {
				Object value1 = stringTemplate.values().get(i + 1);
				Object value2 = stringTemplate.values().get(i + 2);

				if (string.equals("/") && value2 instanceof Integer dividand && dividand.intValue() == 0) {
					throw new IllegalArgumentException("Divide by 0");
				}
				if (value1 instanceof String || value2 instanceof String) {
					throw new IllegalArgumentException("Wrong type");
				}
				break;
			}
			default: {
				continue;
			}
			}
		}
		return StringTemplate.interpolate(stringTemplate.fragments(),
				stringTemplate.values().stream().map(this::sanitize).toList());
	}

	private String sanitize(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Missing parameter");
		}
		if (value instanceof String valueString) {
			return valueString.replace(" ", "+").replace("<", "%3C").replace(">", "%3E").replace("/", "%2F");
		}
		return value.toString();
	}
}