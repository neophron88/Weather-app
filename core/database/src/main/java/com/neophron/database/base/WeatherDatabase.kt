package com.neophron.database.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.neophron.database.current_weather.CurrentWeatherDao
import com.neophron.database.current_weather.CurrentWeatherEntity
import com.neophron.database.several_days_weather.DayWeatherEntity
import com.neophron.database.several_days_weather.DaysWeatherDao

@Database(
    version = 1,
    entities = [DayWeatherEntity::class, CurrentWeatherEntity::class],
    exportSchema = true
)
abstract class WeatherDatabase : RoomDatabase() {


    abstract fun getCurrentWeatherDao(): CurrentWeatherDao

    abstract fun getDaysWeatherDao(): DaysWeatherDao

    companion object {
        @Volatile
        private var database: WeatherDatabase? = null

        fun getDatabase(appContext: Context): WeatherDatabase {
            val temp1 = database
            if (temp1 != null) return temp1

            synchronized(this) {
                val temp2 = database
                if (temp2 != null) return temp2
                val temp3 = Room.databaseBuilder(
                    appContext,
                    WeatherDatabase::class.java,
                    "weather_db"
                ).build()
                database = temp3
                return temp3
            }
        }
    }

}