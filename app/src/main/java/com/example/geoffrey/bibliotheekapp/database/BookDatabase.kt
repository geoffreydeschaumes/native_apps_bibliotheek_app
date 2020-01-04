package com.example.geoffrey.bibliotheekapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.geoffrey.bibliotheekapp.models.Book

/**
 * RoomDatabase acts as a database holder. The class is absract, because Room creates the implementation
 * Database annotation dcleares the entitie (Book::class) for the database and set the version number
 * The companion object returns the BookDatabaseDao, Room generates the body
 */
@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
   abstract val bookDatabaseDao:BookDatabaseDao

    /**
     * The companion object returns the BookDatabaseDao, Room generates the body
     */
    companion object {
        /**
         * Volatile Makes sure the INSTANCE is always up-to-date and the same to all execution threads
         * The changes for one thread are visible to all other threads immediately
         * All the writes and reads will be done to and from the main memory
         */
        @Volatile
        private var INSTANCE: BookDatabase? = null

        /**
         * There is only one instance needed for the room database
         * The database is a Singleton (only 1 database is needed)
         */
        fun getInstance(context: Context):BookDatabase{
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        BookDatabase::class.java,
                        "book_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}