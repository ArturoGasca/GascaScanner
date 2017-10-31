package gasca.com.bardcodescanner.Data

import gasca.com.bardcodescanner.Models.Product
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by in-qu on 26/10/2017.
 */
object CacheManager {

    val sdf = SimpleDateFormat("dd/MM/yyyy")

    private val mCache: MutableMap<String, Product> = mutableMapOf(
        /*"7896014174164" to Product("7896014174164","Botella de agua epura 500 ml", 15.0, 11.0, sdf.parse("10/10/2017"), sdf.parse("15/10/2017")),
        "7501001164003" to Product("7501001164003","Detergente líquido Viva para ropa blanca y de color 4.65 l", 99.0, 0.0, sdf.parse("10/10/2017"), sdf.parse("15/10/2017")),
        "7702018992102" to Product("7702018992102", "Café soluble Nescafé clásico 400 g", 121.0, 0.0, sdf.parse("10/10/2017"), sdf.parse("15/10/2017")),
        "7501199409702" to Product("7501199409702", "Papel higiénico Kleenex Cottonelle beauty care 18 rollos.", 109.00, 79.0, sdf.parse("20/10/2017"), sdf.parse("30/10/2017")),
        "3282770959888" to Product("3282770959888", "Cereal Kellogg's Zucaritas 490 g", 37.0, 0.0, sdf.parse("20/10/2017"), sdf.parse("30/10/2017")),
        "7501001309077" to Product("7501001309077","Mandarina por kilo", 28.9, 0.0, sdf.parse("20/10/2017"), sdf.parse("30/10/2017")),
        "7501199420080" to Product("7501199420080", "Granada fresh nutribits 300 g", 55.0, 35.0, sdf.parse("20/10/2017"), sdf.parse("10/11/2017")),
        "7501017367498" to Product("7501017367498", "Manzana red delicious por kilo", 34.90, 24.9, sdf.parse("28/10/2017"), sdf.parse("30/11/2017")),
        "7702111241152" to Product("7702111241152", "Manzana granny smith por kg", 79.0, 50.50, sdf.parse("1/10/2017"), sdf.parse("20/11/2017")),
        "7501057006432" to Product("7501057006432", "Perón golden Chihuahua por kilo", 38.90, 30.0, sdf.parse("25/10/2017"), sdf.parse("28/10/2017"))*/
    )

    /*
     * Put a key and corresponding value in a map of String, String
     */
    fun put(key: String, value: Product) {
        if(!key.isEmpty()){
            mCache.put(key, value)
        }
    }

    /*
     * retrieves a string value for a corresponding key
     * if this key is previously not inserted using put(..., ...) above
     * it will return null
     */
    fun get(key: String) = mCache[key]

    fun getAll() = mCache.values.toList()

    fun clearAll() = mCache.clear()
}