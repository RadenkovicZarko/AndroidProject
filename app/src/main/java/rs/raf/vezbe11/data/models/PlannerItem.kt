package rs.raf.vezbe11.data.models

import rs.raf.vezbe11.data.models.entities.MealEntity

data class PlannerItem(
    var day: String,
    var typeOfMeal: String,
    var meal: MealEntity?
)
