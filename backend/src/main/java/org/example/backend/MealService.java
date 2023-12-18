package org.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealService {

//    @Autowired
    private final MealRepo repo;

    public List<MealRecord> getAllMeals() {
        return repo.findAll();
    }

    public MealRecord getRandomMeal(){
        //return repo.findAll().get((int) (Math.random() * repo.findAll().size()));
        int randomIndex=(int)(Math.random()* repo.count());
        return repo.findAll().get(randomIndex);

    }

    //getById
    public MealRecord getMealById(String _id) {
        return repo.findById(_id).orElse(null);
    }


    public MealRecord getRandomMealByCategory(String category) throws CategoryNotFoundException{
        List<MealRecord> meals = repo.findAllByStrCategoryIgnoreCase(category)
                .orElseThrow(()->new CategoryNotFoundException("No such category found"));
        return meals.get((int) (Math.random() * meals.size()));

    }
/*
    public List<MealRecord> getMealsByCategory(String category) {
        return repo.findAllByStrCategoryIgnoreCase(category);
    }
*/

    public List<MealRecord> getMealsByFirstLetter(String letter) {
        return repo.findAllByStrMealStartingWithIgnoreCase(letter);
    }

    public List <MealRecord> getMealsByCategory(String category) throws CategoryNotFoundException{
        MealCategory mealCategory=MealCategory.fromString(category);

        return repo.findAllByStrCategoryIgnoreCase(mealCategory.getCategoryName())
                .orElseThrow(() ->new CategoryNotFoundException("The category you are searching for is not existing"));
    }

    public List <MealRecord> getMealsByArea(String area) throws AreaNotFoundException{
        return repo.findAllByStrAreaIgnoreCase(area)
                .orElseThrow(()-> new AreaNotFoundException("Area not found exception"));
    }
    public List <MealRecord> getMealsByIngredient1(String ingredient) throws IngredientNotFoundException {
        return repo.findAllByStrIngredient1ContainingIgnoreCase(ingredient)
                .orElseThrow(()->new IngredientNotFoundException("Ingredient not found"));
    }

    public MealRecord addMeal(MealRecord mealRecord){
        return repo.insert(mealRecord);
    }

    public MealRecord updateMeal(MealRecord mealRecord){
        return repo.save(mealRecord);
    }

    public void deleteMeal(String _id){
        repo.deleteById(_id);
    }

}
