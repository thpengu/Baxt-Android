import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.karsoft.baxt.data.models.main.cart.CartData
import uz.karsoft.baxt.data.models.main.saved_data.SavedProductsData

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "products.db"
        private const val DATABASE_VERSION = 6  // Yangi versiyani kiriting

        // saved_products jadvali
        private const val TABLE_SAVED_PRODUCTS = "saved_products"
        private const val COLUMN_SAVED_ID = "id"
        private const val COLUMN_SAVED_CATEGORY_ID = "category_id"
        private const val COLUMN_SAVED_CREATED_AT = "created_at"
        private const val COLUMN_SAVED_DISCOUNT_PRICE = "discount_price"
        private const val COLUMN_SAVED_IMAGE_URL = "image_url"
        private const val COLUMN_SAVED_NAME = "name"
        private const val COLUMN_SAVED_PRICE = "price"
        private const val COLUMN_SAVED_RATING = "rating"
        private const val COLUMN_SAVED_REVIEW_COUNT = "review_count"
        private const val COLUMN_SAVED_UPDATED_AT = "updated_at"

        // cart_products jadvali
        private const val TABLE_CART_PRODUCTS = "cart_products"
        private const val COLUMN_CART_ID = "id"
        private const val COLUMN_CART_CATEGORY_ID = "category_id"
        private const val COLUMN_CART_NAME = "name"
        private const val COLUMN_CART_IMAGE = "image"
        private const val COLUMN_CART_MARKET_ID = "market_id"  // mavjud ustun nomlari
        private const val COLUMN_CART_MARKET_NAME = "market_name"
        private const val COLUMN_CART_QUANTITY = "quantity"
        private const val COLUMN_CART_PRICE = "price"
    }


    override fun onCreate(db: SQLiteDatabase) {

        val createSavedTable = ("CREATE TABLE $TABLE_SAVED_PRODUCTS (" +
                "$COLUMN_SAVED_ID INTEGER PRIMARY KEY," +
                "$COLUMN_SAVED_CATEGORY_ID INTEGER," +
                "$COLUMN_SAVED_CREATED_AT TEXT," +
                "$COLUMN_SAVED_DISCOUNT_PRICE INTEGER," +
                "$COLUMN_SAVED_IMAGE_URL TEXT," +
                "$COLUMN_SAVED_NAME TEXT," +
                "$COLUMN_SAVED_PRICE INTEGER," +
                "$COLUMN_SAVED_RATING REAL," +
                "$COLUMN_SAVED_REVIEW_COUNT INTEGER," +
                "$COLUMN_SAVED_UPDATED_AT TEXT)"
                )
        db.execSQL(createSavedTable)


        val createCartTable = """
        CREATE TABLE $TABLE_CART_PRODUCTS (
            $COLUMN_CART_ID INTEGER PRIMARY KEY,
            $COLUMN_CART_CATEGORY_ID INTEGER,
            $COLUMN_CART_NAME TEXT,
            $COLUMN_CART_IMAGE TEXT,
            $COLUMN_CART_MARKET_ID INTEGER,
            $COLUMN_CART_MARKET_NAME TEXT,
            $COLUMN_CART_QUANTITY INTEGER,
            $COLUMN_CART_PRICE INTEGER
        )
    """.trimIndent()
        db.execSQL(createCartTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {


        db.execSQL("DROP TABLE IF EXISTS $TABLE_SAVED_PRODUCTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CART_PRODUCTS")

        onCreate(db)
    }

    fun addProductToFavorites(product: SavedProductsData) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SAVED_CATEGORY_ID, product.category_id)
            put(COLUMN_SAVED_CREATED_AT, product.created_at)
            put(COLUMN_SAVED_DISCOUNT_PRICE, product.discount_price)
            put(COLUMN_SAVED_ID, product.id)
            put(COLUMN_SAVED_IMAGE_URL, product.image_url)
            put(COLUMN_SAVED_NAME, product.name)
            put(COLUMN_SAVED_PRICE, product.price)
            put(COLUMN_SAVED_RATING, product.rating)
            put(COLUMN_SAVED_REVIEW_COUNT, product.review_count)
            put(COLUMN_SAVED_UPDATED_AT, product.updated_at)
        }
        db.insert(TABLE_SAVED_PRODUCTS, null, values)
        db.close()
    }

    fun addProductToCart(product: CartData) {
        val db = this.writableDatabase

        // Check if the product is already in the cart
        if (isProductINCart(product.id)) {
            // Product already exists in the cart, skip adding
            db.close()
            return
        }

        val existingProducts = getAllCartProducts()
        if (existingProducts.isNotEmpty()) {
            // Get the market ID of the first product in the cart
            val existingMarketId = existingProducts.first().market_id
            if (existingMarketId != product.market_id) {
                // The new product is from a different market, so show a message
                // Here you might want to use a Toast or other UI element to inform the user
                db.close()
                return
            }
        }

        // If the product passes both checks, proceed with adding it to the cart
        val values = ContentValues().apply {
            put(COLUMN_CART_ID, product.id)
            put(COLUMN_CART_CATEGORY_ID, product.category_id)
            put(COLUMN_CART_NAME, product.name)
            put(COLUMN_CART_IMAGE, product.image)
            put(COLUMN_CART_MARKET_ID, product.market_id)
            put(COLUMN_CART_MARKET_NAME, product.market_name)
            put(COLUMN_CART_QUANTITY, product.quantity)
            put(COLUMN_CART_PRICE, product.price)
        }
        db.insert(TABLE_CART_PRODUCTS, null, values)
        db.close()
    }



    fun getAllSavedProducts(): List<SavedProductsData> {
        val savedProducts = mutableListOf<SavedProductsData>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_SAVED_PRODUCTS", null)

        if (cursor.moveToFirst()) {
            do {
                val product = SavedProductsData(
                    category_id = cursor.getInt(cursor.run { getColumnIndex(COLUMN_SAVED_CATEGORY_ID) }),
                    created_at = cursor.getString(cursor.run {
                        getColumnIndex(
                            COLUMN_SAVED_CREATED_AT
                        )
                    }),
                    discount_price = cursor.getInt(cursor.run {
                        getColumnIndex(
                            COLUMN_SAVED_DISCOUNT_PRICE
                        )
                    }),
                    id = cursor.getInt(cursor.run { getColumnIndex(COLUMN_SAVED_ID) }),
                    image_url = cursor.getString(cursor.run { getColumnIndex(COLUMN_SAVED_IMAGE_URL) }),
                    name = cursor.getString(cursor.run { getColumnIndex(COLUMN_SAVED_NAME) }),
                    price = cursor.getInt(cursor.run { getColumnIndex(COLUMN_SAVED_PRICE) }),
                    rating = cursor.getDouble(cursor.run { getColumnIndex(COLUMN_SAVED_RATING) }),
                    review_count = cursor.getInt(cursor.run {
                        getColumnIndex(
                            COLUMN_SAVED_REVIEW_COUNT
                        )
                    }),
                    updated_at = cursor.getString(cursor.run {
                        getColumnIndex(
                            COLUMN_SAVED_UPDATED_AT
                        )
                    })
                )
                savedProducts.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return savedProducts
    }


    fun getAllCartProducts(): List<CartData> {
        val cartProducts = mutableListOf<CartData>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_CART_PRODUCTS", null)

        if (cursor.moveToFirst()) {
            do {
                val product = CartData(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_ID)),
                    category_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_CATEGORY_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_NAME)),
                    image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_IMAGE)),
                    market_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_MARKET_ID)),
                    market_name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CART_MARKET_NAME)),
                    quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_QUANTITY)),
                    price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_PRICE))
                )
                cartProducts.add(product)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cartProducts
    }


    // Mahsulotning "like" holatini tekshirish
    fun isProductLiked(productId: Int): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_SAVED_PRODUCTS,
            arrayOf(COLUMN_SAVED_ID),
            "$COLUMN_SAVED_ID = ?",
            arrayOf(productId.toString()),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun isProductINCart(productId: Int): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_CART_PRODUCTS,
            arrayOf(COLUMN_CART_ID),
            "$COLUMN_CART_ID = ?",
            arrayOf(productId.toString()),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }


    // Mahsulotni o'chirish
    fun deleteProduct(productId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_SAVED_PRODUCTS, "$COLUMN_SAVED_ID = ?", arrayOf(productId.toString()))
        db.close()
    }

    // korzinkada mahsulotni o'chirish
    fun deleteCartProduct(productId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CART_PRODUCTS, "$COLUMN_CART_ID = ?", arrayOf(productId.toString()))
        db.close()
    }



}

