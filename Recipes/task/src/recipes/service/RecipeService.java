package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.Recipe;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final String RECIPE_BY_ID_NOT_FOUND_ERR_MSG = "Recipe with specified id (%s) does not exist";

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe saveRecipe(Recipe recipe) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        recipe.setUserName(auth.getName());
        recipe.setDate(LocalDateTime.now());

        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipeById(long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> searchRecipesByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchRecipesByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public void updateRecipe(long id, Recipe recipe) {

        Optional<Recipe> foundedRecipe = recipeRepository.findById(id);

        if (foundedRecipe.isPresent()) {
            if (isValidUser(foundedRecipe.get())) {
                mapRecipeFields(recipe, foundedRecipe.get());
                recipeRepository.save(foundedRecipe.get());
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(RECIPE_BY_ID_NOT_FOUND_ERR_MSG, id));
        }
    }

    public void deleteRecipe(long id) {
        Optional<Recipe> foundedRecipe = recipeRepository.findById(id);

        if (foundedRecipe.isPresent()) {
            if (isValidUser(foundedRecipe.get())) {
                recipeRepository.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(RECIPE_BY_ID_NOT_FOUND_ERR_MSG, id));
        }
    }

    private boolean isValidUser(Recipe foundedRecipe) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return foundedRecipe.getUserName().equals(auth.getName());
    }

    private void mapRecipeFields(Recipe srcRecipe, Recipe dstRecipe) {
        dstRecipe.setName(srcRecipe.getName());
        dstRecipe.setDescription(srcRecipe.getDescription());
        dstRecipe.setCategory(srcRecipe.getCategory());
        dstRecipe.setIngredients(srcRecipe.getIngredients());
        dstRecipe.setDirections(srcRecipe.getDirections());
        dstRecipe.setDate(LocalDateTime.now());
    }
}
