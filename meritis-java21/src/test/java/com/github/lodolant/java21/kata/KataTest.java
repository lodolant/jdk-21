package com.github.lodolant.java21.kata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KataTest {
	@Test
	void str_basicConcat() throws IllegalArgumentException {
		String weather = "rainy";

		String message = STR."Today, weather will be \{weather}";

		Assertions.assertEquals("Today, weather will be rainy", message);
	}

	@Test
	void str_basicFormatted() throws IllegalArgumentException {
		String repas = "Risotto d'asperges";

		String message = STR."Aujourd'hui, le chef vous propose: \{repas}";

		Assertions.assertEquals("Aujourd'hui, le chef vous propose: Risotto d'asperges", message);
	}

	@Test
	void str_complex() throws IllegalArgumentException {
		String expected = """
				Bonjour Lionel,
				Pour te facturer à ton client, nous avons besoin que tu remplisses ton CRA avant la fin du mois sur notre nouveau site:
				http://virus.fr
				Si tu as un problème, n'hésite pas à revenir vers nous 😄
				La sécurité étant l'affaire de tous, attention à ne jamais aller sur des sites suspects !
				""";

		String nom = "Lionel";
		String site = "http://virus.fr";
		String emoji = new String(Character.toChars(0x1F604));
		String message = STR."""
				Bonjour \{nom},
				Pour te facturer à ton client, nous avons besoin que tu remplisses ton CRA avant la fin du mois sur notre nouveau site:
				\{site}
				Si tu as un problème, n'hésite pas à revenir vers nous \{emoji}
				La sécurité étant l'affaire de tous, attention à ne jamais aller sur des sites suspects !
				"""
				.formatted(nom, site, emoji);
		System.out.println(message);
		Assertions.assertEquals(expected, message);
	}
}
