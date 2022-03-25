package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Long> saveRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        return Map.of("id", savedRecipe.getId());
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipeById(@PathVariable long id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);

        return recipe.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/recipe/search")
    public List<Recipe> searchRecipes(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        if ((category == null && name == null) || (category != null && name != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "0 or more than 1 parameters were passed");
        }

        if (category != null) {
            return recipeService.searchRecipesByCategory(category);
        } else {
            return recipeService.searchRecipesByName(name);
        }
    }

    @PutMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable long id, @Valid @RequestBody Recipe recipe) {
        recipeService.updateRecipe(id, recipe);
    }

    @DeleteMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
    }
}
