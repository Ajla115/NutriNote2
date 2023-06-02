package com.example.nutrinote2.databasedata

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Math.random
import kotlin.random.Random

class DBHandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME_COL + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + EMAIL_COL + " TEXT)")

        val createFoodsTableQuery = ("CREATE TABLE " + FOODS_TABLE_NAME + " ("
                + FOODS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FOODS_NAME_COL + " TEXT,"
                + FOODS_CATEGORY_COL + " TEXT,"
                + FOODS_CARBS_COL + " INTEGER,"
                + FOODS_PROTEIN_COL + " INTEGER,"
                + FOODS_FAT_COL + " INTEGER)")

        val createUserFoodsTableQuery = ("CREATE TABLE " + USER_FOODS_TABLE_NAME + " ("
                + USER_FOODS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_FOODS_USER_ID_COL + " INTEGER, "
                + USER_FOODS_FOOD_ID_COL + " INTEGER, "
                + USER_FOODS_DATE_COL + " TEXT, "
                + "FOREIGN KEY(" + USER_FOODS_USER_ID_COL + ") REFERENCES " + TABLE_NAME + "(" + ID_COL + "), "
                + "FOREIGN KEY(" + USER_FOODS_FOOD_ID_COL + ") REFERENCES " + FOODS_TABLE_NAME + "(" + FOODS_ID_COL + ")"
                + ")")

        val createLogsQuery = ("CREATE TABLE " + LOGS_TABLE_NAME + " ("
                + LOGS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LOGS_EMAIL_COL + " TEXT,"
                + LOGS_TEST_COL + " INTEGER)")

        val createDailyConsumptionTableQuery = ("CREATE TABLE " + DAILY_CONSUMPTION_TABLE_NAME + " ("
                + DAILY_CONSUMPTION_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DAILY_CONSUMPTION_USER_ID_COL + " INTEGER, "
                + DAILY_CONSUMPTION_PROTEIN_COL + " REAL DEFAULT 0, "
                + DAILY_CONSUMPTION_CARBS_COL + " REAL DEFAULT 0, "
                + DAILY_CONSUMPTION_FAT_COL + " REAL DEFAULT 0, "
                + "FOREIGN KEY(" + DAILY_CONSUMPTION_USER_ID_COL + ") REFERENCES " + TABLE_NAME + "(" + ID_COL + ")"
                + ")")

        Log.d("DBHandler", "onCreate method called")

        db.execSQL(createLogsQuery)
        db.execSQL(createTableQuery)
        db.execSQL(createFoodsTableQuery)
        db.execSQL(createUserFoodsTableQuery)
        db.execSQL(createDailyConsumptionTableQuery)
    }

    fun insertDailyConsumption(userId: Int?, protein: Float, carbs: Float, fat: Float) {
        val db = writableDatabase
        val values = ContentValues()

        values.put(DAILY_CONSUMPTION_USER_ID_COL, userId)
        values.put(DAILY_CONSUMPTION_PROTEIN_COL, protein)
        values.put(DAILY_CONSUMPTION_CARBS_COL, carbs)
        values.put(DAILY_CONSUMPTION_FAT_COL, fat)

        db.insert(DAILY_CONSUMPTION_TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getFoodProteinById(foodId: Int?): Float {
        val db = readableDatabase
        val selectQuery = "SELECT $FOODS_PROTEIN_COL FROM $FOODS_TABLE_NAME WHERE $FOODS_ID_COL = ?"
        val selectionArgs = arrayOf(foodId.toString())

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getFloat(cursor.getColumnIndex(FOODS_PROTEIN_COL))
            }
        }

        db.close()
        return 0.0f
    }

    @SuppressLint("Range")
    fun getFoodCarbsById(foodId: Int?): Float {
        val db = readableDatabase
        val selectQuery = "SELECT $FOODS_CARBS_COL FROM $FOODS_TABLE_NAME WHERE $FOODS_ID_COL = ?"
        val selectionArgs = arrayOf(foodId.toString())

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getFloat(cursor.getColumnIndex(FOODS_CARBS_COL))
            }
        }

        db.close()
        return 0.0f
    }

    @SuppressLint("Range")
    fun getFoodFatById(foodId: Int?): Float {
        val db = readableDatabase
        val selectQuery = "SELECT $FOODS_FAT_COL FROM $FOODS_TABLE_NAME WHERE $FOODS_ID_COL = ?"
        val selectionArgs = arrayOf(foodId.toString())

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getFloat(cursor.getColumnIndex(FOODS_FAT_COL))
            }
        }

        db.close()
        return 0.0f
    }


    fun updateDailyConsumption(userId: Int?, protein: Float, carbs: Float, fat: Float) {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DAILY_CONSUMPTION_PROTEIN_COL, protein)
        values.put(DAILY_CONSUMPTION_CARBS_COL, carbs)
        values.put(DAILY_CONSUMPTION_FAT_COL, fat)

        val whereClause = "$DAILY_CONSUMPTION_USER_ID_COL = ?"
        val whereArgs = arrayOf(userId.toString())

        db.update(DAILY_CONSUMPTION_TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    @SuppressLint("Range")
    fun getFoodIdByName(foodName: String): Int? {
        val db = readableDatabase
        val selectQuery = "SELECT $FOODS_ID_COL FROM $FOODS_TABLE_NAME WHERE $FOODS_NAME_COL = ?"
        val selectionArgs = arrayOf(foodName)

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndex(FOODS_ID_COL))
            }
        }

        db.close()
        return null
    }

    @SuppressLint("Range")
    fun getDailyConsumptionProtein(userId: Int?): Float {
        val db = readableDatabase
        var protein: Float = 0f

        val selectQuery = "SELECT $DAILY_CONSUMPTION_PROTEIN_COL FROM $DAILY_CONSUMPTION_TABLE_NAME WHERE $DAILY_CONSUMPTION_USER_ID_COL = ?"
        val selectionArgs = arrayOf(userId.toString())

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                protein = cursor.getFloat(cursor.getColumnIndex(DAILY_CONSUMPTION_PROTEIN_COL))
            }
        }

        db.close()
        return protein
    }

    @SuppressLint("Range")
    fun getDailyConsumptionCarbs(userId: Int?): Float {
        val db = readableDatabase
        var carbs: Float = 0f

        val selectQuery = "SELECT $DAILY_CONSUMPTION_CARBS_COL FROM $DAILY_CONSUMPTION_TABLE_NAME WHERE $DAILY_CONSUMPTION_USER_ID_COL = ?"
        val selectionArgs = arrayOf(userId.toString())

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                carbs = cursor.getFloat(cursor.getColumnIndex(DAILY_CONSUMPTION_CARBS_COL))
            }
        }

        db.close()
        return carbs
    }

    @SuppressLint("Range")
    fun getDailyConsumptionFat(userId: Int?): Float {
        val db = readableDatabase
        var fat: Float = 0f

        val selectQuery = "SELECT $DAILY_CONSUMPTION_FAT_COL FROM $DAILY_CONSUMPTION_TABLE_NAME WHERE $DAILY_CONSUMPTION_USER_ID_COL = ?"
        val selectionArgs = arrayOf(userId.toString())

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                fat = cursor.getFloat(cursor.getColumnIndex(DAILY_CONSUMPTION_FAT_COL))
            }
        }

        db.close()
        return fat
    }

    @SuppressLint("Range")
    fun getLastLoginEmail(): String {
        val selectQuery = "SELECT $LOGS_EMAIL_COL FROM $LOGS_TABLE_NAME ORDER BY $LOGS_ID_COL DESC LIMIT 1"
        val db = this.readableDatabase
        var lastLoginEmail: String = ""

        db.rawQuery(selectQuery, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                lastLoginEmail = cursor.getString(cursor.getColumnIndex(LOGS_EMAIL_COL))
            }
        }

        db.close()
        return lastLoginEmail
    }


    fun insertLoginLog(log_email: String) {
        val db = writableDatabase
        val values = ContentValues()

        values.put(LOGS_EMAIL_COL, log_email)
        values.put(LOGS_TEST_COL, 1)

        db.insert(LOGS_TABLE_NAME, null, values)
        db.close()
    }

    fun isUserFoodsTableEmpty(userId: Int?): Boolean {
        val countQuery = "SELECT COUNT(*) FROM $USER_FOODS_TABLE_NAME WHERE $USER_FOODS_USER_ID_COL = $userId"
        val db = this.readableDatabase

        db.rawQuery(countQuery, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val count = cursor.getInt(0)
                return count == 0
            }
        }

        db.close()
        return true
    }

    @SuppressLint("Range")
    fun getFoodAndDatesByUserId(userId: Int?): List<Pair<String, String>> {
        val db = readableDatabase
        val foodAndDates = mutableListOf<Pair<String, String>>()

        val selectQuery = "SELECT f.$FOODS_NAME_COL, u.$USER_FOODS_DATE_COL " +
                "FROM $USER_FOODS_TABLE_NAME u " +
                "INNER JOIN $FOODS_TABLE_NAME f ON u.$USER_FOODS_FOOD_ID_COL = f.$FOODS_ID_COL " +
                "WHERE u.$USER_FOODS_USER_ID_COL = ? " +
                "ORDER BY u.$USER_FOODS_DATE_COL DESC" // Sort by biggest date
        val selectionArgs = arrayOf(userId.toString())

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            while (cursor.moveToNext()) {
                val foodName = cursor.getString(cursor.getColumnIndex(FOODS_NAME_COL))
                val date = cursor.getString(cursor.getColumnIndex(USER_FOODS_DATE_COL))
                foodAndDates.add(foodName to date)
            }
        }

        db.close()
        return foodAndDates
    }

    @SuppressLint("Range")
    fun getUserIdByEmail(email: String): Int? {
        val db = readableDatabase
        var userId: Int? = null

        val selectQuery = "SELECT $ID_COL FROM $TABLE_NAME WHERE $EMAIL_COL = ?"
        val selectionArgs = arrayOf(email)

        db.rawQuery(selectQuery, selectionArgs)?.use { cursor ->
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(cursor.getColumnIndex(ID_COL))
            }
        }

        db.close()
        return userId
    }

    fun insertUserFood(userId: Int, foodId: Int, date: String) {
        val db = writableDatabase
        val values = ContentValues()

        values.put(USER_FOODS_USER_ID_COL, userId)
        values.put(USER_FOODS_FOOD_ID_COL, foodId)
        values.put(USER_FOODS_DATE_COL, date)

        db.insert(USER_FOODS_TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getFoodsByCategory(category: String): List<Food> {
        val foods = mutableListOf<Food>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $FOODS_TABLE_NAME WHERE $FOODS_CATEGORY_COL = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(category))

        while (cursor.moveToNext()) {
            val food = Food(
                id = cursor.getInt(cursor.getColumnIndex(FOODS_ID_COL)),
                name = cursor.getString(cursor.getColumnIndex(FOODS_NAME_COL)),
                category = cursor.getString(cursor.getColumnIndex(FOODS_CATEGORY_COL)),
                carbs = cursor.getFloat(cursor.getColumnIndex(FOODS_CARBS_COL)),
                protein = cursor.getFloat(cursor.getColumnIndex(FOODS_PROTEIN_COL)),
                fat = cursor.getFloat(cursor.getColumnIndex(FOODS_FAT_COL))
            )
            foods.add(food)
        }

        cursor.close()
        return foods
    }

    fun insertFoods() {
        val foods = listOf(
                Food(1, "Scrambled Eggs", "Breakfast", 1.2f, 13.1f, 9.3f),
                Food(2, "Oatmeal", "Breakfast", 54.0f, 6.1f, 1.9f),
                Food(3, "Greek Yogurt", "Breakfast", 3.6f, 10.0f, 0.4f),
                Food(4, "Grilled Chicken Salad", "Lunch", 6.3f, 41.0f, 7.2f),
                Food(5, "Quinoa Bowl", "Lunch", 30.0f, 12.0f, 4.0f),
                Food(6, "Vegetable Wrap", "Lunch", 38.0f, 7.6f, 1.2f),
                Food(7, "Salmon with Roasted Vegetables", "Dinner", 2.1f, 22.5f, 13.9f),
                Food(8, "Brown Rice Stir-Fry", "Dinner", 45.0f, 6.0f, 2.0f),
                Food(9, "Grilled Steak with Sweet Potato", "Dinner", 3.1f, 26.0f, 8.6f),
                Food(10, "Almonds", "Snack", 6.0f, 21.0f, 49.0f),
                Food(11, "Apple Slices with Peanut Butter", "Snack", 11.0f, 3.0f, 16.0f),
                Food(12, "Greek Yogurt with Berries", "Snack", 5.0f, 15.0f, 0.5f)
        )

        val db = writableDatabase

        foods.forEach { food ->
            val values = ContentValues().apply {
                put(FOODS_NAME_COL, food.name)
                put(FOODS_CATEGORY_COL, food.category)
                put(FOODS_CARBS_COL, food.carbs)
                put(FOODS_PROTEIN_COL, food.protein)
                put(FOODS_FAT_COL, food.fat)
            }

            db.insert(FOODS_TABLE_NAME, null, values)
        }

        db.close()
    }


    fun addUser(username: String?, password: String?, email: String?) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(USERNAME_COL, username)
        values.put(PASSWORD_COL, password)
        values.put(EMAIL_COL, email)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun findByUsername(username: String?): User? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $USERNAME_COL = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(username))
        val user = if (cursor.moveToFirst()) {
            User(
                username = cursor.getString(cursor.getColumnIndex(USERNAME_COL)),
                email = cursor.getString(cursor.getColumnIndex(EMAIL_COL)),
                password = cursor.getString(cursor.getColumnIndex(PASSWORD_COL))
            )
        } else {
            null
        }
        cursor.close()
        db.close()
        return user
    }

    @SuppressLint("Range")
    fun findByEmail(email: String?): User? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $EMAIL_COL = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(email))
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                username = cursor.getString(cursor.getColumnIndex(USERNAME_COL)),
                email = cursor.getString(cursor.getColumnIndex(EMAIL_COL)),
                password = cursor.getString(cursor.getColumnIndex(PASSWORD_COL))
            )
        }
        cursor.close()
        // db.close() -- removed as explained in the previous message
        return user
    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        db.execSQL("DROP TABLE IF EXISTS $FOODS_TABLE_NAME")

        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "userdb"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val ID_COL = "id"
        private const val USERNAME_COL = "username"
        private const val PASSWORD_COL = "password"
        private const val EMAIL_COL = "email"

        private const val FOODS_TABLE_NAME = "foods"
        private const val FOODS_ID_COL = "food_id"
        private const val FOODS_NAME_COL = "food_name"
        private const val FOODS_CATEGORY_COL = "category"
        private const val FOODS_CARBS_COL = "carbs"
        private const val FOODS_PROTEIN_COL = "protein"
        private const val FOODS_FAT_COL = "fat"

        private const val USER_FOODS_TABLE_NAME = "UserFoods"
        private const val USER_FOODS_ID_COL = "id"
        private const val USER_FOODS_USER_ID_COL = "userId"
        private const val USER_FOODS_FOOD_ID_COL = "foodId"
        private const val USER_FOODS_DATE_COL = "date"

        private const val LOGS_TABLE_NAME = "LoginLogs"
        private const val LOGS_ID_COL = "log_id"
        private const val LOGS_EMAIL_COL = "log_email"
        private const val LOGS_TEST_COL = "log_test"

        private val DAILY_CONSUMPTION_TABLE_NAME = "daily_consumption"
        private val DAILY_CONSUMPTION_ID_COL = "id"
        private val DAILY_CONSUMPTION_USER_ID_COL = "user_id"
        private val DAILY_CONSUMPTION_PROTEIN_COL = "protein"
        private val DAILY_CONSUMPTION_CARBS_COL = "carbs"
        private val DAILY_CONSUMPTION_FAT_COL = "fat"
    }
}
