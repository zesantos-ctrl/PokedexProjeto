package br.com.pokedexAPI.Pokedex;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/pokemon")
@CrossOrigin(origins = "*")
public class PokemonController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://pokeapi.co/api/v2/pokemon/";
    private final String SPECIES_URL = "https://pokeapi.co/api/v2/pokemon-species/";

    @GetMapping("/{idOrName}")
    public Features getPokemon(@PathVariable String idOrName) {
        try {
            Map<String, Object> resp = restTemplate.getForObject(API_URL + idOrName.toLowerCase(), Map.class);
            if (resp == null) return emptyFeatures();

            int id = ((Number) resp.get("id")).intValue();
            String name = (String) resp.get("name");

            // Imagem (tenta animada, senão front_default)
            String image = Optional.ofNullable((Map<?, ?>) resp.get("sprites"))
                    .map(sprites -> ((Map<?, ?>)((Map<?, ?>)((Map<?, ?>)((Map<?, ?>)sprites.get("versions"))
                            .get("generation-v"))
                            .get("black-white"))
                            .get("animated"))
                            .get("front_default"))
                    .map(Object::toString)
                    .orElseGet(() -> Optional.ofNullable((Map<?, ?>) resp.get("sprites"))
                            .map(sprites -> sprites.get("front_default"))
                            .map(Object::toString)
                            .orElse(""));

            // Tipos
            List<String> types = ((List<Map<String, Map<String, String>>>) resp.getOrDefault("types", List.of()))
                    .stream()
                    .map(t -> t.get("type").get("name"))
                    .toList();

            // Habilidades
            List<String> abilities = ((List<Map<String, Map<String, String>>>) resp.getOrDefault("abilities", List.of()))
                    .stream()
                    .map(a -> {
                        Map<String, String> abilityMap = a.get("ability");
                        String abilityName = abilityMap.get("name");
                        String abilityUrl = abilityMap.get("url");
                        try {
                            Map abilityResp = restTemplate.getForObject(abilityUrl, Map.class);
                            List<Map<String, Object>> effects = (List<Map<String, Object>>) abilityResp.get("effect_entries");
                            String effect = effects.stream()
                                    .filter(e -> ((Map)e.get("language")).get("name").equals("en"))
                                    .map(e -> (String) e.get("short_effect"))
                                    .findFirst()
                                    .orElse("");
                            return abilityName + " - " + effect;
                        } catch (Exception ex) {
                            return abilityName;
                        }
                    })
                    .toList();

            int height = ((Number) resp.getOrDefault("height", 0)).intValue();
            int weight = ((Number) resp.getOrDefault("weight", 0)).intValue();

            // Species: descrição e cor
            Map<String, Object> speciesResp = restTemplate.getForObject(SPECIES_URL + name, Map.class);
            String description = "";
            String color = "unknown";
            if (speciesResp != null) {
                List<Map<String, Object>> flavorEntries = (List<Map<String, Object>>) speciesResp.getOrDefault("flavor_text_entries", List.of());
                description = flavorEntries.stream()
                        .filter(f -> ((Map)f.get("language")).get("name").equals("en"))
                        .map(f -> ((String)f.get("flavor_text")).replace("\n"," ").replace("\f"," "))
                        .findFirst()
                        .orElse("");

                Map colorMap = (Map) speciesResp.get("color");
                if (colorMap != null) color = (String) colorMap.get("name");
            }

            return new Features(id, name, image, height, weight, types, abilities, description, color);

        } catch (RestClientException ex) {
            return emptyFeatures();
        }
    }

    private Features emptyFeatures() {
        return new Features(0, "desconhecido", "", 0, 0,
                List.of(), List.of(), "", "unknown");
    }
}
