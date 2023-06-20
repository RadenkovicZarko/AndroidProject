package rs.raf.vezbe11.data.repositories


import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.MealEntity
import rs.raf.vezbe11.data.models.Resource


interface MealRepository {
    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<MealEntity>>
    fun getAllByName(name: String): Observable<List<MealEntity>>
    fun insert(meal: MealEntity): Completable
}