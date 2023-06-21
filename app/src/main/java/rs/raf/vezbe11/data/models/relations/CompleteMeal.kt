package rs.raf.vezbe11.data.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import rs.raf.vezbe11.data.models.entities.*

data class CategoryMealRelation (
    @Embedded val meal: CategoryEntity,
    @Relation(
        parentColumn = "strCategory",
        entityColumn = "strCategory"
    )
    val mealsWithCategory: List<MealEntity>
)


data class AreaMealRelation (
    @Embedded val meal: AreaEntity,
    @Relation(
        parentColumn = "strArea",
        entityColumn = "strAreaId"
    )
    val mealsWithArea: List<MealEntity>
)


data class MealWithIngredients(
    @Embedded val meal: MealEntity,
    @Relation(
        parentColumn = "idMeal",
        entityColumn = "idIngredient",
        associateBy = Junction(IngredientMealEntity::class)
    )
    val ingredients: List<IngredientEntity>
)

data class IngredientsWithMeal(
    @Embedded val ingredient: IngredientEntity,
    @Relation(
        parentColumn = "idIngredient",
        entityColumn = "idMeal",
        associateBy = Junction(IngredientMealEntity::class)
    )
    val ingredients: List<MealEntity>
)