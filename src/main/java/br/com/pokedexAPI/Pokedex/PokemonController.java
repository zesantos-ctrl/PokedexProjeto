package br.com.pokedexAPI.Pokedex;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pokemon")
@CrossOrigin(origins = "*")
public class PokemonController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://pokeapi.co/api/v2/pokemon/";

    // Buscar um Pokémon por ID ou nome
    @GetMapping("/{idOrName}")
    public Pokemon getPokemon(@PathVariable String idOrName) {
        Map response = restTemplate.getForObject(API_URL + idOrName.toLowerCase(), Map.class);
        if (response == null) return new Pokemon(0, "desconhecido", "");

        int id = (int) response.get("id");
        String name = (String) response.get("name");
        // Pega a imagem front_default dentro do campo sprites
        Map sprites = (Map) response.get("sprites");
        String imageUrl = (String) sprites.get("front_default");

        return new Pokemon(id, name, imageUrl);
    }

    // Buscar lista de Pokémon com apenas ID, nome e imagem
    @GetMapping("/all")
    public List<Pokemon> getAllPokemon(@RequestParam(defaultValue = "20") int limit) {
        Map response = restTemplate.getForObject(API_URL + "?limit=" + limit, Map.class);
        List<Map<String, String>> results = (List<Map<String, String>>) response.get("results");

        return results.stream().map(p -> {
            Map pDetails = restTemplate.getForObject(p.get("url"), Map.class);
            int id = (int) pDetails.get("id");
            String name = (String) pDetails.get("name");

            // Pega a imagem animada
            Map sprites = (Map) pDetails.get("sprites");
            Map versions = (Map) sprites.get("versions");
            Map genV = (Map) versions.get("generation-v");
            Map blackWhite = (Map) genV.get("black-white");
            Map animated = (Map) blackWhite.get("animated");
            String animatedUrl = (String) animated.get("front_default");

            // Retorna a animada
            return new Pokemon(id, name, animatedUrl);
        }).collect(Collectors.toList());
    }
}
