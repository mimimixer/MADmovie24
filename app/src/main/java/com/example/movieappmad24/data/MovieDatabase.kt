package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieappmad24.data.worker.WorkManagerSeedDatabaseRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage

@Database(
entities = [Movie:: class, MovieImage::class], // tables in the db, separated by comma
version = 29, // schema version; whenever you change schema you have to increase the version number
exportSchema = false // for schema version history updates
)

abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao // Dao-instance so that DB knows about the Dao
    //abstract fun movieImageDao (): MovieImageDao
    //abstract fun movieWithImageDao (): MovieWithImageDao

    //val movies = getMovies()


    // add more daos here if you have multiple daos/tables
    companion object {
        @Volatile // never cache the value of instance -> prevent caches when threading
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return instance ?: synchronized(this) { // wrap in synchronized block to prevent race conditions
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db_28")
                    .fallbackToDestructiveMigration() // if schema changes wipe the db
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            println("// Seed the database with initial data")
                            //WorkManagerSeedDatabaseRepository(context).seedAll()
                            WorkManagerSeedDatabaseRepository(context).seedMovie()
                            WorkManagerSeedDatabaseRepository(context).seedImage()
                        }
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            println("// Opened existing database")
                        }
                    })
                    .build()                        // returns a db instance
                    .also {
                        instance =
                            it
                    }// override the class instance with newly created db
                }
        }
    }
}
