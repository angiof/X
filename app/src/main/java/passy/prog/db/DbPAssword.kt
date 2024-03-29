package passy.prog.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import passy.prog.utils.UtilsFuns

@Database(entities = [EntityPassword::class], version = 1, exportSchema = false)
abstract class DbPAssword : RoomDatabase() {

    abstract fun passwordDaos(): DaoPasswords

    companion object {

        //instanzia questo db e informa ai altri trad che questa intanzia esiste
        @Volatile
        private var INSTANCE: DbPAssword? = null

        fun getDatabse(context: Context): DbPAssword {
            //se la instazia non è null crea il db
            //altrimenti carica il suo contenuto
            val supportFactory = SupportFactory(SQLiteDatabase.getBytes("password".toCharArray()))

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DbPAssword::class.java,
                    "dbPassword"
                )
                    .openHelperFactory(supportFactory)
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}