package uz.karsoft.baxt.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.karsoft.baxt.data.models.main.saved_data.SavedProductsData

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "products.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "saved_products"

        private const val COLUMN_CATEGORY_ID = "category_id"
        private const val COLUMN_CREATED_AT = "created_at"
        private const val COLUMN_DISCOUNT_PRICE = "discount_price"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IMAGE_URL = "image_url"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_RATING = "rating"
        private const val COLUMN_REVIEW_COUNT = "review_count"
        private const val COLUMN_UPDATED_AT = "updated_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_CATEGORY_ID INTEGER,"
                + "$COLUMN_CREATED_AT TEXT,"
                + "$COLUMN_DISCOUNT_PRICE INTEGER,"
                + "$COLUMN_IMAGE_URL TEXT,"
                + "$COLUMN_NAME TEXT,"
                + "$COLUMN_PRICE INTEGER,"
                + "$COLUMN_RATING REAL,"
                + "$COLUMN_REVIEW_COUNT INTEGER,"
                + "$COLUMN_UPDATED_AT TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Mahsulotni qo'shish
    fun addProduct(product: SavedProductsData) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY_ID, product.category_id)
            put(COLUMN_CREATED_AT, product.created_at)
            put(COLUMN_DISCOUNT_PRICE, product.discount_price)
            put(COLUMN_ID, product.id)
            put(COLUMN_IMAGE_URL, product.image_url)
            put(COLUMN_NAME, product.name)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_RATING, product.rating)
            put(COLUMN_REVIEW_COUNT, product.review_count)
            put(COLUMN_UPDATED_AT, product.updated_at)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Mahsulotni o'chirish
    fun deleteProduct(productId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(productId.toString()))
        db.close()
    }

    // Mahsulot bor yoki yo'qligini tekshirish
    fun isProductLiked(productId: Int): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID),
            "$COLUMN_ID = ?",
            arrayOf(productId.toString()),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // Barcha saqlangan mahsulotlarni olish
    fun getAllSavedProducts(): List<SavedProductsData> {
        val savedProducts = mutableListOf<SavedProductsData>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val product = SavedProductsData(
                    category_id = cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID)),
                    created_at = cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_AT)),
                    discount_price = cursor.getInt(cursor.getColumnIndex(COLUMN_DISCOUNT_PRICE)),
                    id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    image_url = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)),
                    rating = cursor.getDouble(cursor.getColumnIndex(COLUMN_RATING)),
                    review_count = cursor.getInt(cursor.getColumnIndex(COLUMN_REVIEW_COUNT)),
                    updated_at = cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_AT))
                )
                savedProducts.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return savedProducts
    }


}
